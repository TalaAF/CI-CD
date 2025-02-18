package com.example.payroll.employeeService;

class EmployeeNotFoundException extends RuntimeException {

  EmployeeNotFoundException(Long id) {
    super("Could not find employee " + id);
  }

  EmployeeNotFoundException(String email) {
    super("Could not find employee with email " + email);
  }
}