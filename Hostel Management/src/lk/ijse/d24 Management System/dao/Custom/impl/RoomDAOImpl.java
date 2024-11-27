package dao.Custom.impl;

import dao.Custom.RoomDAO;
import entity.Room;
import entity.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FactoryConfiguration;

import java.sql.SQLException;
import java.util.List;

public class RoomDAOImpl implements RoomDAO {
    @Override
    public boolean save(Room entity) throws ClassNotFoundException, SQLException {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        session.save(entity);
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public boolean update(Room entity) throws Exception {
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
        session.delete(session.load(Room.class, s));
        transaction.commit();
        session.close();
        return true;
    }

    @Override
    public Room get(String s) throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        Room room = session.get(Room.class, s);
        transaction.commit();
        session.close();
        return room;
    }

    @Override
    public List<Room> getAll() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List list = session.createQuery("FROM Room").list();
        transaction.commit();
        session.close();
        return list;
    }


    @Override
    public List<Room> getMatchingResults(String search) {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Room> list = session.createQuery("FROM Room WHERE room_Type_id Like :typeId OR key_money LIKE :keyMoney OR qty LIKE :Qty OR type LIKE :Type ")
                .setParameter("typeId", search)
                .setParameter("keyMoney", search)
                .setParameter("Qty", search)
                .setParameter("Type", search).list();
        transaction.commit();
        session.close();
        return list;
    }
}
