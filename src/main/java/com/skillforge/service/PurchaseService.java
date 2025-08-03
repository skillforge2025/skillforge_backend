package com.skillforge.service;

import org.springframework.security.core.Authentication;

import com.skillforge.apiresponse.ApiResponse;
import com.skillforge.dto.ContentRequestDTO;

public interface PurchaseService {
	boolean isPurchasedCourse(Authentication authenticated, ContentRequestDTO contentInfo);
	ApiResponse purchaseCourse(Long UserId,Long CourseId);
}
