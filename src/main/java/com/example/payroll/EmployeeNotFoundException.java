package com.example.payroll;

class EmployeeNotFoundException extends RuntimeException {

  EmployeeNotFoundException(Long id) {
    super("Could not find employee " + id);
  }

  EmployeeNotFoundException(String email) {
    super("Could not find employee with email " + email);
  }
}