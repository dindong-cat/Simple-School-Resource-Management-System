/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

import java.time.*;

/**
 *
 * @author Home
 */
public class Booking {
    private int bookingId;
    private int accountId;
    private String accountName;
    private int resourceId;
    private String resourceName;
    private LocalDateTime bookingFrom;
    private LocalDateTime bookingTo;

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public LocalDateTime getBookingFrom() {
        return bookingFrom;
    }

    public void setBookingFrom(LocalDateTime bookingFrom) {
        this.bookingFrom = bookingFrom;
    }

    public LocalDateTime getBookingTo() {
        return bookingTo;
    }

    public void setBookingTo(LocalDateTime bookingTo) {
        this.bookingTo = bookingTo;
    }

    
    
}
