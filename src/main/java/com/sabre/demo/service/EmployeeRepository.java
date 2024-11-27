package com.sabre.demo.service;

import com.sabre.demo.model.Employee;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeRepository {
    private static List<Employee> allEmployees =new ArrayList<>(
            List.of(
                    new Employee(1L, "Mark", 65.0f),
                    new Employee(2L, "John", 75.0f),
                    new Employee(3L, "Jane", 85.0f),
                    new Employee(4L, "Doe", 95.0f)

            ));

    public List<Employee> findAll() {
        return allEmployees;
    }

    public Optional<Employee> findById(Long id) {
        return allEmployees.stream().filter(employee -> employee.getEmployeeId().equals(id)).findFirst();
    }

    public Employee save(Employee originalEmployee) {
        allEmployees.add(originalEmployee);
        return originalEmployee;
    }

    public void deleteById(Long id) {
        allEmployees.removeIf(employee -> employee.getEmployeeId().equals(id));

    }
}
