package com.example.payroll;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class LoadDatabase {

  private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

  @Bean
  CommandLineRunner initDatabase(EmployeeRepository repository, DepartmentRepository departmentRepository) {

    Department department = new Department("IT", "Bethlehem");
    Department department2 = new Department("HR", "Ramallah");
    departmentRepository.save(department);
    departmentRepository.save(department2);

    Employee employee = new Employee("Bilbo Baggins", "burglar", "bilbo@email.com");
    employee.setDepartment(department);
    department.addEmployee(employee);
    
    Employee employee2 = new Employee("Frodo Baggins", "thief", "frodo@email.com");
    employee2.setDepartment(department2);
    department2.addEmployee(employee2);


    return args -> {
      log.info("Preloading " + repository.save(employee));
      log.info("Preloading " + repository.save(employee2));
    };
  }
}