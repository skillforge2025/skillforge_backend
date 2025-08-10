package com.skillforge.service;

import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import jakarta.websocket.server.ServerEndpoint;

import java.io.File;
import java.io.IOException;

@Service
public class UtilService {

	public double getVideoLengthInMinutes(MultipartFile file) {
		File convFile = null;
		try {
			// Convert MultipartFile to File
			convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
			file.transferTo(convFile);

			// Use JAVE2 to extract video metadata
			MultimediaObject multimediaObject = new MultimediaObject(convFile);
			MultimediaInfo info = multimediaObject.getInfo();

			long durationMillis = info.getDuration(); // duration in milliseconds
			return durationMillis / 60000.0; // convert to minutes

		} catch (IOException | EncoderException e) {
			throw new RuntimeException("Failed to get video duration", e);
		} finally {
			if (convFile != null && convFile.exists()) {
				convFile.delete(); // Cleanup
			}
		}
	}
}
