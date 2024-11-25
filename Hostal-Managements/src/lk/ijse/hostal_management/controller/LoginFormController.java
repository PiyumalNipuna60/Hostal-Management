package lk.ijse.hostal_management.controller;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class LoginFormController {
    public JFXTextField txtUserName;
    public AnchorPane LoginContext;
    public JFXPasswordField txtPassword;
    public CheckBox checkBox;
    public PasswordField pwdPasswordField;
    public Label lblHide;

    public void LoginOnAction(ActionEvent actionEvent) throws IOException {
        if (txtUserName.getText().equalsIgnoreCase("") && txtPassword.getText().equals("")) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/DashBoardForm.fxml"));
            Parent parent=loader.load();
            Scene scene=new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.show();
            Stage stage1= (Stage)LoginContext.getScene().getWindow();
            stage1.close();
        }else{
            new Alert(Alert.AlertType.ERROR, "Invalid User Name Or Password.Please Try Again" ).show();
        }
    }

    public void showPasswordOnMousePressed(javafx.scene.input.MouseEvent mouseEvent) {
        Image img = new Image("view/assets/show.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(30);
        view.setFitWidth(30);
        lblHide.setGraphic(view);

        txtPassword.setStyle("-fx-background-color:white ");
        txtPassword.setPromptText(txtPassword.getText());
        txtPassword.setText("");
        txtPassword.setDisable(true);

    }

    /* --------------------------------------------HIDE PASSWORD----------------------------------------------------*/

    public void hidePasswordOnMousePressed(javafx.scene.input.MouseEvent mouseEvent) {
        Image img = new Image("view/assets/hide.png");
        ImageView view = new ImageView(img);
        view.setFitHeight(30);
        view.setFitWidth(30);
        lblHide.setGraphic(view);

        txtPassword .setText(txtPassword.getPromptText());
        txtPassword.setPromptText("");
        txtPassword.setDisable(false);
        txtPassword.setStyle("-fx-control-inner-background:#ffffff ");

    }

    public void checkBoxOnAction(ActionEvent actionEvent) {
        if (checkBox.isSelected()) {
            txtPassword.setText(pwdPasswordField.getText());
            System.out.println("Select");
            pwdPasswordField.setVisible(false);
            txtPassword.setVisible(true);
        } else {
            System.out.println("not");
            pwdPasswordField.setText(txtPassword.getText());
            pwdPasswordField.setVisible(true);
            txtPassword.setVisible(false);
        }
    }

//    public void hidePasswordOnMousePressed(javafx.scene.input.MouseEvent mouseEvent) {
//    }

//    public void showPasswordOnMousePressed(javafx.scene.input.MouseEvent mouseEvent) {
//    }
}
