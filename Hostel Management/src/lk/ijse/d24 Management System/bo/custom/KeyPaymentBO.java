package bo.custom;

import bo.SuperBO;
import dto.CustomDTO;

import java.util.ArrayList;

public interface KeyPaymentBO extends SuperBO {
    ArrayList<CustomDTO> getPendingKeyPayments() throws Exception;

    boolean updatePaymentStatus(String resId) throws Exception;
}
