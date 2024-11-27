package controller;

import bo.BOFactory;
import bo.custom.ReservationBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXTextField;
import dto.ReservationDTO;
import dto.RoomDTO;
import dto.StudentDTO;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleGroup;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import util.NotificationUtil;

import java.time.LocalDate;

public class HomeFormController {
    private final ReservationBO rBO = (ReservationBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.RESERVATION);
    public JFXComboBox<String> cmbStudentId;
    public JFXTextField txtStudentName;
    public JFXComboBox<String> cmbRoomId;
    public JFXTextField txtRoomType;
    public JFXTextField txtAvailableQty;
    public Label lblDate;
    public Label lblReservationID;
    public JFXButton btnReserve;
    public JFXButton btnCancel;
    public JFXTextField txtKeyMoney;
    public JFXRadioButton rdbxNow;
    public ToggleGroup grouped;
    public JFXRadioButton rdbxLater;

    public void reserveRoomOnAction(ActionEvent actionEvent) {
        try {
            if (cmbRoomId.getValue() != null && cmbStudentId.getValue() != null && (rdbxNow.isSelected() || rdbxLater.isSelected())) {
                String status = rdbxNow.isSelected() ? "Payed" : "Pending";
                if (rBO.saveReservation(new ReservationDTO(rBO.getReservationId(), LocalDate.now(), rBO.getStudentDetails(cmbStudentId.getValue()), rBO.getRoomDetails(cmbRoomId.getValue()), status))) {
                    NotificationUtil.playNotification(AnimationType.POPUP, "Reservation " + lblReservationID.getText() + " Successfully Recorded!", NotificationType.SUCCESS, Duration.millis(3000));
                    lblReservationID.setText(rBO.getReservationId());
                    btnCancel.fire();
                }else{
                    new Alert(Alert.AlertType.ERROR, "Complete ALl Fields!", ButtonType.OK).show();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    public void clearFormOnAction(ActionEvent actionEvent) {
        cmbStudentId.getSelectionModel().clearSelection();
        cmbRoomId.getSelectionModel().clearSelection();
        txtRoomType.clear();
        txtAvailableQty.clear();
        txtKeyMoney.clear();
        txtStudentName.clear();
        rdbxLater.setSelected(false);
        rdbxNow.setSelected(false);
    }

    public void initialize() {
        lblDate.setText(LocalDate.now() + "");
        try {
            lblReservationID.setText(rBO.getReservationId());
            cmbRoomId.getItems().addAll(rBO.getRoomIds());
            cmbStudentId.getItems().addAll(rBO.getStudentIds());

            cmbRoomId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    try {
                        setRoomFields(rBO.getRoomDetails(newValue));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            cmbStudentId.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    try {
                        setStudentFields(rBO.getStudentDetails(newValue));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    private void setStudentFields(StudentDTO student) {
        txtStudentName.setText(student.getName());
    }

    private void setRoomFields(RoomDTO room) {
        txtRoomType.setText(room.getType());
        txtAvailableQty.setText(room.getQty() + "");
        txtKeyMoney.setText(room.getKey_money());
    }
}
