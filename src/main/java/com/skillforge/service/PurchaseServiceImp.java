package com.skillforge.service;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.skillforge.apiresponse.ApiResponse;
import com.skillforge.customexception.ResourceNotFoundException;
import com.skillforge.customexception.UserNotFoundException;
import com.skillforge.dao.CourseDao;
import com.skillforge.dao.StudentDao;
import com.skillforge.dto.ContentRequestDTO;
import com.skillforge.entity.Course;
import com.skillforge.entity.CoursePurchasedDetails;
import com.skillforge.entity.Student;
import com.skillforge.entity.User;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class PurchaseServiceImp implements PurchaseService {

	private final StudentDao studentDao;
	private final CourseDao courseDao;

	@Override
	public boolean isPurchasedCourse(Authentication authenticated, ContentRequestDTO contentInfo) {

		User user = (User) authenticated.getPrincipal();
		Student student = studentDao.findById(user.getId())
				.orElseThrow(() -> new ResourceNotFoundException("student doen't exit"));
		List<CoursePurchasedDetails> courses = student.getCourseList();
		for (int i = 0; i < courses.size(); i++) {
			if (courses.get(i).getCourse().getCourseId().equals(contentInfo.getCourseId()))
				return true;
		}
		return false;
	}

	@Override
	public ApiResponse purchaseCourse(Long userId, Long courseId) {
		// TODO Auto-generated method stub
		Student student = studentDao.findById(userId).orElseThrow(() -> new UserNotFoundException("invalid user "));
		Course course = courseDao.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("invalid couse"));
		CoursePurchasedDetails coursePurchased=new  CoursePurchasedDetails();
		coursePurchased.setStudent(student);
		coursePurchased.setCourse(course);
		student.getCourseList().add(coursePurchased);
		studentDao.save(student);
		return new ApiResponse("purchased successfully");
	}
}
