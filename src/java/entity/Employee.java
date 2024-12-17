/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.time.LocalDate;

/**
 *
 * @author JidniVai
 */
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;
    //CREATE TABLE employee (
//    employee_id INT PRIMARY KEY,
//    first_name VARCHAR(100),
//    last_name VARCHAR(100),
//    email VARCHAR(100),
//    hire_date DATE,
//    salary DECIMAL(10,2),
//    department_id INT
//);
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate hireDate;
    private String stringDate;
    private float salary;
    private int departmentId;

    public Employee() {
        hireDate = LocalDate.now();
    }

    public Employee(int id, String firstName, String lastName, String email, LocalDate hireDate, float salary, int departmentId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.hireDate = hireDate;
        this.salary = salary;
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Employee{" + "id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", hireDate=" + hireDate + ", salary=" + salary + ", departmentId=" + departmentId + '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getHireDate() {
        return hireDate;
    }

    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }

    public float getSalary() {
        return salary;
    }

    public void setSalary(float salary) {
        this.salary = salary;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public String getStringDate() {
        return hireDate.toString();
    }

    public void setStringDate(String stringDate) {
        this.hireDate = LocalDate.parse(stringDate);
    }

}
