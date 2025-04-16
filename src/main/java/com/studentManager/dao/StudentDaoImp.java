package com.studentManager.dao;

import com.studentManager.models.CourseModel;
import com.studentManager.models.StudentModel;
import com.studentManager.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class StudentDaoImp implements StudentDao{

    @Override
    public List<StudentModel> getAllStudent() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from StudentModel", StudentModel.class).list();
        }
    }

    @Override
    public Optional<StudentModel> updateStudent(StudentModel student) {
        return transaction(session -> {
            session.merge(student);
            return student;
        });
    }

    @Override
    public Optional<StudentModel> saveStudent(StudentModel student) {
        return transaction(session -> {
            session.persist(student);
            return student;
        });
    }

    @Override
    public Optional<StudentModel> deleteStudent(int studentId) {
        return transaction(session -> {
            StudentModel student = session.get(StudentModel.class, studentId);
            if (student != null) {
                session.remove(student);
            }
            return student;
        });
    }

    @Override
    public Optional<StudentModel> getStudent(int studentId) {
        return transaction(session -> session.get(StudentModel.class, studentId));
    }

    @Override
    public Optional<StudentModel> registerCourse(int studentId, int courseId) {
        return transaction(session -> {
            StudentModel student = session.get(StudentModel.class, studentId);
            CourseModel course = session.get(CourseModel.class, courseId);
            if (student != null && course != null) {
                student.getCourses().add(course);
            }
            session.merge(student);
            return student;
        });
    }

    @Override
    public Optional<StudentModel> removeCourse(int studentId, int courseId) {
        return transaction(session -> {
            StudentModel student = session.get(StudentModel.class, studentId);
            CourseModel course = session.get(CourseModel.class, courseId);
            if (student != null && course != null) {
                student.getCourses().remove(course);
            }
            session.merge(student);
            return student;
        });
    }

    private Optional<StudentModel> transaction(Function<Session, StudentModel> operation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            StudentModel student = operation.apply(session);
            transaction.commit();
            return Optional.ofNullable(student);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.empty();
    }
}
