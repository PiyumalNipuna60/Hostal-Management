package lk.ijse.hostal_management.bo.custom.impl;

import bo.custom.KeyPaymentBO;
import dao.Custom.QueryDAO;
import dao.Custom.ReservationDAO;
import dao.DAOFactory;
import dto.CustomDTO;
import entity.Reservation;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class KeyPaymentBOImpl implements KeyPaymentBO {
    private QueryDAO qDAO = (QueryDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.QUERY);
    private ReservationDAO rDAO = (ReservationDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.RESERVATION);
    @Override
    public ArrayList<CustomDTO> getPendingKeyPayments() throws Exception {
        return  new ArrayList<CustomDTO>(qDAO.getPendingKeyPayments().stream().map(o -> {return new CustomDTO((String)o[0],(String)o[1],(String)o[2],(String)o[3],(String)o[4],(String)o[5]);}).collect(Collectors.toList()));
    }

    @Override
    public boolean updatePaymentStatus(String resId) throws Exception {
        Reservation reservation = rDAO.get(resId);
        reservation.setStatus("Payed");
        return rDAO.update(reservation);
    }
}
