package com.example.payroll.departmentService;

public class DepartmentNotFoundException extends RuntimeException {

  public DepartmentNotFoundException(Long id) {
    super("Could not find department " + id);
  }
  public DepartmentNotFoundException(String name) {
      super("Could not find department " + name);
    }
}