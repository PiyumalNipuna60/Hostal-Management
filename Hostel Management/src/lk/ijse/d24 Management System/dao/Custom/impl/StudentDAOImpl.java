package dao.Custom.impl;

import dao.Custom.StudentDAO;
import entity.Student;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FactoryConfiguration;

import java.sql.SQLException;
import java.util.List;

public class StudentDAOImpl implements StudentDAO {
    @Override
    public boolean save(Student entity) throws ClassNotFoundException, SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Student entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.update(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean delete(String s) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.delete(session.load(Student.class, s));
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Student get(String s) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Student student = session.get(Student.class, s);
        transaction.commit();
        session.close();
        return student;
    }

    @Override
    public List<Student> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Student> list = session.createQuery("FROM Student").list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public List<Student> getMatchingResults(String search) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Student> list = session.createQuery("FROM Student WHERE StudentId Like :ID OR ContactNo LIKE :contactNo OR address LIKE :address OR gender LIKE :gender OR name LIKE :name ")
                .setParameter("ID", search)
                .setParameter("contactNo", search)
                .setParameter("address", search)
                .setParameter("gender", search)
                .setParameter("name", search)
                .list();
        transaction.commit();
        session.close();
        return list;
    }
}
