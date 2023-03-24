package lk.ijse.hostal_management.dao.custom;

import dao.SuperDAO;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<Object[]> getPendingKeyPayments() throws Exception;
}
