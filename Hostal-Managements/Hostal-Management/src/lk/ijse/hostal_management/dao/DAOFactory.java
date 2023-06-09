package lk.ijse.hostal_management.dao;

import dao.Custom.impl.*;

public class DAOFactory {
    private static DAOFactory daoFactory;

    private DAOFactory() {

    }

    public static DAOFactory getInstance() {
        return daoFactory == null ? daoFactory = new DAOFactory() : daoFactory;
    }

    public SuperDAO getDAO(DAOTypes DAOTypes) {
        switch (DAOTypes) {
            case USER:
                return new UserDaoImpl();
            case STUDENT:
                return new StudentDAOImpl();
            case ROOM:
                return new RoomDAOImpl();
            case RESERVATION:
                return new ReservationDAOImpl();
            case QUERY:
                return new QueryDAOImpl();
            default:
                return null;
        }
    }

    public enum DAOTypes {
        USER, STUDENT, ROOM, RESERVATION, QUERY
    }
}
