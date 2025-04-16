package com.studentManager.dao;
import com.studentManager.models.CourseModel;
import com.studentManager.models.ProfessorModel;
import com.studentManager.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ProfessorDaoImp implements ProfessorDao {

    @Override
    public List<ProfessorModel> getAllProfessors() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from ProfessorModel" , ProfessorModel.class).list();
        }
    }

    @Override
    public Optional<ProfessorModel> updateProfessor(ProfessorModel professor) {
        return transaction(session -> {
            session.merge(professor);
            return professor;
        });
    }

    @Override
    public Optional<ProfessorModel> saveProfessor(ProfessorModel professor) {
        return transaction(session -> {
            session.persist(professor);
            return professor;
        });
    }

    @Override
    public Optional<ProfessorModel> deleteProfessor(int professorId) {
        return transaction(session -> {
            ProfessorModel professor = session.get(ProfessorModel.class, professorId);
            if (professor != null) {
                List<CourseModel> courses = session
                        .createQuery("from CourseModel where professor.id = :profId", CourseModel.class)
                        .setParameter("profId", professorId)
                        .list();

                for (CourseModel course : courses) {
                    course.setProfessor(null);
                    session.merge(course);
                }

                session.remove(professor);
            }
            return professor;
        });
    }


    @Override
    public Optional<ProfessorModel> getProfessor(int professorId) {
        return transaction(session -> session.get(ProfessorModel.class, professorId));
    }

    private Optional<ProfessorModel> transaction(Function<Session, ProfessorModel> operation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            ProfessorModel course = operation.apply(session);
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
