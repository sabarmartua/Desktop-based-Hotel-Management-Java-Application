/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_projek_pbo;

/**
 *
 * @author SABAR MARTUA TAMBA
 */
public class Room {
    private Integer roomNo;
    private String roomType;
    private String status;
    private Double price;
    
    public Room(Integer roomNo, String roomType, String status, Double price){
        this.roomNo= roomNo;
        this.roomType = roomType;
        this.status = status;
        this.price = price;
    }
    
    public Integer getRoomNo(){
        return roomNo;
    }
    
    public String getRoomType(){
        return roomType;
    }
    public String getStatus(){
        return status;
    }    
    public Double getPrice(){
        return price;
    }    
}
