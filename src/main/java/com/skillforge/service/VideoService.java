package com.skillforge.service;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.skillforge.customexception.UserNotFoundException;
import com.skillforge.dao.StudentDao;
import com.skillforge.dao.UserDao;
import com.skillforge.dto.ContentRequestDTO;
import com.skillforge.entity.Student;
import com.skillforge.entity.User;

@Service
public class VideoService {

    private final UserDao userDao;
	@Autowired
	private AmazonS3 s3Client;

	@Autowired
	private StudentDao studentDao;
	
	@Value("${aws.s3.bucketName}")
	private String bucketName;

    VideoService(UserDao userDao) {
        this.userDao = userDao;
    }

	public String uploadVideo(MultipartFile file) throws IOException {
		String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
		ObjectMetadata metadata = new ObjectMetadata();
		metadata.setContentLength(file.getSize());
		metadata.setContentType(file.getContentType());

		s3Client.putObject(bucketName, fileName, file.getInputStream(), metadata);

		return s3Client.getUrl(bucketName, fileName).toString(); // Return public URL
	}

	// accessing video
	public S3Object getVideoStream( String fileName) {
		return s3Client.getObject(bucketName, fileName);
	}
}
