package com.skillforge.service;

import software.amazon.awssdk.core.ResponseInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
public class VideoService {

    @Autowired
    private S3Client s3Client; // Now AWS SDK v2

    @Value("${aws.s3.bucketName}")
    private String bucketName;

    @Value("${aws.s3.imageBucketName}")
    private String imageBucket;

    public String uploadImage(MultipartFile file) {
        String key = "courses/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(imageBucket)
                    .key(key)
                    .contentType(file.getContentType())
                    .acl("public-read") // Same as CannedAccessControlList.PublicRead in v1
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
        } catch (IOException e) {
            throw new RuntimeException("Error uploading image to S3: " + e.getMessage(), e);
        }

        return "https://" + imageBucket + ".s3.amazonaws.com/" + key;
    }

    public String uploadVideo(MultipartFile file) {
        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
        try {
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucketName)
                    .key(fileName)
                    .contentType(file.getContentType())
                    .build();

            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));

        } catch (IOException e) {
            throw new RuntimeException("Error uploading video to S3: " + e.getMessage(), e);
        }

        return "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
    }

    // Download video from S3
    public ResponseInputStream<GetObjectResponse> getVideoStream(String fileName) {
        GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                .bucket(bucketName)
                .key(fileName)
                .build();

        return s3Client.getObject(getObjectRequest);
    }
}
