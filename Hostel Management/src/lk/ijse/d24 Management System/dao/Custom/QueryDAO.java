package dao.Custom;

import dao.SuperDAO;

import java.util.List;

public interface QueryDAO extends SuperDAO {
    List<Object[]> getPendingKeyPayments() throws Exception;
}
