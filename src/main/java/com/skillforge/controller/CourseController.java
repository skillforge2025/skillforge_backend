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

import com.skillforge.dto.ContentRequestDTO;
import com.skillforge.service.VideoAccessService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;

@RestController
@RequestMapping("/stream")
@AllArgsConstructor

public class CourseController {

	private final VideoAccessService videoAcessService;

	@PreAuthorize("hasRole('STUDENT')")
	@GetMapping("/{courseName}")
	public ResponseEntity<StreamingResponseBody> streamVideo(Authentication authenticated,
			@RequestBody ContentRequestDTO contentRequest, HttpServletResponse response) {
		String url = contentRequest.getUrl();
//		String fileName = url.substring(url.lastIndexOf("/" + 1));
		String fileName="cad35b25-fa43-4de7-bc5d-95831c5452ed_866bdfd2-d95c-43cf-b090-134385c2ff72.MP4";

		ResponseInputStream<GetObjectResponse> s3Stream = videoAcessService.getVideo(authenticated, contentRequest,
				fileName);

		String mimeType = URLConnection.guessContentTypeFromName(fileName);
		if (mimeType == null) {
			mimeType = "application/octet-stream";
		}

		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", "inline; filename=\"" + fileName + "\"");

		StreamingResponseBody stream = outputStream -> {
			byte[] buffer = new byte[4096];
			int bytesRead;
			try (InputStream inputStream = s3Stream) {
				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
				}
			}
		};

		return new ResponseEntity<>(stream, HttpStatus.OK);
	}

}
