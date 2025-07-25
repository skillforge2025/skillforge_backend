package com.skillforge.service;



import com.skillforge.dto.PostStudentDTO;
import com.skillforge.dto.StudentDTO;

public interface StudentService {
	StudentDTO studentDetails(Long id);
	StudentDTO addStudentDetails(PostStudentDTO student);
}
