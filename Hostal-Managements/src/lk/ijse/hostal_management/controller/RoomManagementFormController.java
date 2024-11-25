package lk.ijse.hostal_management.controller;

import bo.BOFactory;
import bo.custom.RoomBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dto.RoomDTO;
import dto.UserDTO;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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
import view.tm.RoomTM;
import view.tm.StudentTM;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RoomManagementFormController {
    private final RoomBO rBO = (RoomBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.ROOM);
    private final LinkedHashMap<JFXTextField, Pattern> regexMap = new LinkedHashMap<>();
    public JFXTextField txtTypeID;
    public JFXTextField txtQty;
    public JFXTextField txtRoomType;
    public TableView<RoomTM> tblRooms;
    public TableColumn colId;
    public TableColumn colType;
    public TableColumn colKeyMoney;
    public TableColumn colQty;
    public TableColumn colOption;
    public JFXTextField txtKeyMoney;
    public JFXButton btnAddRoom;
    public JFXButton btnCancel;
    public JFXTextField txtSearch;

    public void addStudentOnAction(ActionEvent actionEvent) {
        try {
            if (btnAddRoom.getText().equals("Add Room")) {
                if (rBO.saveRoom(new RoomDTO(txtTypeID.getText(), txtRoomType.getText(), txtKeyMoney.getText(), Integer.parseInt(txtQty.getText()))))
                    NotificationUtil.playNotification(AnimationType.POPUP, "Room Saved Successfully!", NotificationType.SUCCESS, Duration.millis(3000));

            } else {
                if (rBO.updateRoom(new RoomDTO(txtTypeID.getText(), txtRoomType.getText(), txtKeyMoney.getText(), Integer.parseInt(txtQty.getText()))))
                    NotificationUtil.playNotification(AnimationType.POPUP, "Room Updated Successfully!", NotificationType.SUCCESS, Duration.millis(3000));
            }
            loadAllRooms(rBO.getAllRooms());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
        btnCancel.fire();
    }

    public void initialize() {
        btnAddRoom.setDisable(true);
        colId.setCellValueFactory(new PropertyValueFactory<>("room_Type_id"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colKeyMoney.setCellValueFactory(new PropertyValueFactory<>("key_money"));
        colQty.setCellValueFactory(new PropertyValueFactory<>("qty"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        try {
            loadAllRooms(rBO.getAllRooms());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }

        regexMap.put(txtTypeID,Pattern.compile("^(RM-)[0-9]{4}$"));
        regexMap.put(txtRoomType,Pattern.compile("^[A-z ,-/0-9]+$"));
        regexMap.put(txtQty,Pattern.compile("^[0-9]+$"));
        regexMap.put(txtKeyMoney,Pattern.compile("^[0-9]+([.]{1}[0-9]{1,2})?+$"));



        tblRooms.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setUpdateFields(newValue);
            }
        });
    }

    private void setUpdateFields(RoomTM tm) {
        btnAddRoom.setText("Update Room");
        btnAddRoom.setDisable(false);
        txtTypeID.setText(tm.getRoom_Type_id());
        txtRoomType.setText(tm.getType());
        txtQty.setText(tm.getQty() + "");
        txtKeyMoney.setText(tm.getKey_money());
    }

    private void loadAllRooms(ArrayList<RoomDTO> rooms) {
        tblRooms.setItems(FXCollections.observableArrayList(rooms.stream().map(dto -> {
            return new RoomTM(dto.getRoom_Type_id(), dto.getType(), dto.getKey_money(), dto.getQty(), getButton(dto.getRoom_Type_id()));
        }).collect(Collectors.toList())));
    }

    private JFXButton getButton(String id) {
        JFXButton btn = new JFXButton("Delete");
        btn.setStyle("-fx-border-color: Black");
        btn.setOnAction(event -> {
            if (new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ButtonType.YES,ButtonType.NO).showAndWait().get().equals(ButtonType.YES)) {
                try {
                    rBO.deleteRoom(id);
                    NotificationUtil.playNotification(AnimationType.POPUP, "Room " + id + " Deleted Successfully!", NotificationType.SUCCESS, Duration.millis(3000));
                    loadAllRooms(rBO.getAllRooms());
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
                }
            }else{
                System.out.println("NO");
            }
        });
        return btn;
    }

    public void ValidateFields(KeyEvent keyEvent) {
        Object obj = ValidationUtil.Validate(regexMap, btnAddRoom);
        if (keyEvent.getCode().equals(KeyCode.ENTER))
            if (obj instanceof JFXTextField) ((JFXTextField) obj).requestFocus();
            else btnAddRoom.fire();

    }

    public void clearFOrmOnAction(ActionEvent actionEvent) {
        resetFields(txtTypeID, txtRoomType, txtQty, txtKeyMoney);
        btnAddRoom.setText("Add Room");
        btnAddRoom.setDisable(true);
        tblRooms.getSelectionModel().clearSelection();
    }

    public void resetFields(JFXTextField... fields) {
        for (JFXTextField field : fields) {
            field.getParent().setStyle("-fx-border-color :   #EDEDF0;" + "-fx-border-width:1.5;" + "-fx-border-radius:  5;" + "-fx-background-radius:  5;");
            field.clear();
        }
    }

    public void SearchMatchingResults(KeyEvent keyEvent) {
        try {
            loadAllRooms(rBO.getMatchingRooms(txtSearch.getText()));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }
}
