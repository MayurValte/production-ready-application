package com.production_ready_features;

import com.production_ready_features.DTO.EmployeeDTO;
import com.production_ready_features.clients.EmployeeClient;
import com.production_ready_features.entities.User;
import com.production_ready_features.services.JwtService;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class ProductionReadyFeaturesApplicationTests {

    @Autowired
    private EmployeeClient employeeClient;

    @Autowired
    private JwtService jwtService;

    Logger logger= LoggerFactory.getLogger(ProductionReadyFeaturesApplicationTests.class);

    @Test
	@Order(3)
    public void getAllEmployees() {
        List<EmployeeDTO> allEmployee = employeeClient.getAllEmployee();
        logger.info(allEmployee.toString());
    }

    @Test
	@Order(2)
    public void getEmpById() {
        EmployeeDTO empById = employeeClient.getEmpById(2L);
        System.out.println(empById);
    }

    @Test
	@Order(1)
    public void createEmp() {
        EmployeeDTO employeeDTO = new EmployeeDTO(null, "MayurValte", "MayurValte1999@gmail.com", 18,
				"ADMIN", 34332.21, LocalDate.of(2025, 8, 9), true);
		EmployeeDTO savedEmployee = employeeClient.createNewEmp(employeeDTO);
		System.out.println(savedEmployee);

	}

    @Test
    public void testJwt(){
        User user=new User(10L,"mayur@gmail.com","1234","Mayur");
        String token = jwtService.generateAccessToken(user);
        System.out.println(token);
//        Long id = jwtService.getUserIdFromToke(token);
//        System.out.println(id);

    }

}
