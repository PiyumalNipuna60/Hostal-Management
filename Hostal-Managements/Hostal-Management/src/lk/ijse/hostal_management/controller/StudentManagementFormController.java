package lk.ijse.hostal_management.controller;

import bo.BOFactory;
import bo.custom.StudentBO;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dto.StudentDTO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import view.tm.StudentTM;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class StudentManagementFormController {
    private final StudentBO sBO = (StudentBO) BOFactory.getInstance().getBO(BOFactory.BOTypes.STUDENT);
    private final LinkedHashMap<JFXTextField, Pattern> RegexMap = new LinkedHashMap<>();
    public JFXTextField txtStudentID;
    public JFXTextField txtAddress;
    public JFXTextField txtStudentName;
    public TableView<StudentTM> tblStudent;
    public TableColumn colId;
    public TableColumn colName;
    public TableColumn colAddress;
    public TableColumn colMobile;
    public TableColumn colDOB;
    public TableColumn colGender;
    public TableColumn colOption;
    public JFXTextField txtContactNo;
    public JFXDatePicker dtpckrDOB;
    public JFXComboBox<String> cmbGender;
    public JFXButton btnAddStudent;
    public JFXButton btnCancel;
    public JFXTextField txtSearch;

    public void addStudentOnAction(ActionEvent actionEvent) {
        try {

            if (dtpckrDOB.getValue() != null && dtpckrDOB.getValue().isBefore(LocalDate.now().minusYears(18))) {
                if (btnAddStudent.getText().equals("Add Student")) {
                    if (cmbGender.getValue() != null) {
                        if (sBO.saveStudent(new StudentDTO(txtStudentID.getText(), txtStudentName.getText(), txtAddress.getText(), txtContactNo.getText(), dtpckrDOB.getValue(), cmbGender.getValue()))) {
                            NotificationUtil.playNotification(AnimationType.POPUP, "Student Saved Successfully!", NotificationType.SUCCESS, Duration.millis(3000));
                            btnCancel.fire();
                            loadStudents(sBO.getAllStudents());
                        } else {
                            NotificationUtil.playNotification(AnimationType.POPUP, "Something Went Wrong !", NotificationType.ERROR, Duration.millis(3000));
                        }
                    } else {
                        new Alert(Alert.AlertType.ERROR, "Gender Not Selected!", ButtonType.OK).show();
                    }
                } else {
                    if (sBO.updateStudent(new StudentDTO(txtStudentID.getText(), txtStudentName.getText(), txtAddress.getText(), txtContactNo.getText(), dtpckrDOB.getValue(), cmbGender.getValue()))) {
                        NotificationUtil.playNotification(AnimationType.POPUP, "Student Updated Successfully!", NotificationType.SUCCESS, Duration.millis(3000));
                        btnCancel.fire();
                        loadStudents(sBO.getAllStudents());
                    } else {
                        NotificationUtil.playNotification(AnimationType.POPUP, "Something Went Wrong !", NotificationType.ERROR, Duration.millis(3000));
                    }
                }

            } else {
                new Alert(Alert.AlertType.ERROR, "Date Of Birth Is Not Selected Or You Are Not Over 18 Years!", ButtonType.OK).show();

            }
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    public void clearFormOnAction(ActionEvent actionEvent) {
        btnAddStudent.setText("Add Student");
        btnAddStudent.setDisable(true);
        resetFields(txtStudentID,txtStudentName,txtAddress,txtContactNo);
        dtpckrDOB.setValue(null);
        cmbGender.getSelectionModel().clearSelection();
        tblStudent.getSelectionModel().clearSelection();
    }
    public void resetFields(JFXTextField... fields) {
        for (JFXTextField field : fields) {
            field.getParent().setStyle("-fx-border-color :   #EDEDF0;" + "-fx-border-width:1.5;" + "-fx-border-radius:  5;" + "-fx-background-radius:  5;");
            field.clear();
        }
    }

    public void SearchMatching(KeyEvent keyEvent) {
        try {
            loadStudents(sBO.getMatchingStudents(txtSearch.getText()));
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }
    }

    public void initialize() {
        btnAddStudent.setDisable(true);
        cmbGender.getItems().addAll("Male", "Female", "Rather Not Specify", "Custom");
        colId.setCellValueFactory(new PropertyValueFactory<>("StudentId"));
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
        colMobile.setCellValueFactory(new PropertyValueFactory<>("ContactNo"));
        colDOB.setCellValueFactory(new PropertyValueFactory<>("dob"));
        colGender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        colOption.setCellValueFactory(new PropertyValueFactory<>("btn"));

        try {
            loadStudents(sBO.getAllStudents());
        } catch (Exception e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
        }

        tblStudent.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                setUpdatefields(newValue);
            }
        });
        RegexMap.put(txtStudentID, Pattern.compile("^[A-z 0-9-]+$"));
        RegexMap.put(txtStudentName, Pattern.compile("^[A-z ]+$"));
        RegexMap.put(txtAddress, Pattern.compile("^[A-z1-9 /,.-]+$"));
        RegexMap.put(txtContactNo, Pattern.compile("^[0-9]{10,11}$"));
    }

    private void setUpdatefields(StudentTM tm) {
        btnAddStudent.setDisable(false);
        btnAddStudent.setText("Update Student");
        txtStudentID.setText(tm.getStudentId());
        txtStudentName.setText(tm.getName());
        txtAddress.setText(tm.getAddress());
        txtContactNo.setText(tm.getContactNo());
        dtpckrDOB.setValue(tm.getDob());
        cmbGender.getSelectionModel().select(tm.getGender());
    }

    private void loadStudents(ArrayList<StudentDTO> students) {
        tblStudent.setItems(FXCollections.observableArrayList(students.stream().map(dto -> {
            return new StudentTM(dto.getStudentId(), dto.getName(), dto.getAddress(), dto.getContactNo(), dto.getDob(), dto.getGender(), getButton(dto.getStudentId()));
        }).collect(Collectors.toList())));
    }

    private JFXButton getButton(String id) {
        JFXButton btn = new JFXButton("Delete");
        btn.setStyle("-fx-border-color: Black");
        btn.setOnAction(event -> {
            if (new Alert(Alert.AlertType.CONFIRMATION, "Are You Sure ?", ButtonType.YES,ButtonType.NO).showAndWait().get().equals(ButtonType.YES)) {
                try {
                    sBO.deleteStudent(id);
                    NotificationUtil.playNotification(AnimationType.POPUP, "Student " + id + " Deleted Successfully!", NotificationType.SUCCESS, Duration.millis(3000));
                    loadStudents(sBO.getAllStudents());
                } catch (Exception e) {
                    e.printStackTrace();
                    new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK).show();
                }
            }
        });
        return btn;
    }

    public void validateFieldsOnKeyRelease(KeyEvent keyEvent) {
        Object validate = ValidationUtil.Validate(RegexMap, btnAddStudent);
        if (keyEvent.getCode().equals(KeyCode.ENTER)) {
            if (validate instanceof JFXTextField) {
                ((JFXTextField) validate).requestFocus();
            } else {
                btnAddStudent.fire();
            }
        }
    }
}
