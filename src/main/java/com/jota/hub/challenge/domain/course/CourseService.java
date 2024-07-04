package com.jota.hub.challenge.domain.course;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course create(Course course){
        var courseExists = courseRepository.findByName(course.getName());
        if(courseExists.isPresent()){
            throw new IllegalArgumentException("The course already exists.");
        }

        return courseRepository.save(course);
    }

    public Page<Course> list(Pageable pageable){
        return courseRepository.findAll(pageable);
    }

    public Course findById(Long id){
        var course = courseRepository.findById(id);
        if(course.isEmpty()){
            throw new NoSuchElementException("Course not found.");
        }

        return course.get();
    }

    public void delete(Long id){
        var course = findById(id);

        if(!course.isActive()){
            throw new IllegalArgumentException("The course is already inactive.");
        }

        course.setActive(false);
        courseRepository.save(course);
    }

    public Course update(Course course) {
        var courseExists = findById(course.getId());
        if(!course.getName().isBlank() && !course.getName().equals(courseExists.getName())){
            courseExists.setName(course.getName());
        }
        if(!course.getCategory().isBlank() && !course.getCategory().equals(courseExists.getCategory())){
            courseExists.setCategory(course.getCategory());
        }

        return courseRepository.save(courseExists);
    }
}
