/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import dao.DBUtills;
import entity.Employee;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author JidniVai
 */
@ManagedBean(name = "employeeService", eager = true)
@SessionScoped
public class EmployeeService {

    public EmployeeService() {
    }

    ArrayList employees;
    private Map<String, Object> sessionMap = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
    Connection connection;

    public ArrayList getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList employees) {
        this.employees = employees;
    }

    public Map<String, Object> getSessionMap() {
        return sessionMap;
    }

    public void setSessionMap(Map<String, Object> sessionMap) {
        this.sessionMap = sessionMap;
    }

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public void init() {
        String query = " CREATE TABLE IF NOT EXISTS employee ("
                + "    employee_id INT PRIMARY KEY AUTO_INCREMENT,"
                + "    first_name VARCHAR(100),"
                + "    last_name VARCHAR(100),"
                + "    email VARCHAR(100),"
                + "    hire_date DATE,"
                + "    salary DECIMAL(10,2),"
                + "    department_id INT"
                + ");";
        try {
            connection = DBUtills.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Create a new employee record in the database.
     *
     * @param employee The employee object to be inserted.
     * @return true if the record was inserted successfully, false otherwise.
     */
    public boolean createEmployee(Employee employee) {
        PreparedStatement preparedStatement = null;
        String query = "INSERT INTO employee ( first_name, last_name, email, hire_date, salary, department_id) "
                + "VALUES (  ?, ?, ?, ?, ?, ?)";
        try {
            connection = DBUtills.getConnection();
            preparedStatement = connection.prepareStatement(query);

//            preparedStatement.setInt(1, employee.getId());
            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setDate(4, Date.valueOf(employee.getHireDate()));
            preparedStatement.setFloat(5, employee.getSalary());
            preparedStatement.setInt(6, employee.getDepartmentId());

            boolean b = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            connection.close();
            return b;
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logging in production
            return false;
        } finally {

            DBUtills.close(connection, preparedStatement, null);

        }
    }

    /**
     * Retrieve a specific employee by their ID.
     *
     * @param id The ID of the employee.
     * @return The Employee object, or null if not found.
     */
    public void getEmployeeById(int id) {
        System.out.println("this is from service, getbyid, value = " + id);
        String query = "SELECT * FROM employee WHERE employee_id = ?";
        try {
            connection = DBUtills.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
//            preparedStatement.close();
//            connection.close();
            if (resultSet.next()) {
                Employee emp = mapToEmployee(resultSet);
                System.out.println(emp);

                FacesContext facesContext = FacesContext.getCurrentInstance();
                if (facesContext != null) {
                    sessionMap = facesContext.getExternalContext().getSessionMap();
                    if (sessionMap != null) {
                        sessionMap.put("selectedEmployee", emp);
                    } else {
                        System.out.println("Session map is null.");
                    }
                } else {
                    System.out.println("FacesContext is null.");
                }

//                sessionMap.put("selectedEmployee", emp);
                System.out.println("mapped");
            }
        } catch (SQLException e) {
            System.out.println(e.getCause().toString()); // Replace with logging in production
        }
    }

    /**
     * Retrieve all employees from the database.
     *
     * @return A list of all Employee objects.
     */
    public List<Employee> getAllEmployees() {
        String query = "SELECT * FROM employee";
        employees = new ArrayList<>();

        try {
//            connection.close();
            connection = DBUtills.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                employees.add(mapToEmployee(resultSet));
            }
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logging in production
        }

//        if(employees.size()==0){
//            List<Employee> randomEmployees = getRamdomEmployees();
//            for (Employee randomEmployee : randomEmployees) {
//                createEmployee(randomEmployee);
//            }
//            System.out.println("Added random employees");
////            return getAllEmployees();
//        }
        return employees;

//        return employees.size()==0 ? getRamdomEmployees(): employees;
    }

    /**
     * Update an existing employee record in the database.
     *
     * @param employee The employee object containing updated data.
     * @return true if the record was updated successfully, false otherwise.
     */
    public boolean updateEmployee(Employee employee) {
        String query = "UPDATE employee "
                + "SET first_name = ?, "
                + "last_name = ?, "
                + "email = ?,"
                + " hire_date = ?, "
                + "salary = ?, "
                + "department_id = ? "
                + "WHERE employee_id = ?";
        try {
            connection = DBUtills.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, employee.getFirstName());
            preparedStatement.setString(2, employee.getLastName());
            preparedStatement.setString(3, employee.getEmail());
            preparedStatement.setDate(4, Date.valueOf(employee.getHireDate()));
            preparedStatement.setFloat(5, employee.getSalary());
            preparedStatement.setInt(6, employee.getDepartmentId());
            preparedStatement.setInt(7, employee.getId());

            boolean b = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            connection.close();
            return b;
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logging in production
            return false;
        }
    }

    /**
     * Delete an employee record from the database.
     *
     * @param id The ID of the employee to be deleted.
     * @return true if the record was deleted successfully, false otherwise.
     */
    public boolean deleteEmployee(int id) {
        String query = "DELETE FROM employee WHERE employee_id = ?";
        try {
            connection = DBUtills.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            preparedStatement.setInt(1, id);

            boolean b = preparedStatement.executeUpdate() > 0;
            preparedStatement.close();
            connection.close();
            return b;
        } catch (SQLException e) {
            e.printStackTrace(); // Replace with logging in production
            return false;
        }
    }

    /**
     * Maps a ResultSet row to an Employee object.
     *
     * @param resultSet The result set containing the data.
     * @return An Employee object.
     * @throws SQLException If any SQL error occurs.
     */
    private Employee mapToEmployee(ResultSet resultSet) throws SQLException {
        return new Employee(
                resultSet.getInt("employee_id"),
                resultSet.getString("first_name"),
                resultSet.getString("last_name"),
                resultSet.getString("email"),
                resultSet.getDate("hire_date").toLocalDate(),
                resultSet.getFloat("salary"),
                resultSet.getInt("department_id")
        );
    }

    public List<Employee> getRamdomEmployees() {
        Random random = new Random();
        List<Employee> employeeList = new ArrayList<>();

        // Array of sample names and departments
        String[] firstNames = {"John", "Jane", "Michael", "Sarah", "David", "Emily", "Robert", "Jessica", "James", "Laura"};
        String[] lastNames = {"Smith", "Johnson", "Brown", "Williams", "Jones", "Garcia", "Miller", "Davis", "Martinez", "Hernandez"};
        int[] departmentIds = {101, 102, 103, 104, 105};

        // Generate 20 sample employees
        for (int i = 1; i <= 20; i++) {
            String firstName = firstNames[random.nextInt(firstNames.length)];
            String lastName = lastNames[random.nextInt(lastNames.length)];
            String email = firstName.toLowerCase() + "." + lastName.toLowerCase() + i + "@example.com";
            LocalDate hireDate = LocalDate.of(
                    random.nextInt(5) + 2015, // Year between 2015 and 2020
                    random.nextInt(12) + 1, // Month between 1 and 12
                    random.nextInt(28) + 1 // Day between 1 and 28
            );
            float salary = Float.valueOf(random.nextInt(50000) + 30000); // Salary between 30,000 and 80,000
            int departmentId = departmentIds[random.nextInt(departmentIds.length)];

            Employee employee = new Employee(i, firstName, lastName, email, hireDate, salary, departmentId);
//            boolean success = employeeService.createEmployee(employee);
            employeeList.add(employee);

        }
        return employeeList;
    }

    public void dropTable() {
        String query = " Drop table employee;";
        try {
            connection = DBUtills.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
            preparedStatement.close();
            connection.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

}
