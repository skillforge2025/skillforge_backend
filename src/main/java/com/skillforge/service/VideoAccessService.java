package com.skillforge.service;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.model.S3Object;
import com.skillforge.customexception.ResourceNotFoundException;
import com.skillforge.dto.ContentRequestDTO;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VideoAccessService {
	private final PurchaseService purchaseService;
	private final VideoService videoService;

	public S3Object getVideo(Authentication authenticated, ContentRequestDTO contentRequest, String fileName) {
		if (!purchaseService.isPurchasedCourse(authenticated, contentRequest))
			throw new ResourceNotFoundException("user doen't have access ");
		return videoService.getVideoStream(fileName);
	}
}
