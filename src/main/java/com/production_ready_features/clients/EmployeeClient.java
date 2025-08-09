package com.production_ready_features.clients;

import com.production_ready_features.DTO.EmployeeDTO;

import java.util.List;

public interface EmployeeClient {
    List<EmployeeDTO> getAllEmployee();

    EmployeeDTO getEmpById(Long id);

    EmployeeDTO createNewEmp(EmployeeDTO employeeDTO);
}
