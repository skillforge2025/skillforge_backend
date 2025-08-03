package com.skillforge.controller;

import java.io.InputStream;
import java.net.URLConnection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import com.amazonaws.services.s3.model.S3Object;
import com.skillforge.dto.ContentRequestDTO;
import com.skillforge.service.VideoAccessService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/course")
@AllArgsConstructor

public class CourseController {

	private final VideoAccessService videoAcessService;

	@GetMapping("/stream/{fileName}")
	@PreAuthorize("hasRole('STUDENT')")
	public ResponseEntity<StreamingResponseBody> streamVideo(Authentication authenticated,
			@RequestBody ContentRequestDTO contentRequest, HttpServletResponse response) {
		String url = contentRequest.getUrl();
		String fileName = url.substring(url.lastIndexOf("/" + 1));

		S3Object s3Object = videoAcessService.getVideo(authenticated, contentRequest, fileName);
		InputStream inputStream = s3Object.getObjectContent();

		String mimeType = URLConnection.guessContentTypeFromName(fileName);
		if (mimeType == null)
			mimeType = "application/octet-stream";

		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");
		StreamingResponseBody stream = outputStream -> {
			byte[] buffer = new byte[4096];
			int bytesRead;
			while ((bytesRead = inputStream.read(buffer)) != -1) {
				outputStream.write(buffer, 0, bytesRead);
			}
			inputStream.close();
		};

		return new ResponseEntity<>(stream, HttpStatus.OK);
	}

}
