package lk.ijse.hostal_management.dao.custom.impl;

import dao.Custom.QueryDAO;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.FactoryConfiguration;

import java.util.List;

public class QueryDAOImpl implements QueryDAO {
    @Override
    public List<Object[]> getPendingKeyPayments() throws Exception {
        Session session = FactoryConfiguration.getInstance().getSession();
        Transaction transaction = session.beginTransaction();
        List<Object[]> list = session.createQuery("SELECT reservation.resId,s.StudentId,s.name, r.room_Type_id, r.type, reservation.status FROM Reservation reservation JOIN Student s ON reservation.student=s.StudentId JOIN Room r ON reservation.room=r.room_Type_id WHERE reservation.status='Pending'").list();
        transaction.commit();
        session.close();
        return list;
    }
}
