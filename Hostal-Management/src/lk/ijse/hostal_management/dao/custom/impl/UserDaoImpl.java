package lk.ijse.hostal_management.dao.custom.impl;

import dao.Custom.UserDAO;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import util.FactoryConfiguration;

import java.io.Serializable;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class UserDaoImpl implements UserDAO {
    @Override
    public boolean save(User entity) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(User entity) throws Exception {
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
        session.delete(session.load(User.class, s));
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public User get(String s) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        User user = session.get(User.class, s);
        transaction.commit();
        session.close();
        return user;
    }

    @Override
    public List<User> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<User> list = session.createQuery("FROM User").list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public List<User> getMatchingResults(String search) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<User> list = session.createQuery("FROM User WHERE nic Like :NIC OR name LIKE :Name OR userName LIKE :UserName OR password LIKE :Password ")
                .setParameter("NIC", search)
                .setParameter("Name", search)
                .setParameter("UserName", search)
                .setParameter("Password", search).list();
        transaction.commit();
        session.close();
        return list;
    }

    @Override
    public HashMap<String, String> getAllUserNPasswordMap() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Object[]> list = session.createQuery("SELECT userName, password FROM User").list();
        transaction.commit();
        session.close();
        HashMap<String, String> userMap = new HashMap<>();
        list.stream().forEach(o -> userMap.put((String)o[0],(String)o[1]));
        return userMap;
    }
}
