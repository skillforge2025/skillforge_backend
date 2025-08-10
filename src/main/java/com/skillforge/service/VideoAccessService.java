package com.skillforge.service;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import com.skillforge.customexception.ResourceNotFoundException;
import com.skillforge.dto.ContentRequestDTO;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class VideoAccessService {

    private final PurchaseService purchaseService;
    private final VideoService videoService;

    public ResponseInputStream<GetObjectResponse> getVideo(Authentication authenticated, ContentRequestDTO contentRequest, String fileName) {
        if (!purchaseService.isPurchasedCourse(authenticated, contentRequest))
            throw new ResourceNotFoundException("User doesn't have access");
        return videoService.getVideoStream(fileName);
    }
}
