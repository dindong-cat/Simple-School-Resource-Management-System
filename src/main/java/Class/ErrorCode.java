/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

/**
 *
 * @author Home
 */
public enum ErrorCode {
    
    DuplicateAccountName("400", "There is an existing account already"),
    DuplicateResourceName("401", "There is an existing resource already"),
    OverlappedBooking("402", "There is another booking in this time slot")
    ; 
    
    private final String code;
    private final String description;
    
    private ErrorCode(String code, String description) {
        this.code = code;
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
