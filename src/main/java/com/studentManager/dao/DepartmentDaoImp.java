package com.studentManager.dao;

import com.studentManager.models.DepartmentModel;
import com.studentManager.models.ProfessorModel;
import com.studentManager.utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DepartmentDaoImp implements DepartmentDao {


    @Override
    public List<DepartmentModel> getAllDepartments() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("from DepartmentModel " , DepartmentModel.class).list();
        }
    }

    @Override
    public Optional<DepartmentModel> updateDepartment(DepartmentModel department) {
        return transaction(session -> {
            session.merge(department);
            return department;
        });
    }

    @Override
    public Optional<DepartmentModel> saveDepartment(DepartmentModel department) {
        return transaction(session -> {
            session.persist(department);
            return department;
        });
    }

    @Override
    public Optional<DepartmentModel> deleteDepartment(int departmentId) {
        return transaction(session -> {
            DepartmentModel department = session.get(DepartmentModel.class, departmentId);
            if (department != null) {
                for (ProfessorModel prof : department.getProfessors()) {
                    prof.setDepartment(null);
                    session.merge(prof);
                }
                session.remove(department);
            }
            return department;
        });
    }


    @Override
    public Optional<DepartmentModel> getDepartment(int departmentId) {
        return transaction(session -> session.get(DepartmentModel.class, departmentId));
    }

    @Override
    public Optional<DepartmentModel> addProfessorToDepartment(int departmentId, int professorId) {
        return transaction(session -> {
            DepartmentModel department = session.get(DepartmentModel.class, departmentId);
            ProfessorModel professor = session.get(ProfessorModel.class, professorId);
            if (department != null && professor != null) {
                professor.setDepartment(department);
                session.merge(professor);
            }
            return department;
        });
    }

    @Override
    public Optional<DepartmentModel> removeProfessorFromDepartment(int departmentId, int professorId) {
        return transaction(session -> {
            DepartmentModel department = session.get(DepartmentModel.class, departmentId);
            ProfessorModel professor = session.get(ProfessorModel.class, professorId);
            if (department != null && professor != null && professor.getDepartment() != null &&
                    professor.getDepartment().getDepartmentId() == departmentId) {
                professor.setDepartment(null);
                session.merge(professor);
            }
            return department;
        });
    }


    private Optional<DepartmentModel> transaction(Function<Session, DepartmentModel> operation) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()){
            transaction = session.beginTransaction();
            DepartmentModel course = operation.apply(session);
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
