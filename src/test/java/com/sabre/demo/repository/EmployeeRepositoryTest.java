package com.sabre.demo.repository;

import com.sabre.demo.model.Employee;
import com.sabre.demo.service.EmployeeRepository;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeRepositoryTest {

    @InjectMocks
    private EmployeeRepository employeeRepository;

    @Test
    @Order(1)
    public void findAllEmployeesSuccessfully() {
        List<Employee> employees = employeeRepository.findAll();
        assertEquals(4, employees.size());
    }

    @Test
    @Order(2)
    public void findEmployeeByIdSuccessfully() {
        Optional<Employee> employee = employeeRepository.findById(1L);
        assertTrue(employee.isPresent());
        assertEquals("Mark", employee.get().getEmployeeName());
    }



    @Test
    @Order(3)
    public void saveEmployeeSuccessfully() {
        Employee newEmployee = new Employee(5L, "Alice", 100.0f);
        Employee savedEmployee = employeeRepository.save(newEmployee);
        assertEquals(newEmployee, savedEmployee);
        assertEquals(5, employeeRepository.findAll().size());
    }

    @Test
    @Order(4)
    public void deleteEmployeeSuccessfully() {
        employeeRepository.deleteById(1L);
        assertEquals(4, employeeRepository.findAll().size());
        assertFalse(employeeRepository.findById(1L).isPresent());
    }



}
