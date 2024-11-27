package controller;

import bo.BOFactory;
import bo.custom.KeyPaymentBO;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Duration;
import tray.animations.AnimationType;
import tray.notification.NotificationType;
import util.NotificationUtil;
import view.tm.KeyPaymentTM;

import java.util.stream.Collectors;

public class FindPendingKeyPaymentFormController {
    public TableView<KeyPaymentTM> tblKeyPayments;
    public TableColumn colResId;
    public TableColumn colStudentID;
    public TableColumn colStudentName;
    public TableColumn colRoomId;
    public TableColumn colRoomType;
    public TableColumn colStatus;
    public TableColumn colOption;

    private final KeyPaymentBO kBO = (KeyPaymentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.KEY_PAYMENTS);

    public void initialize() {
        colResId.setCellValueFactory(new PropertyValueFactory<>("resId"));
        colStudentID.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        colStudentName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colRoomId.setCellValueFactory(new PropertyValueFactory<>("room_Type_id"));
        colRoomType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colStatus.setCellValueFactory(new PropertyValueFactory<>("status"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));
        try {
            loadAllPendingKeyPayments();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadAllPendingKeyPayments() throws Exception {
        tblKeyPayments.setItems(FXCollections.observableArrayList(kBO.getPendingKeyPayments().stream().map(dto -> {
            return new KeyPaymentTM(dto.getResId(),dto.getStudentId(),dto.getName(),dto.getRoom_Type_id(),dto.getType(),dto.getStatus(),getButton(dto.getResId()));
        }).collect(Collectors.toList())));
    }

    private JFXButton getButton(String resId) {
        JFXButton btn = new JFXButton(" Pay ");
        btn.setStyle("-fx-border-color: Black");
        btn.setOnAction(event -> {
            try {
                if (new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure?", ButtonType.YES, ButtonType.NO).showAndWait().get().equals(ButtonType.YES)) {
                    if (kBO.updatePaymentStatus(resId)) {
                        NotificationUtil.playNotification(AnimationType.POPUP, "Reservation Status Updated Successfully!", NotificationType.SUCCESS, Duration.millis(3000));
                        loadAllPendingKeyPayments();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return btn;
    }
}
