package com.example.payroll;

import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
class Employee {

  private @Id @GeneratedValue Long id;
  private String name;
  private String role;
  private String email;

  @ManyToOne
  @JoinColumn(name = "department_id", nullable = false)
  private Department department;

  Employee() {
  }

  Employee(String name, String role, String email) {

    this.name = name;
    this.role = role;
    this.email = email;
  }

  public Long getId() {
    return this.id;
  }

  public String getName() {
    return this.name;
  }

  public String getRole() {
    return this.role;
  }

  public String getEmail() {
    return email;
  }

  public Department getDepartment() {
      return department;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setRole(String role) {
    this.role = role;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setDepartment(Department department) {
      this.department = department;
  }

  @Override
  public boolean equals(Object o) {

    if (this == o)
      return true;
    if (!(o instanceof Employee))
      return false;
    Employee employee = (Employee) o;
    return Objects.equals(this.id, employee.id) && Objects.equals(this.name, employee.name)
        && Objects.equals(this.role, employee.role) && Objects.equals(this.email, employee.email);
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.id, this.name, this.role, this.email);
  }

  @Override
  public String toString() {
    return "Employee{" + "id=" + this.id
        + ", name='" + this.name + '\''
        + ", role='" + this.role + '\''
        + ", email='" + this.email + '\''
         + ", department='" + this.department.getName() + '\''
        + '}';
  }
}
