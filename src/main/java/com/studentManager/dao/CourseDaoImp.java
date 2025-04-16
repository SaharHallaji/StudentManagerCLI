package com.studentManager.dao;

import com.studentManager.models.CourseModel;
import com.studentManager.models.ProfessorModel;
import com.studentManager.models.StudentModel;
import com.studentManager.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class CourseDaoImp implements CourseDao{

    @Override
    public List<CourseModel> getAllCourses() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from CourseModel " , CourseModel.class).list();
        }
    }

    @Override
    public Optional<CourseModel> updateCourse(CourseModel course) {
        return transaction(session -> {
            session.merge(course);
            return course;
        });
    }

    @Override
    public Optional<CourseModel> saveCourse(CourseModel course) {
        return transaction(session -> {
            session.persist(course);
            return course;
        });
    }

    @Override
    public Optional<CourseModel> deleteCourse(int courseId) {
        return transaction(session -> {
            CourseModel course = session.get(CourseModel.class, courseId);
            if (course != null) {

                String hql = "SELECT s FROM StudentModel s JOIN fetch s.courses c WHERE c.courseId = :courseId";
                var students = session.createQuery(hql, StudentModel.class)
                        .setParameter("courseId", courseId)
                        .list();

                for (StudentModel student : students) {
                    student.getCourses().remove(course);
                    session.persist(student);
                }

                session.remove(course);
            }
            return course;
        });
    }

    @Override
    public Optional<CourseModel> getCourse(int courseId) {
        return transaction(session -> session.get(CourseModel.class, courseId));
    }

    @Override
    public Optional<CourseModel> defineProfessor(int courseId, int professorId) {
        return transaction(session -> {
            CourseModel course = session.get(CourseModel.class, courseId);
            ProfessorModel professor = session.get(ProfessorModel.class, professorId);
            if (course != null && professor != null) {
                course.setProfessor(professor);
            }
            session.merge(course);
            return course;
        });
    }

    @Override
    public Optional<CourseModel> removeProfessor(int courseId) {
        return transaction(session -> {
            CourseModel course = session.get(CourseModel.class, courseId);
            if (course != null) {
                course.setProfessor(null);
            }
            session.merge(course);
            return course;
        });
    }

    private Optional<CourseModel> transaction(Function<Session, CourseModel> operation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            CourseModel course = operation.apply(session);
            transaction.commit();
            return Optional.ofNullable(course);
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return Optional.empty();
    }
}
