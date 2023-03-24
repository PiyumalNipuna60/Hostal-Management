package lk.ijse.hostal_management.bo;

import bo.custom.impl.*;

public class BOFactory {
    private static BOFactory boFactory;

    private BOFactory() {

    }

    public static BOFactory getInstance() {
        return boFactory == null ? boFactory = new BOFactory() : boFactory;
    }

    public SuperBO getBO(BOTypes type) {
        switch (type) {
            case USER:
                return new UserBOImpl();
            case ROOM:
                return new RoomBOImpl();
            case STUDENT:
                return new StudentBOImpl();
            case RESERVATION:
                return new ReservationBOImpl();
            case KEY_PAYMENTS:
                return new KeyPaymentBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            default:
                return null;
        }
    }

    public enum BOTypes {
        USER, ROOM, STUDENT, RESERVATION, KEY_PAYMENTS, LOGIN
    }
}
