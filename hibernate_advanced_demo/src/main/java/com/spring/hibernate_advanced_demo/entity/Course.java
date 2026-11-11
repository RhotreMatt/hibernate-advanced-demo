package com.spring.hibernate_advanced_demo.entity;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name="course")
public class Course {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id")
    private int id;

    @Column(name="title")
    private String title;

    @ManyToOne(cascade= {CascadeType.DETACH,
                        CascadeType.MERGE,
                        CascadeType.PERSIST,
                        CascadeType.REFRESH})
    @JoinColumn(name="instructor_id")
    private Instructor instructor;

    @OneToMany(fetch=FetchType.LAZY, cascade=CascadeType.ALL)
    @JoinColumn(name="course_id")
    private List<Review> review;

    // Step B: ManyToMany Owning Side mapping (Lines 43-49)
    @ManyToMany(fetch=FetchType.LAZY,
                cascade= {CascadeType.DETACH,
                          CascadeType.MERGE,
                          CascadeType.PERSIST,
                          CascadeType.REFRESH})
    @JoinTable(name="course_student",
               joinColumns=@JoinColumn(name="course_id"),
               inverseJoinColumns=@JoinColumn(name="student_id"))
    private List<Student> students;

    public Course() {
    }

    public Course(String title) {
        this.title = title;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public Instructor getInstructor() { return instructor; }
    public void setInstructor(Instructor instructor) { this.instructor = instructor; }

    public List<Review> getReview() { return review; }
    public void setReview(List<Review> review) { this.review = review; }

    public List<Student> getStudents() { return students; }
    public void setStudents(List<Student> students) { this.students = students; }

    public void addReview(Review rev) {
        if (review == null) {
            review = new ArrayList<>();
        }
        review.add(rev);
    }

    // Step B: Convenience method for adding students (Lines 110-115)
    public void addStudent(Student stud) {
        if (students == null) {
            students = new ArrayList<>();
        }
        students.add(stud);
    }

    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}