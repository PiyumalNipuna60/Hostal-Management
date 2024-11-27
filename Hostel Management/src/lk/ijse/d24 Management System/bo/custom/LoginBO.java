package bo.custom;

import bo.SuperBO;

import java.util.HashMap;

public interface LoginBO extends SuperBO {

    boolean checkCredentials(String userName, String password) throws Exception;
}
