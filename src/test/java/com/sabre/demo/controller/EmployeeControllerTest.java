package com.sabre.demo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sabre.demo.model.Employee;
import com.sabre.demo.service.EmployeeService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class EmployeeControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;
    private Employee employee;
    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        employee = new Employee(1L, "Mark", 65.0f);
    }

    @Test
    @Order(1)
    public void testSaveEmployee() throws Exception {
        when(employeeService.saveEmployee(any(Employee.class))).thenReturn(employee);
        ResultActions response = mockMvc.perform(post("/employee/save")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));
        Mockito.verify(employeeService, Mockito.times(1)).saveEmployee(any(Employee.class));

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId", is(1)))
                .andExpect(jsonPath("$.employeeName", is("Mark")))
                .andExpect(jsonPath("$.employeeSalary", is(65.0)));
    }

    @Test
    @Order(2)
    public void testGetAllEmployees() throws Exception {
        given(employeeService.fetchAllEmployees()).willReturn(java.util.List.of(employee));
        ResultActions response = mockMvc.perform(get("/employee/getAll")
                .contentType(MediaType.APPLICATION_JSON));
//        ResultActions response = mockMvc.perform(post("/employee/getAll"));
//        Mockito.verify(employeeService, Mockito.times(1)).fetchAllEmployees();

        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].employeeId", is(1)))
                .andExpect(jsonPath("$[0].employeeName", is("Mark")))
                .andExpect(jsonPath("$[0].employeeSalary", is(65.0)));
    }

    @Test
    @Order(3)
    public void testGetEmployeeById() throws Exception {
        given(employeeService.getEmployeeById(anyLong())).willReturn(employee);
        ResultActions response = mockMvc.perform(get("/employee/getById/1")
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId", is(1)))
                .andExpect(jsonPath("$.employeeName", is("Mark")))
                .andExpect(jsonPath("$.employeeSalary", is(65.0)));

    }

    @Test
    @Order(4)
    public void testUpdateEmployee() throws Exception {
        given(employeeService.updateEmployeeById(anyLong(), any(Employee.class))).willReturn(employee);
        ResultActions response = mockMvc.perform(put("/employee/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.employeeId", is(1)))
                .andExpect(jsonPath("$.employeeName", is("Mark")))
                .andExpect(jsonPath("$.employeeSalary", is(65.0)));
    }

    @Test
    @Order(5)
    public void deleteEmployee() throws Exception {
        given(employeeService.deleteDepartmentById(anyLong())).willReturn("Employee deleted successfully");
        ResultActions response = mockMvc.perform(delete("/employee/delete/1")
                .contentType(MediaType.APPLICATION_JSON));
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is("Employee deleted successfully")));
    }


}
