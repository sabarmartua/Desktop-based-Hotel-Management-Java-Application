/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_projek_pbo;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author SABAR MARTUA TAMBA
 */
public class dashboardController implements Initializable {

    @FXML
    private Button CheckinButton;

    @FXML
    private Button ClearButton;

    @FXML
    private TableView<customerData> CustomerTable;

    @FXML
    private Button Customers;

    @FXML
    private Button DeleteButton;

    @FXML
    private Button MinimizeButton;

    @FXML
    private TableColumn<Room, String> PriceTable;

    @FXML
    private AnchorPane RoomBase;

    @FXML
    private Button addButton;

    @FXML
    private Button available;

    @FXML
    private AnchorPane base;

    @FXML
    private Button closeButton;

    @FXML
    private AnchorPane dashboardInfo;

    @FXML
    private AnchorPane dashboardbase;

    @FXML
    private AreaChart<?, ?> dataChart;

    @FXML
    private AnchorPane formField;

    @FXML
    private AnchorPane header;

    @FXML
    private Button home;

    @FXML
    private Button logout;

    @FXML
    private Label numberOfCustomer;

    @FXML
    private Label numberOfRoom;

    @FXML
    private Label numberTsBook;

    @FXML
    private TextField priceField;

    @FXML
    private Label pricelable;

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TextField roomidField;

    @FXML
    private TableColumn<Room, String> roomidTable;

    @FXML
    private Label roomlabel;

    @FXML
    private AnchorPane sidebar;

    @FXML
    private AnchorPane sidebar2;

    @FXML
    private ComboBox<?> statusField;

    @FXML
    private TableColumn<Room, String> statusTable;

    @FXML
    private Label statuslabel;

    @FXML
    private ComboBox<?> typeField;

    @FXML
    private TableColumn<Room, String> typeTable;

    @FXML
    private Label typelabel;

    @FXML
    private Button updateButton;
    @FXML
    private TableColumn<customerData, String> checkin;

    @FXML
    private TableColumn<customerData, String> checkout;

    @FXML
    private TableColumn<customerData, String> totalpayment;

    @FXML
    private TableColumn<customerData, String> phoneList;

    @FXML
    private TableColumn<customerData, String> nameList;

    @FXML
    private TableColumn<customerData, String> emailList;
    @FXML
    private TableColumn<customerData, String> customerNomer;

    private Connection Conn;
    private PreparedStatement prepare;
    private ResultSet result;
    
    public void dashboardCountBookToday(){
        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());
        
        String sql = "SELECT COUNT(id) FROM customer WHERE checkIn = '"+sqlDate+"'";
        Conn = DBConnection.connectDb();
        
        int count = 0;
        
        try{
            prepare = Conn.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                count = result.getInt("COUNT(id)");
            }
            
