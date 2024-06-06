/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_projek_pbo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author SABAR MARTUA TAMBA
 */
public class DBConnection {
    public static Connection connectDb(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection Conn = DriverManager.getConnection("jdbc:mysql://localhost:3308/final_projek_pbo", "root", "");
            return Conn;
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
