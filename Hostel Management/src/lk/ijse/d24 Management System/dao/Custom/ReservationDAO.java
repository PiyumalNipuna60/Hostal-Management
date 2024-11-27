package dao.Custom;

import dao.CrudDAO;
import entity.Reservation;
import entity.Room;

import java.util.List;

public interface ReservationDAO extends CrudDAO<Reservation,String> {
    List<Reservation> getMatchingResults(String search);

    String getReservationId() throws Exception;
}
