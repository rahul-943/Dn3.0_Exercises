package Emplyee_management_System;

import java.util.Scanner;

public class EmployeeManagementSystem {

    // Employee class
    public static class Employee {
        private String employeeId;
        private String name;
        private String position;
        private double salary;

        public Employee(String employeeId, String name, String position, double salary) {
            this.employeeId = employeeId;
            this.name = name;
            this.position = position;
            this.salary = salary;
        }

        public String getEmployeeId() { return employeeId; }
        public String getName() { return name; }
        public String getPosition() { return position; }
        public double getSalary() { return salary; }

        @Override
        public String toString() {
            return "Employee{" +
                    "employeeId='" + employeeId + '\'' +
                    ", name='" + name + '\'' +
                    ", position='" + position + '\'' +
                    ", salary=" + salary +
                    '}';
        }
    }

    // EmployeeManagementSystem class
    private Employee[] employees;
    private int size;

    public EmployeeManagementSystem(int capacity) {
        employees = new Employee[capacity];
        size = 0;
    }

    // Add an employee
    public void addEmployee(Employee employee) {
        if (size < employees.length) {
            employees[size++] = employee;
        } else {
            System.out.println("Employee array is full.");
        }
    }

    // Search for an employee by ID
    public Employee searchEmployeeById(String employeeId) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId().equals(employeeId)) {
                return employees[i];
            }
        }
        return null;
    }

    // Traverse and print all employees
    public void traverseEmployees() {
        for (int i = 0; i < size; i++) {
            System.out.println(employees[i]);
        }
    }

    // Delete an employee by ID
    public boolean deleteEmployeeById(String employeeId) {
        for (int i = 0; i < size; i++) {
            if (employees[i].getEmployeeId().equals(employeeId)) {
                // Shift elements to the left
                for (int j = i; j < size - 1; j++) {
                    employees[j] = employees[j + 1];
                }
                employees[--size] = null; // Decrease size and nullify last element
                return true;
            }
        }
        return false;
    }

    // Main method to handle user input
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        EmployeeManagementSystem ems = new EmployeeManagementSystem(10); // Capacity of 10

        while (true) {
            System.out.println("Employee Management System");
            System.out.println("1. Add Employee");
            System.out.println("2. Search Employee by ID");
            System.out.println("3. Traverse Employees");
            System.out.println("4. Delete Employee by ID");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter employee ID: ");
                    String empId = scanner.nextLine();
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter position: ");
                    String position = scanner.nextLine();
                    System.out.print("Enter salary: ");
                    double salary = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
                    ems.addEmployee(new Employee(empId, name, position, salary));
                    break;

                case 2:
                    System.out.print("Enter employee ID to search: ");
                    empId = scanner.nextLine();
                    Employee employee = ems.searchEmployeeById(empId);
                    if (employee != null) {
                        System.out.println("Employee found: " + employee);
                    } else {
                        System.out.println("Employee not found.");
                    }
                    break;

                case 3:
                    System.out.println("Employees:");
                    ems.traverseEmployees();
                    break;

                case 4:
                    System.out.print("Enter employee ID to delete: ");
                    empId = scanner.nextLine();
                    if (ems.deleteEmployeeById(empId)) {
                        System.out.println("Employee deleted.");
                    } else {
                        System.out.println("Employee not found.");
                    }
                    break;

                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }
        }
    }
}
