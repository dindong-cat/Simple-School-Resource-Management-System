/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Class;

/**
 *
 * @author Home
 */
public class SQL {
    final private String SQLName; 
    final private String SQLAccount; 
    final private String SQLPassword; 
    
    public SQL() {
        this.SQLName = "jdbc:mysql:///project_assignment";
        this.SQLAccount = "root";
        this.SQLPassword = "";
    }
    

    public String getSQLName() {
        return SQLName;
    }

    public String getSQLAccount() {
        return SQLAccount;
    }

    public String getSQLPassword() {
        return SQLPassword;
    }
    
}
