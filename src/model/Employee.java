/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import main.Logable;

/**
 *
 * @author admin
 */
public class Employee extends Person implements Logable{
    
    private int employeeld;
    private String password;
    
    
    public static final int EMPLOYEE_ID = 123;
    public static final String PASSWORD = "test";
    
    

    
    public Employee(String name) {
        super(name);
        
    }

    
    

    public int getEmployeeld() {
        return employeeld;
    }

    public void setEmployeeld(int employeeld) {
        this.employeeld = employeeld;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public void login (){
        
    }

    @Override
    public boolean login(int user, String password) {
        if(user == EMPLOYEE_ID && password.equals(PASSWORD)){
            return true;
            
        } else{
            return false;
        }
    }
}
    
   
    

