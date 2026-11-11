package com.spring.hibernate_advanced_demo;

import com.spring.hibernate_advanced_demo.dao.AppDao;
import com.spring.hibernate_advanced_demo.entity.Course;
import com.spring.hibernate_advanced_demo.entity.Instructor;
import com.spring.hibernate_advanced_demo.entity.InstructorDetail;
import com.spring.hibernate_advanced_demo.entity.Review;
import com.spring.hibernate_advanced_demo.entity.Student;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class HibernateAdvancedDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(HibernateAdvancedDemoApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(AppDao app) {
        return runner -> {
            // Uncomment the operation you want to execute:
            // createInstructor(app);
            // findInstructor(app);
            // deleteInstructor(app);
            findInstructorDetail(app);
            // deleteInstructorDetail(app);
            createInstructorWithCourses(app);
            findCoursesForInstructor(app);
            // Step G: Test JOIN FETCH
            findInstructorWithCoursesJoinFetch(app);

            // Step H: Test Update
            // updateInstructor(app);

            // updateCourse(app);
            deleteInstructorOnly(app);

            // Step D: Create course with reviews
            createCourseAndReviews(app);

            // Retrieve course with reviews
            // retrieveCourseAndReviews(app);
            deleteCourseAndReviews(app);

            createCourseAndStudents(app);

            // findCourseAndStudents(app);
            // findStudentAndCourses(app);
            // addMoreCoursesForStudent(app);
            // deleteCourseById(app);
            deleteStudent(app);
        };
    }

    private void createInstructor(AppDao app) {
        Instructor ins = new Instructor("Vicky",
                "Magtatanggol",
                "vicky.mags@example.com");

        InstructorDetail deets = new InstructorDetail("Math",
                "Solving problems");

        ins.setInstructorDeets(deets);

        System.out.println("Information saved!");
        app.save(ins);
    }

    private void findInstructor(AppDao app) {
        int id = 1;
        System.out.println("Find id: " + id);

        Instructor ins = app.findInstructorById(id);

        if (ins != null) {
            System.out.println("Instructor: " + ins);
            System.out.println("Details: " + ins.getInstructorDeets());
        } else {
            System.out.println("Instructor not found for id: " + id);
        }
    }

    private void deleteInstructor(AppDao app) {
        int id = 1;
        System.out.println("Delete id: " + id);

        app.deleteInstructorId(id);
        System.out.println("Deleted successfully!");
    }

    private void findInstructorDetail(AppDao app) {
        // get the instructor detail object
        int id = 23;
        InstructorDetail inDeets = app.findInstructorDetailById(id);

        if (inDeets != null) {
            // print the instructor detail
            System.out.println("Details: " + inDeets);

            // print the associated instructor
            System.out.println("Instructor: " + inDeets.getInstructor());
        } else {
            System.out.println("InstructorDetail not found for id: " + id);
        }
    }

    // private void deleteInstructorDetail(AppDao app) {
    //     int id = 22;
    //     System.out.println("Delete id: " + id);

    //     app.deleteInstructorDetail(id);
    //     System.out.println("Deleted successfully!");
    // }

    private void deleteInstructorDetail(AppDao app) {
        int id = 23;
        System.out.println("Delete id: " + id);

        app.deleteInstructorDetail(id);
        System.out.println("Deleted successfully!");
    }

    private void createInstructorWithCourses(AppDao app) {
        // create the instructor and details
        Instructor ins = new Instructor("Peri",
                "Winkle",
                "peri.winkle@example.com");

        InstructorDetail deets = new InstructorDetail("Science",
                "Sprinkling dust");

        // associate the objects
        ins.setInstructorDeets(deets);

        // create courses
        Course course1 = new Course("Physics");
        Course course2 = new Course("Biology");

        // add courses to instructor
        ins.add(course1);
        ins.add(course2);

        // save the instructor
        System.out.println("Courses saved: " + ins.getCourses());
        app.save(ins);
    }

    private void findCoursesForInstructor(AppDao app) {
        int id = 24;

        // find the instructor
        System.out.println("Find instructor id: " + id);
        Instructor ins = app.findInstructorById(id);

        System.out.println("Instructor: " + ins);

        // find courses for instructor
        System.out.println("Courses from id: " + id);
        List<Course> courses = app.findCoursesByInstructorId(id);

        // associate the objects
        ins.setCourses(courses);

        // get the courses
        System.out.println(ins.getCourses());
    }

    // Step G: Lines 32-41 from your screenshot
    private void findInstructorWithCoursesJoinFetch(AppDao app) {
        int id = 3; // Ensure ID 3 (or an ID created with courses) exists in your database
        System.out.println("Find instructor id: " + id);

        Instructor ins = app.findInstructorByIdJoinFetch(id);

        System.out.println("Instructor: " + ins);
        System.out.println("Courses: " + ins.getCourses());
    }

    // Step H: Lines 33-46 from your screenshot
    private void updateInstructor(AppDao app) {
        int id = 3;
        System.out.println("Find instructor id: " + id);

        Instructor ins = app.findInstructorById(id);

        // change instructor's data
        ins.setLastName("Cosma");

        // update the data
        System.out.println("Updated!");
        app.update(ins);
    }

    // Update Course Method
    private void updateCourse(AppDao app) {
        int id = 10;
        System.out.println("Finding course id: " + id);

        Course tempCourse = app.findCourseById(id);

        if (tempCourse != null) {
            System.out.println("Updating course title...");
            tempCourse.setTitle("Air Guitar - The Ultimate Guide");

            app.updateCourse(tempCourse);
            System.out.println("Course updated successfully!");
        }
    }

    // Lines 34-40 from screenshot: Delete instructor only
    private void deleteInstructorOnly(AppDao app) {
        int id = 3;
        System.out.println("Delete instructor: " + id);

        app.deleteInstructorById(id);
        System.out.println("Deleted successfully!");
    }

    private void createCourseAndReviews(AppDao app) {
        // create a course
        Course course = new Course("How to Speed Run Spring in 1 Day");

        // add some reviews
        course.addReview(new Review("Great speed 2x...loved it!!!"));
        course.addReview(new Review("I wanted the speed 0.75x :(("));
        course.addReview(new Review("Hoping to do a speed of 3x :D. Anyways, nice!"));

        // save the course
        System.out.println("Course saved!");
        app.save(course);
    }

    // Lines 38-49 from last screenshot
    private void retrieveCourseAndReviews(AppDao app) {
        int id = 3; // Ensure the course ID exists in your DB
        System.out.println("Get course and reviews from id: " + id);

        // get the course and reviews
        Course course = app.findCourseAndReviewsByCourseId(id);

        // print the course
        System.out.println(course);

        // print the reviews
        System.out.println(course.getReview());
    }

    private void deleteCourseAndReviews(AppDao app) {
        int id = 3;
        System.out.println("Delete course: " + id);

        app.deleteCourseById(id);
        System.out.println("Deleted successfully!");
    }

    private void createCourseAndStudents(AppDao app) {
        // create a course
        Course course = new Course("How to Be A FullStack Dev in Half A Day");

        // create the students
        Student student1 = new Student("Light", "Yagami", "light.yagami@example.com");
        Student student2 = new Student("Leslie", "Andrews", "leslie.andrews@example.com");

        // add student to the course
        course.addStudent(student1);
        course.addStudent(student2);

        // save the course and associated students
        app.save(course);
        System.out.println("Successfully enrolled!");
    }

    // Lines 41-47 from screenshot
    private void findCourseAndStudents(AppDao app) {
        int id = 21;
        Course course = app.findCourseAndStudentByCourseId(id);

        System.out.println("Course: " + course);
        System.out.println("Students: " + course.getStudents());
    }

    // Lines 42-49 from screenshot
    private void findStudentAndCourses(AppDao app) {
        int id = 2;
        Student student = app.findStudentAndCoursesByStudentId(id);

        System.out.println("Student: " + student);
        System.out.println("Courses: " + student.getCourses());
    }

    // Lines 44-59 from screenshot
    private void addMoreCoursesForStudent(AppDao app) {
        int id = 2;
        Student student = app.findStudentAndCoursesByStudentId(id);

        Course course1 = new Course("How to Test without Running Application");
        Course course2 = new Course("How to Develop without Computer");
        Course course3 = new Course("How to Be a Legit Developer");

        student.addCourse(course1);
        student.addCourse(course2);
        student.addCourse(course3);

        System.out.println("Succesffuly enrolled to new courses!");

        app.update(student);
    }

    // Lines 127-134 from screenshot
    private void deleteCourseById(AppDao app) {
        int id = 23;
        System.out.println("Delete course: " + id);

        app.deleteCourseById(id);
        System.out.println("Deleted successfully!");
    }

    // Lines 43-50 from screenshot
    private void deleteStudent(AppDao app) {
        int id = 2;
        System.out.println("Delete student: " + id);

        app.deleteStudentById(id);
        System.out.println("Deleted successfully!");
    }
}