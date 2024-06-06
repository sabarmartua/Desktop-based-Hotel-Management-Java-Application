/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package final_projek_pbo;

import java.util.Date;

/**
 *
 * @author SABAR MARTUA TAMBA
 */
public class customerData {
    private Integer customerNumber;
    private String Name;
    private String phoneNumber;
    private String email;
    private Date checkin;
    private Date checkout;
    private Double total;
    
    
    public customerData(Integer customerNumber, String Name, String phoneNumber, Double total, Date checkin, Date checkout, String email){
        this.customerNumber = customerNumber;
        this.Name = Name;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.checkin = checkin;
        this.checkout = checkout;
        this.total = total;
    }
    public Integer getCustomerNumber() {
        return customerNumber;
    }

    // Getter for Name
    public String getName() {
        return Name;
    }

    // Getter for phoneNumber
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Getter for checkin
    public Date getCheckin() {
        return checkin;
    }

    // Getter for checkout
    public Date getCheckout() {
        return checkout;
    }
        public Double getTotal() {
        return total;
    }
}
