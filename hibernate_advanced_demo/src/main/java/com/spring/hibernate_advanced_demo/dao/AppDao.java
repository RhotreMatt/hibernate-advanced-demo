package com.spring.hibernate_advanced_demo.dao;

import java.util.List;

import com.spring.hibernate_advanced_demo.entity.Course;
import com.spring.hibernate_advanced_demo.entity.Instructor;
import com.spring.hibernate_advanced_demo.entity.InstructorDetail;
import com.spring.hibernate_advanced_demo.entity.Student;

public interface AppDao {

    void save(Instructor theInstructor);

    Instructor findInstructorById(int theId);

    void deleteInstructorId(int theId);

    InstructorDetail findInstructorDetailById(int theId);

    void deleteInstructorDetail(int theId);

    List<Course> findCoursesByInstructorId(int theId);

    // Step G: Join Fetch
    Instructor findInstructorByIdJoinFetch(int theId);

    // Step H: Update
    void update(Instructor in);

    // Update Course methods
    Course findCourseById(int theId);

    void updateCourse(Course tempCourse);

    // Delete ONLY instructor method
    void deleteInstructorById(int theId);

    // Overloaded save method for Course
    void save(Course theCourse);

    // Retrieve course and its reviews using join fetch
    Course findCourseAndReviewsByCourseId(int theId);

    // Step G: Delete Course by ID
    void deleteCourseById(int theId);

    // Many-to-Many methods
    Course findCourseAndStudentByCourseId(int theId);

    Student findStudentAndCoursesByStudentId(int theId);

    void update(Student student);

    void deleteStudentById(int theId);
}