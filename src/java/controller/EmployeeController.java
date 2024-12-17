package controller;

import entity.Employee;
import java.time.LocalDate;
import java.util.Arrays;
import service.EmployeeService;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import java.util.List;
import java.util.Random;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 * Managed bean for handling Employee operations in a JSF application.
 *
 * @author JidniVai
 */
@ManagedBean(name = "employeeController")
@SessionScoped
public class EmployeeController {

    @ManagedProperty(value = "#{employeeService}")
    EmployeeService employeeService;

    private Employee employee;
    private List<Employee> employeeList; // Holds the list of employees to display

    public EmployeeService getEmployeeService() {
        return employeeService;
    }

    public void setEmployeeService(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Constructor initializes the employeeService and employee object.
     */
    public EmployeeController() {
//        this.employeeService = new EmployeeService();
        this.employee = new Employee();
    }

    /**
     * Initializes the employee list after the bean is constructed.
     */
    @PostConstruct
    public void init() {
        employeeService.init();
    }

    /**
     * Creates a new employee and refreshes the employee list.
     *
     * @return Navigation outcome.
     */
    public String createEmployee() {
        boolean success = employeeService.createEmployee(this.employee);
        if (success) {
            this.employee = new Employee(); // Reset the form
            return "index.xhtml?faces-redirect=true"; // Navigate to the employee list page
        } else {
            return "error?faces-redirect=true"; // Navigate to an error page
        }
    }

    public String add() {
        employee = new Employee();
        int s = employeeList.size();
        if (s == 0) {
            employee.setId(1);
        } else {
            Employee last = employeeList.get(s - 1);
            employee.setId(last.getId() + 1);
        }
        return "createEmployee.xhtml";
    }

    /**
     * Updates an existing employee and refreshes the employee list.
     *
     * @return Navigation outcome.
     */
    public String updateEmployee(Employee employee) {
        boolean success = employeeService.updateEmployee(employee);
        if (success) {
            this.employee = new Employee(); // Reset the form
            return "index.xhtml?faces-redirect=true"; // Navigate to the employee list page
        } else {
            return "error?faces-redirect=true"; // Navigate to an error page
        }
    }

    /**
     * Deletes an employee and refreshes the employee list.
     *
     * @param id The ID of the employee to delete.
     * @return Navigation outcome.
     */
    public String deleteEmployee(int id) {
        boolean success = employeeService.deleteEmployee(id);
        if (success) {
            return "index.xhtml?faces-redirect=true"; // Navigate to the employee list page
        } else {
            return "error.xhtml?faces-redirect=true"; // Navigate to an error page
        }
    }

    /**
     * Loads an employee's details for editing.
     *
     * @param id The ID of the employee to load.
     * @return Navigation outcome.
     */
    public String editEmployee(Integer id) {
        if (id == null) {
            id = 1;
        };
         employeeService.getEmployeeById(id);
        return "editEmployee.xhtml?faces-redirect=true"; // Navigate to the edit employee page
    }

    public String editEmployee2(int id) {
        employeeService.getEmployeeById(id);
        return "index.xhtml?faces-redirect=true"; // Navigate to the edit employee page
    }

    // Getters and Setters
    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Employee> getEmployeeList() {
        employeeList = employeeService.getAllEmployees();
//        System.out.println(Arrays.toString(employeeList.toArray()));
//        System.out.println("heyy");
        return employeeList;
    }

    public void setEmployeeList(List<Employee> employeeList) {
        this.employeeList = employeeList;
    }

    public String addRandomValues() {
        employeeService.init();
        List<Employee> employees = employeeService.getRamdomEmployees();
        for (Employee employee1 : employees) {
            employeeService.createEmployee(employee1);
        }
        return "index.xhtml?faces-redirect=true";
    }

    public String destroy() {
        employeeService.dropTable();
        return "index.xhtml?faces-redirect=true";
    }

    public static void main(String[] args) {

        EmployeeService employeeService = new EmployeeService();
        employeeService.init();

        System.out.println(Arrays.toString(employeeService.getAllEmployees().toArray()));
//        employeeService.init();
//
//        System.out.println("Sample data generation complete.");
    }
}