            numberTsBook.setText(String.valueOf(count));
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void dashboardCountRoom(){
        String sql = "SELECT COUNT(id) FROM room";
        Conn = DBConnection.connectDb();
        
        int count = 0;
        
        try{
            prepare = Conn.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                count = result.getInt("COUNT(id)");
            }
            
            numberOfRoom.setText(String.valueOf(count));
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void dashboardCountCustomer(){
        String sql = "SELECT COUNT(id) FROM customer";
        Conn = DBConnection.connectDb();
        
        int count = 0;
        
        try{
            prepare = Conn.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                count = result.getInt("COUNT(id)");
            }
            
            numberOfCustomer.setText(String.valueOf(count));
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void roomAdd() {
        String sqlCheck = "SELECT * FROM room WHERE roomNo = ?";
        String sqlInsert = "INSERT INTO room (roomNo, type, status, price) VALUES (?, ?, ?, ?)";

        Conn = DBConnection.connectDb();

        try {
            String roomNo = roomidField.getText();
            String type = (String) typeField.getSelectionModel().getSelectedItem();
            String status = (String) statusField.getSelectionModel().getSelectedItem();
            String price = priceField.getText();

            Alert alert;

            if (roomNo.isEmpty() || type.isEmpty() || status.isEmpty() || price.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all the fields!");
                alert.showAndWait();
            } else {
                // Check if roomNo already exists
                prepare = Conn.prepareStatement(sqlCheck);
                prepare.setString(1, roomNo);
                ResultSet resultSet = prepare.executeQuery();

                if (resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Room number already exists!");
                    alert.showAndWait();
                } else {
                    // If roomNo is unique, proceed with insertion
                    prepare = Conn.prepareStatement(sqlInsert);
                    prepare.setString(1, roomNo);
                    prepare.setString(2, type);
                    prepare.setString(3, status);
                    prepare.setString(4, price);

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully added!");
                    alert.showAndWait();

                    roomClearBtn();
                    availableRoomShowData();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void roomClearBtn() {
        roomidField.setText("");
        typeField.getSelectionModel().getSelectedItem();
        statusField.getSelectionModel().getSelectedItem();
        priceField.setText("");
    }

    private String type[]
            = {
                "Single Bed",
                "Double Bed",
                "Extra Bed",
                "King Size Bed",
                "Twin Kig Size Bed"
            };

    public void availableRoomsRoomType() {
        List<String> listData = new ArrayList<>();
        for (String data : type) {
            listData.add(data);
        }

        ObservableList list = FXCollections.observableArrayList(listData);
        typeField.setItems(list);
    }

    private String status[]
            = {
                "Available",
                "Not Available",
                "Occupied"
            };

    public void statusRoom() {
        List<String> listData = new ArrayList<>();
        for (String data : status) {
            listData.add(data);
        }

        ObservableList list = FXCollections.observableArrayList(listData);
        statusField.setItems(list);
    }

    public ObservableList<Room> availableRoomsData() {
        ObservableList<Room> listData = FXCollections.observableArrayList();
        String sql = "SELECT * From room";

        Conn = DBConnection.connectDb();

        try {
            Room roomList;

            prepare = Conn.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                roomList = new Room(result.getInt("roomNo"),
                        result.getString("type"),
                        result.getString("status"),
                        result.getDouble("price"));
                listData.add(roomList);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }

    private ObservableList<Room> roomDataList;

    public void availableRoomShowData() {
        roomDataList = availableRoomsData();
        roomidTable.setCellValueFactory(new PropertyValueFactory<>("roomNo"));
        typeTable.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        statusTable.setCellValueFactory(new PropertyValueFactory<>("status"));
        PriceTable.setCellValueFactory(new PropertyValueFactory<>("price"));

        roomTable.setItems(roomDataList);
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) base.getScene().getWindow();
        stage.setIconified(true);
    }

    private double x = 0;
    private double y = 0;

    public void logout() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);
            });

            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.show();

            logout.getScene().getWindow().hide();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableRoomsSelectData() {
        Room roomD = roomTable.getSelectionModel().getSelectedItem();
        int num = roomTable.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        roomidField.setText(String.valueOf(roomD.getRoomNo()));
        priceField.setText(String.valueOf(roomD.getPrice()));

    }

    public void roomUpdate() {
        String type1 = (String) typeField.getSelectionModel().getSelectedItem();
        String status1 = (String) statusField.getSelectionModel().getSelectedItem();
        String price1 = (String) priceField.getText();
        String roomNo1 = (String) roomidField.getText();

        String sqlCheck = "SELECT * FROM room WHERE roomNo = ?";
        String sqlUpdate = "UPDATE room SET type = ?, status = ?, price = ? WHERE roomNo = ?";

        Conn = DBConnection.connectDb();

        try {
            Alert alert;
            if (type1.isEmpty() || status1.isEmpty() || price1.isEmpty() || roomNo1.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select the data first!");
                alert.showAndWait();
            } else {
                // Check if roomNo already exists
                prepare = Conn.prepareStatement(sqlCheck);
                prepare.setString(1, roomNo1);
                ResultSet resultSet = prepare.executeQuery();

                if (!resultSet.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Room number does not exist!");
                    alert.showAndWait();
                } else {
                    // If roomNo exists, proceed with update
                    prepare = Conn.prepareStatement(sqlUpdate);
                    prepare.setString(1, type1);
                    prepare.setString(2, status1);
                    prepare.setString(3, price1);
                    prepare.setString(4, roomNo1);

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully updated!");
                    alert.showAndWait();

                    availableRoomShowData();
                    roomClearBtn();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void roomDelete() {
        String roomNo = roomidField.getText();
        String sqlDelete = "DELETE FROM room WHERE roomNo = ?";

        Conn = DBConnection.connectDb();

        try {
            Alert alert;
            if (roomNo.isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please select a room to delete!");
                alert.showAndWait();
            } else {
                // Confirm deletion
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to delete this room?");

                // Get user's choice
                if (alert.showAndWait().get() == ButtonType.OK) {
                    prepare = Conn.prepareStatement(sqlDelete);
                    prepare.setString(1, roomNo);

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Room deleted successfully!");
                    alert.showAndWait();

                    availableRoomShowData();
                    roomClearBtn();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void availableRoomsCheckin() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("checkIn.fxml"));
            Stage stage = new Stage();
            Scene scene = new Scene(root);

            root.setOnMousePressed((MouseEvent event) -> {
                x = event.getSceneX();
                y = event.getSceneY();
            });

            root.setOnMouseDragged((MouseEvent event) -> {
                stage.setX(event.getScreenX() - x);
                stage.setY(event.getScreenY() - y);

                stage.setOpacity(.8);
            });

            root.setOnMouseReleased((MouseEvent event) -> {
                stage.setOpacity(1);

            });

            stage.initStyle(StageStyle.TRANSPARENT);

            stage.setScene(scene);
            stage.show();

            CheckinButton.getScene().getWindow().hide();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ObservableList<customerData> customerListData() {
        ObservableList<customerData> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM customer ";

        Conn = DBConnection.connectDb();

        try {
            prepare = Conn.prepareStatement(sql);
            result = prepare.executeQuery();

            customerData custD;

            while (result.next()) {
                custD = new customerData(result.getInt("customer_id"),
                        result.getString("name"),
                        result.getString("phoneNumber"),
                        result.getDouble("total"),
                        result.getDate("checkIn"),
                        result.getDate("checkOut"),
                        result.getString("email")
                );

                listData.add(custD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return listData;
    }
    private ObservableList<customerData> listCustomerData;

    public void customerShowData() {
        listCustomerData = customerListData();

        customerNomer.setCellValueFactory(new PropertyValueFactory<>("customerNumber"));
        nameList.setCellValueFactory(new PropertyValueFactory<>("Name"));
        phoneList.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        totalpayment.setCellValueFactory(new PropertyValueFactory<>("total"));
        checkin.setCellValueFactory(new PropertyValueFactory<>("checkin"));
        checkout.setCellValueFactory(new PropertyValueFactory<>("checkout"));
        emailList.setCellValueFactory(new PropertyValueFactory<>("email"));

        CustomerTable.setItems(listCustomerData);

    }
    
    public void switchForm(ActionEvent event){
        if(event.getSource() ==  home){
            dashboardbase.setVisible(true);
            RoomBase.setVisible(false);
            CustomerTable.setVisible(false);
            dashboardCountBookToday();
            dashboardCountRoom();
            dashboardCountCustomer();
            dashboardChart();
            
        }else if(event.getSource() ==  available){
            dashboardbase.setVisible(false);
            RoomBase.setVisible(true);
            CustomerTable.setVisible(false);
            
            availableRoomShowData();
            
        }else if(event.getSource() ==  Customers){
            dashboardbase.setVisible(false);
            RoomBase.setVisible(false);
            CustomerTable.setVisible(true);
            
            customerShowData();
            
        }
    }
    
    public void dashboardChart(){
        dataChart.getData().clear();
        
        String sql = "SELECT date, customer_id FROM customer_receipt GROUP BY date ORDER BY TIMESTAMP(date) ASC LIMIT 8";
        
        Conn = DBConnection.connectDb();
        
        XYChart.Series chart = new XYChart.Series();
        
        try{
            prepare = Conn.prepareStatement(sql);
            result = prepare.executeQuery();
            
            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }
            
         dataChart.getData().add(chart);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        availableRoomsRoomType();
        statusRoom();
        roomClearBtn();

        availableRoomShowData();
        customerListData();
        customerShowData();
        dashboardCountBookToday();
        dashboardCountRoom();
        dashboardCountCustomer();
        dashboardChart();
        dashboardChart();
    }

}
