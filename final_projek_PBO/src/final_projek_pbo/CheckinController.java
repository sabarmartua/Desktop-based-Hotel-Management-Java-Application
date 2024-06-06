/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_projek_pbo;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 *
 * @author SABAR MARTUA TAMBA
 */
public class CheckinController implements Initializable {

    @FXML
    private Button CheckinButton;

    @FXML
    private Button buttonClose;

    @FXML
    private DatePicker checkinField;

    @FXML
    private DatePicker checkoutField;

    @FXML
    private Label cidLabel;

    @FXML
    private Label codLabel;

    @FXML
    private Label customerNumber;

    @FXML
    private TextField emailField;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField phoneField;

    @FXML
    private Label phoneNoLabel;

    @FXML
    private Button receiptButton;

    @FXML
    private Button resetButton;

    @FXML
    private Label totalDay;

    @FXML
    private Label total;

    @FXML
    private ComboBox<?> roomNomer;

    @FXML
    private ComboBox<?> roomType;

    public void close() {
        System.exit(0);
    }

    private Connection Conn;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    public void CustomerNumber() {
        String customerNumberq = "SELECT customer_id FROM customer";
        Conn = DBConnection.connectDb();
        try {
            prepare = Conn.prepareStatement(customerNumberq);
            result = prepare.executeQuery();

            while (result.next()) {
                getData.customerNum = result.getInt("customer_id");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void dislplayCustomerNumber() {
        CustomerNumber();
        customerNumber.setText(String.valueOf(getData.customerNum + 1));
    }

    public void customerCheckin() {
        String insertCustomerData = "INSERT INTO customer (customer_id, name, roomType, roomNumber, phoneNumber, total, email, checkIn, checkOut) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        Conn = DBConnection.connectDb();

        try {
            String customerNum = customerNumber.getText();
            String roomTypes = (String) roomType.getSelectionModel().getSelectedItem();
            String roomN = (String) roomNomer.getSelectionModel().getSelectedItem();
            String Name = nameField.getText();
            String phoneNum = phoneField.getText();
            String email = emailField.getText();
            String checkinDate = String.valueOf(checkinField.getValue());
            String checkoutDate = String.valueOf(checkoutField.getValue());

            Alert alert;
            if (customerNum.isEmpty() || Name.isEmpty() || phoneNum.isEmpty() || email.isEmpty() || checkinDate.isEmpty() || checkoutDate.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Blank credentials");
                alert.showAndWait();
            } else {
                String totalC = String.valueOf(totalP);
                
                prepare = Conn.prepareStatement(insertCustomerData);
                prepare.setString(1, customerNum);
                prepare.setString(2, Name);
                prepare.setString(3, roomTypes);
                prepare.setString(4, roomN);
                prepare.setString(5, phoneNum);
                prepare.setString(6, totalC);
                prepare.setString(7, email);
                prepare.setString(8, checkinDate);
                prepare.setString(9, checkoutDate);

                prepare.executeUpdate();

                String date = String.valueOf(checkinField.getValue());

                String customerN = customerNumber.getText();

                String customerReciepts = "INSERT INTO customer_receipt (customer_id, date, total) VALUES(?, ?, ?)";
                
                prepare = Conn.prepareStatement(customerReciepts);
                prepare.setString(1, customerN);
                prepare.setString(2, date);
                prepare.setString(3, totalC);
                
                prepare.executeUpdate();
                String sqlEditStatus = "UPDATE room SET status = 'Occupied' WHERE roomNo = '" + roomN + "'";

                statement = Conn.createStatement();
                statement.executeUpdate(sqlEditStatus);

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully checked in!");
                alert.showAndWait();

                reset();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void totalDays() {

        Alert alert;

        if (checkoutField.getValue().isAfter(checkinField.getValue())) {
            getData.totalDays = checkoutField.getValue().compareTo(checkinField.getValue());
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Invaid Date");
            alert.showAndWait();
        }
    }

    private float totalP = 0;

    public void displayTotal() {
        totalDays();
        String totalD = String.valueOf(getData.totalDays);
        totalDay.setText(totalD);
        String selectItem = (String) roomNomer.getSelectionModel().getSelectedItem();
        String sql = "SELECT * FROM room WHERE roomNo = '" + selectItem + "'";
        double priceData = 0;

        Conn = DBConnection.connectDb();
        try {
            prepare = Conn.prepareStatement(sql);
            result = prepare.executeQuery();
            if (result.next()) {
                priceData = result.getDouble("price");
            }

            totalP = (float) ((priceData) * getData.totalDays);

            total.setText(String.valueOf(totalP));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void roomTypeList() {
        String listType = "SELECT * FROM room WHERE status = 'Available' GROUP BY type ORDER BY type ASC";

        Conn = DBConnection.connectDb();
        try {
            ObservableList listData = FXCollections.observableArrayList();
            prepare = Conn.prepareStatement(listType);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getString("type"));
            }
            roomType.setItems(listData);

            roomNumberList();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void roomNumberList() {

        String item = (String) roomType.getSelectionModel().getSelectedItem();

        String availableRoomNumber = "SELECT * FROM room WHERE type = '" + item + "'AND status = 'Available'  ORDER BY roomNo ASC";
        Conn = DBConnection.connectDb();

        try {
            ObservableList listData = FXCollections.observableArrayList();
            prepare = Conn.prepareStatement(availableRoomNumber);
            result = prepare.executeQuery();

            while (result.next()) {
                listData.add(result.getString("roomNo"));
            }

            roomNomer.setItems(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void reset() {
        nameField.setText("");
        phoneField.setText("");
        emailField.setText("");
        roomType.getSelectionModel().clearSelection();
        roomNomer.getSelectionModel().clearSelection();
        totalDay.setText("--");
        total.setText("0.00");

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dislplayCustomerNumber();
        roomTypeList();
        roomNumberList();
        reset();
    }

}
