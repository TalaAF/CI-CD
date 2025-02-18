package com.example.payroll.employeeService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.example.payroll.departmentService.Department;
import com.example.payroll.departmentService.DepartmentNotFoundException;
import com.example.payroll.departmentService.DepartmentRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeRepository repository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private EmployeeModelAssembler assembler;

    @Override
    public CollectionModel<EntityModel<EmployeeDTO>> findAll() {
        List<EntityModel<EmployeeDTO>> employees = repository.findAll().stream() //
        .map(t -> EmployeeMapper.mapEmployeetoEmployeeDTO(t)) //
        .map(assembler::toModel) //
        .collect(Collectors.toList());
  
    return CollectionModel.of(employees, linkTo(methodOn(EmployeeController.class).all()).withSelfRel());
  
    }

    @Override
    public ResponseEntity<?> newEmployee(EmployeeDTO newEmployee) {
         Department dep = departmentRepository.findByName(newEmployee.getDepartmentName())
        .orElseThrow(() -> new DepartmentNotFoundException(newEmployee.getDepartmentName()));

    
    Employee employee = EmployeeMapper.mapEmployeeDTOtoEmployee(newEmployee, dep);
    employee = repository.save(employee);
    EntityModel<EmployeeDTO> entityModel = assembler.toModel(EmployeeMapper.mapEmployeetoEmployeeDTO(employee));

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);    
    }

    @Override
    public EntityModel<EmployeeDTO> findById(Long id) {        
        Employee employee = repository.findById(id) //
        .orElseThrow(() -> new EmployeeNotFoundException(id));

    return assembler.toModel(EmployeeMapper.mapEmployeetoEmployeeDTO(employee));
    }

    @Override
    public EntityModel<EmployeeDTO> findByEmail(String email) {
        Employee employee = repository.findByEmail(email) //
        .orElseThrow(() -> new EmployeeNotFoundException(email));
        return assembler.toModel(EmployeeMapper.mapEmployeetoEmployeeDTO(employee));
    }

    @Override
    public ResponseEntity<?> save(EmployeeDTO newEmployee, Long id) {
        Department dep = departmentRepository.findByName(newEmployee.getDepartmentName())
        .orElseThrow(() -> new DepartmentNotFoundException(newEmployee.getDepartmentName()));

    Employee newEmploye = EmployeeMapper.mapEmployeeDTOtoEmployee(newEmployee, dep);
    Employee updatedEmployee = repository.findById(id) //
        .map(employee -> {
          employee.setName(newEmployee.getName());
          employee.setRole(newEmployee.getRole());
          employee.setEmail(newEmployee.getEmail());
          employee.setDepartment(dep);
          return repository.save(employee);
        }) //
        .orElseGet(() -> {
          return repository.save(newEmploye);
        });

    EntityModel<EmployeeDTO> entityModel = assembler.toModel(EmployeeMapper.mapEmployeetoEmployeeDTO(updatedEmployee));

    return ResponseEntity //
        .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
        .body(entityModel);
    }

    @Override
    public ResponseEntity<?> deleteById(Long id) {
        repository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

}
