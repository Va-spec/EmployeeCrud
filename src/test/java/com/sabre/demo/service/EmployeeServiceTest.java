package com.sabre.demo.service;

import com.sabre.demo.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private Employee employee;

    @BeforeEach
    public void setUp() {
        employee = new Employee(1L, "Mark", 65.0f);
    }

    @Test
    public void testSaveEmployee() {
        Employee employee1 = employeeService.saveEmployee(employee);
        assertEquals(employee, employee1);
    }


    @Test
    public void fetchAllEmployeesSuccessfully() {
        List<Employee> employees = Arrays.asList(
                new Employee(1L, "John Doe", 50000.0f),
                new Employee(2L, "Jane Doe", 60000.0f)
        );
        given(employeeRepository.findAll()).willReturn(employees);
        List<Employee> fetchedEmployees = employeeService.fetchAllEmployees();
        assertEquals(employees, fetchedEmployees);
    }

    @Test
    public void getEmployeeByIdSuccessfully() {
        given(employeeRepository.findById(anyLong())).willReturn(Optional.of(employee));
        Employee fetchedEmployee = employeeService.getEmployeeById(1L);
        assertEquals(employee, fetchedEmployee);
    }



    @Test
    public void updateEmployeeSuccessfully() {
        Employee updatedEmployee = new Employee(1L, "John Smith", 55000.0f);
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        given(employeeRepository.save(any(Employee.class))).willReturn(updatedEmployee);
        Employee result = employeeService.updateEmployeeById(1L, updatedEmployee);
        assertEquals(updatedEmployee.getEmployeeName(), result.getEmployeeName());
        assertEquals(updatedEmployee.getEmployeeSalary(), result.getEmployeeSalary());
    }

    @Test
    public void deleteEmployeeSuccessfully() {
        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));
        String result = employeeService.deleteDepartmentById(1L);
        assertEquals("Employee deleted successfully", result);
    }


}
