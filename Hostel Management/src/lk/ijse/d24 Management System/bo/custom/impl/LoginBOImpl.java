package bo.custom.impl;

import bo.custom.LoginBO;
import bo.custom.UserBO;
import dao.Custom.UserDAO;
import dao.DAOFactory;

import java.util.HashMap;

public class LoginBOImpl implements LoginBO {
    private final UserDAO uDAO = (UserDAO) DAOFactory.getInstance().getDAO(DAOFactory.DAOTypes.USER);

    @Override
    public boolean checkCredentials(String userName, String password) throws Exception {
        HashMap<String, String> allUsers = uDAO.getAllUserNPasswordMap();
        if (allUsers.containsKey(userName)) {
            return allUsers.get(userName).equals(password);
        } else {
            return false;
        }
    }
}
