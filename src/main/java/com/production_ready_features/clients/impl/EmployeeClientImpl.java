package com.production_ready_features.clients.impl;

import com.production_ready_features.DTO.EmployeeDTO;
import com.production_ready_features.advice.ApiResponse;
import com.production_ready_features.clients.EmployeeClient;
import com.production_ready_features.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeClientImpl implements EmployeeClient {

    private final RestClient restClient;

    Logger logger= LoggerFactory.getLogger(EmployeeClientImpl.class);

    @Override
    public List<EmployeeDTO> getAllEmployee() {
        try {
            ResponseEntity<ApiResponse<List<EmployeeDTO>>> employees = restClient.get()
                    .uri("employees")
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res) ->{
                        System.out.println("Error occurred "+res.getBody().readAllBytes());
                    })
                    /*.body(new ParameterizedTypeReference<>() {
                    });*/
                    .toEntity(new ParameterizedTypeReference<>() {
                    });
                    logger.info("Successfully get All Empoyees");

            HttpStatusCode statusCode = employees.getStatusCode();
            HttpHeaders headers = employees.getHeaders();
            return employees.getBody().getData();
        } catch (Exception e) {
            logger.error("Something went wrong : ",e);
            throw new RuntimeException(e);
        }

    }

    @Override
    public EmployeeDTO getEmpById(Long empId) {
        try {
            ApiResponse<EmployeeDTO> employees = restClient.get()
                    .uri("employees/{empId}", empId)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res) ->{
                        System.out.println("Error occurred "+res.getBody().readAllBytes());
                    })
                    .body(new ParameterizedTypeReference<>() {
                    });
            return employees.getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public EmployeeDTO createNewEmp(EmployeeDTO employeeDTO) {
        try {
            ApiResponse<EmployeeDTO>  employees = restClient.post()
                    .uri("employees")
                    .body(employeeDTO)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError,(req,res) ->{
                        System.out.println("Error occurred "+new String(res.getBody().readAllBytes()));
                        throw new ResourceNotFoundException("could not create Empoyee");
                    })
                    .onStatus(HttpStatusCode::is5xxServerError,((request, response) -> {
                        throw new RuntimeException("server error occured");
                    }))
                    .body(new ParameterizedTypeReference<>() {
                    });
            return employees.getData();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
