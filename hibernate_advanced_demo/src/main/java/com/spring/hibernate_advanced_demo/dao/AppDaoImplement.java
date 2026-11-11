package com.spring.hibernate_advanced_demo.dao;

import java.util.List;
import com.spring.hibernate_advanced_demo.entity.Course;
import com.spring.hibernate_advanced_demo.entity.Instructor;
import com.spring.hibernate_advanced_demo.entity.InstructorDetail;
import com.spring.hibernate_advanced_demo.entity.Student;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AppDaoImplement implements AppDao {

    private final EntityManager em;

    @Autowired
    public AppDaoImplement(EntityManager em) {
        this.em = em;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
        em.persist(theInstructor);
    }

    @Override
    public Instructor findInstructorById(int theId) {
        return em.find(Instructor.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorId(int theId) {
        Instructor in = em.find(Instructor.class, theId);
        if (in != null) {
            em.remove(in);
        }
    }

    @Override
    public InstructorDetail findInstructorDetailById(int theId) {
        return em.find(InstructorDetail.class, theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetail(int theId) {
        InstructorDetail deets = em.find(InstructorDetail.class, theId);
        if (deets != null) {
            if (deets.getInstructor() != null) {
                deets.getInstructor().setInstructorDeets(null);
            }
            em.remove(deets);
        }
    }

    @Override
    public List<Course> findCoursesByInstructorId(int theId) {
        TypedQuery<Course> query = em.createQuery(
                "FROM Course WHERE instructor.id=:data", Course.class);
        query.setParameter("data", theId);
        return query.getResultList();
    }

    @Override
    public Instructor findInstructorByIdJoinFetch(int theId) {
        TypedQuery<Instructor> query = em.createQuery(
                "SELECT i FROM Instructor i "
                + "JOIN FETCH i.courses "
                + "JOIN FETCH i.instructorDeets "
                + "WHERE i.id=:data",
                Instructor.class);

        query.setParameter("data", theId);
        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void update(Instructor in) {
        em.merge(in);
    }

    // Methods for updating Course
    @Override
    public Course findCourseById(int theId) {
        return em.find(Course.class, theId);
    }

    @Override
    @Transactional
    public void updateCourse(Course tempCourse) {
        em.merge(tempCourse);
    }

    // Lines 106-122 from screenshot: Delete ONLY instructor
    @Override
    @Transactional
    public void deleteInstructorById(int theId) {
        // retrieve instructor
        Instructor in = em.find(Instructor.class, theId);

        if (in != null) {
            // break the associations of courses for instructor
            List<Course> courses = in.getCourses();
            for (Course c : courses) {
                // remove the instructor from the courses
                c.setInstructor(null);
            }

            // delete only the instructor
            // does not include courses associated
            em.remove(in);
        }
    }

    @Override
    @Transactional
    public void save(Course theCourse) {
        em.persist(theCourse);
    }

    @Override
    public Course findCourseAndReviewsByCourseId(int theId) {
        // create query to fetch course and its reviews in a single SQL round-trip
        TypedQuery<Course> query = em.createQuery(
                "SELECT c FROM Course c JOIN FETCH c.review WHERE c.id = :data", Course.class);
        query.setParameter("data", theId);

        return query.getSingleResult();
    }

    @Override
    @Transactional
    public void deleteCourseById(int theId) {
        // retrieve the course
        Course course = em.find(Course.class, theId);

        // delete the course (cascades to associated reviews)
        if (course != null) {
            em.remove(course);
        }
      }

      // Lines 154-168 from screenshot (Find Course and Students by Course ID)
    @Override
    public Course findCourseAndStudentByCourseId(int theId) {
        // create query
        TypedQuery<Course> query = em.createQuery(
                "SELECT c FROM Course c "
                + "JOIN FETCH c.students "
                + "WHERE c.id=:data", Course.class);
        query.setParameter("data", theId);

        // execute query
        Course course = query.getSingleResult();

        return course;
    }

    // Lines 171-184 from screenshot (Find Student and Courses by Student ID)
    @Override
    public Student findStudentAndCoursesByStudentId(int theId) {
        // create query
        TypedQuery<Student> query = em.createQuery(
                "SELECT s FROM Student s "
                + "JOIN FETCH s.courses "
                + "WHERE s.id=:data", Student.class);
        query.setParameter("data", theId);

        // execute query
        Student student = query.getSingleResult();

        return student;
    }

    // Lines 185-191 from screenshot (Overloaded update method for Student)
    @Override
    @Transactional
    public void update(Student student) {
        em.merge(student);
    }

    // Lines 185-205 from screenshot (Delete Student by ID)
    @Override
    @Transactional
    public void deleteStudentById(int theId) {
        // retrieve student
        Student student = em.find(Student.class, theId);

        if (student != null) {
            // get the courses
            List<Course> courses = student.getCourses();

            // break the associations of courses for student
            for (Course c : courses) {
                c.getStudents().remove(student);
            }

            // delete only the student
            // does not include courses associated
            em.remove(student);
        }
    }
}
