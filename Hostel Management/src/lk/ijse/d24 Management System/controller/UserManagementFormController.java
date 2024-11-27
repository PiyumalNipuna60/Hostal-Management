package controller;

import bo.BOFactory;
import bo.custom.UserBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.UserDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import util.NotificationUtil;
import util.ValidationUtil;
import view.tm.UserTM;

import java.sql.SQLException;
import java.util.ArrayList;

import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserManagementFormController {
    private final UserBO uBO = (UserBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.USER);
    private final LinkedHashMap<JFXTextField, Pattern> regexMap = new LinkedHashMap<>();
    public JFXTextField txtNic;
    public JFXTextField txtConfirmPassword;
    public JFXTextField txtPassword;
    public JFXTextField txtName;
    public JFXTextField txtUserName;
    public JFXButton btnAddUser;
    public JFXButton btnCancel;
    public TableView<UserTM> tblUser;
    public TableColumn colNIC;
    public TableColumn colName;
    public TableColumn colUsername;
    public TableColumn userPassword;
    public TableColumn colOption;
    public JFXTextField txtSearch;

    public void addUserOnAction(ActionEvent actionEvent) {
        if (txtPassword.getText().equals(txtConfirmPassword.getText())) {
            try {
                if (btnAddUser.getText().equals("Add User")) {
                    if (uBO.saveUser(new UserDTO(txtNic.getText(), txtName.getText(), txtUserName.getText(), txtPassword.getText())))
                        NotificationUtil.playNotification(AnimationType.POPUP, "User Saved Successfully!", NotificationType.SUCCESS, Duration.millis(3000));
                } else {
                    if (uBO.updateUser(new UserDTO(txtNic.getText(), txtName.getText(), txtUserName.getText(), txtPassword.getText())))
                        NotificationUtil.playNotification(AnimationType.POPUP, "User Updated Successfully!", NotificationType.SUCCESS, Duration.millis(3000));
                }
                loadAllUsers(uBO.getAllUsers());
            } catch (Exception e) {
                e.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "User Already Exists!", ButtonType.OK).show();
            }
            btnCancel.fire();
        } else {
            new Alert(Alert.AlertType.WARNING, "Passwords Doesn't Match!").show();
        }
    }

    public void resetFields(JFXTextField... fields) {
        for (JFXTextField field : fields) {
            field.getParent().setStyle("-fx-border-color :   #EDEDF0;" + "-fx-border-width:1.5;" + "-fx-border-radius:  5;" + "-fx-background-radius:  5;");
            field.clear();
        }
    }

    public void clearFormOnAction(ActionEvent actionEvent) {
        btnAddUser.setText("Add User");
        btnAddUser.setDisable(true);
        tblUser.getSelectionModel().clearSelection();
        resetFields(txtNic, txtName, txtUserName, txtPassword, txtConfirmPassword);
    }

    public void initialize() {
        btnAddUser.setDisable(true);
        colNIC.setCellValueFactory(new PropertyValueFactory<>("nic"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colUsername.setCellValueFactory(new PropertyValueFactory<>("userName"));
        userPassword.setCellValueFactory(new PropertyValueFactory<>("password"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        try {
            loadAllUsers(uBO.getAllUsers());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        regexMap.put(txtNic, Pattern.compile("^[0-9]{12,15}(V)?(v)?$"));
        regexMap.put(txtName, Pattern.compile("^[A-z ]+$"));
        regexMap.put(txtUserName, Pattern.compile("^[A-z_.0-9]{5,20}$"));
        regexMap.put(txtPassword, Pattern.compile("^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})$"));
        regexMap.put(txtConfirmPassword, Pattern.compile("^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,15})$"));

        tblUser.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setUpdateFields(newValue);
            }
        });
    }

    private void setUpdateFields(UserTM tm) {
        btnAddUser.setDisable(false);
        txtNic.setText(tm.getNic());
        txtName.setText(tm.getName());
        txtUserName.setText(tm.getUserName());
        txtPassword.setText(tm.getPassword());
        txtConfirmPassword.setText(tm.getPassword());
        btnAddUser.setText("Update User");
    }


    private void loadAllUsers(ArrayList<UserDTO> users) {
        ObservableList<UserTM> obList = FXCollections.observableArrayList();
        obList.addAll(users.stream().map(dto -> {
            JFXButton btn = new JFXButton("Delete");
            btn.setStyle("-fx-border-color: Black");
            btn.setOnAction(event -> {
                deleteUser(dto.getNic());
            });
            return new UserTM(dto.getNic(), dto.getName(), dto.getUserName(), dto.getPassword(), btn);
        }).collect(Collectors.toList()));
        tblUser.setItems(obList);
    }

    public void deleteUser(String nic) {
        try {
            if (new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO).showAndWait().get().equals(ButtonType.YES)) {
                uBO.deleteUser(nic);
                NotificationUtil.playNotification(AnimationType.POPUP, "User Successfully Deleted!", NotificationType.SUCCESS, Duration.millis(3000));
            }
            loadAllUsers(uBO.getAllUsers());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }


    public void searchUserOnKeyReleased(KeyEvent keyEvent) {
        try {
            loadAllUsers(uBO.getMatchingUsers(txtSearch.getText()));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    public void validateFieldsOnKeyReleased(KeyEvent keyEvent) {
        Object obj = ValidationUtil.Validate(regexMap, btnAddUser);
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            if (obj instanceof JFXTextField) ((JFXTextField) obj).requestFocus();
            else btnAddUser.fire();

    }
}
