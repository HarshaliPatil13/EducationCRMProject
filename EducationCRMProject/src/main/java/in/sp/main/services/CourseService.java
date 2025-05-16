package in.sp.main.services;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.sp.main.entities.Course;
import in.sp.main.repositories.CourseRepository;

@Service
public class CourseService 
{
	@Autowired
	private CourseRepository courseRepository;
	
	public List<Course> getAllCourseDetails()
	{
		return courseRepository.findAll();
	}
}
