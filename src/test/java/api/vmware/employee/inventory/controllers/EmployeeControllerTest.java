package api.vmware.employee.inventory.controllers;

import api.vmware.employee.inventory.dtos.EmployeeDTO;
import api.vmware.employee.inventory.models.EmployeeRequestModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * The type Employee controller test.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class EmployeeControllerTest {

    /**
     * The Random server port.
     */
    @LocalServerPort
    int randomServerPort;

    @Autowired
    private WebApplicationContext webApplicationContext;

    /**
     * The constant EMPLOYEE_REQUEST_MODEL.
     */
    public static final EmployeeRequestModel EMPLOYEE_REQUEST_MODEL = new EmployeeRequestModel("Abhishek", 29);

    /**
     * Upload employee inventory test.
     */
    @Test
    void uploadEmployeeInventoryTest() throws Exception {
        uploadEmployeeInventory();
        final ResponseEntity<EmployeeDTO[]> employees = getEmployees();
        Assertions.assertEquals(HttpStatus.OK, employees.getStatusCode());
        Assertions.assertEquals(3, employees.getBody().length);
        Arrays.asList(employees.getBody()).forEach(employeeDTO -> {
            try {
                deleteEmployee(employeeDTO.getId().intValue());
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });

    }

    /**
     * Create employee test.
     *
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    void createEmployeeTest() throws URISyntaxException {
        final ResponseEntity<EmployeeDTO> result = createEmployee(EMPLOYEE_REQUEST_MODEL);
        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertEquals(EMPLOYEE_REQUEST_MODEL.getAge(), result.getBody().getAge());
        Assertions.assertEquals(EMPLOYEE_REQUEST_MODEL.getName(), result.getBody().getName());
        deleteEmployee(result.getBody().getId().intValue());
    }

    /**
     * Update employee test.
     *
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    void updateEmployeeTest() throws URISyntaxException {
        final ResponseEntity<EmployeeDTO> result = createEmployee(EMPLOYEE_REQUEST_MODEL);
        final String newName = "Abhishek Onkar";
        EMPLOYEE_REQUEST_MODEL.setName(newName);
        updateEmployeeById(result.getBody().getId().intValue(), EMPLOYEE_REQUEST_MODEL);
        final ResponseEntity<EmployeeDTO> updatedEmployee = getEmployeeById(result.getBody().getId().intValue());
        Assertions.assertEquals(HttpStatus.OK, updatedEmployee.getStatusCode());
        Assertions.assertEquals(EMPLOYEE_REQUEST_MODEL.getAge(), updatedEmployee.getBody().getAge());
        Assertions.assertEquals(newName, updatedEmployee.getBody().getName());
    }

    /**
     * Delete employee test.
     *
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    void deleteEmployeeTest() throws URISyntaxException {

        final ResponseEntity<EmployeeDTO> result = createEmployee(EMPLOYEE_REQUEST_MODEL);
        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertEquals(EMPLOYEE_REQUEST_MODEL.getAge(), result.getBody().getAge());
        Assertions.assertEquals(EMPLOYEE_REQUEST_MODEL.getName(), result.getBody().getName());
        deleteEmployee(result.getBody().getId().intValue());
    }

    /**
     * Find employee by id test.
     *
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    void findEmployeeByIdTest() throws URISyntaxException {
        final ResponseEntity<EmployeeDTO> result = createEmployee(EMPLOYEE_REQUEST_MODEL);
        Assertions.assertEquals(HttpStatus.CREATED, result.getStatusCode());
        Assertions.assertEquals(EMPLOYEE_REQUEST_MODEL.getAge(), result.getBody().getAge());
        Assertions.assertEquals(EMPLOYEE_REQUEST_MODEL.getName(), result.getBody().getName());
        final ResponseEntity<EmployeeDTO> employeeResponse = getEmployeeById(result.getBody().getId().intValue());
        Assertions.assertEquals(HttpStatus.OK, employeeResponse.getStatusCode());
        Assertions.assertEquals(EMPLOYEE_REQUEST_MODEL.getAge(), employeeResponse.getBody().getAge());
        Assertions.assertEquals(EMPLOYEE_REQUEST_MODEL.getName(), employeeResponse.getBody().getName());
        deleteEmployee(result.getBody().getId().intValue());
    }

    /**
     * Find all test.
     *
     * @throws URISyntaxException the uri syntax exception
     */
    @Test
    void findAllTest() throws URISyntaxException {
        Assertions.assertEquals(0, getEmployees().getBody().length);
        List<Integer> employeeIds = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            final ResponseEntity<EmployeeDTO> result = createEmployee(EMPLOYEE_REQUEST_MODEL);
            employeeIds.add(result.getBody().getId().intValue());
        }
        Assertions.assertEquals(10, getEmployees().getBody().length);
        employeeIds.forEach(employeeId -> {
            try {
                deleteEmployee(employeeId);
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        });
    }

    private ResponseEntity<EmployeeDTO> createEmployee(final EmployeeRequestModel employeeRequestModel)
            throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/employees/";
        URI uri = new URI(baseUrl);
        HttpEntity<EmployeeRequestModel> request = new HttpEntity<>(employeeRequestModel);
        return restTemplate.postForEntity(uri, request, EmployeeDTO.class);
    }

    private void deleteEmployee(final Integer employeeId)
            throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/employees/" + employeeId;
        URI uri = new URI(baseUrl);
        restTemplate.delete(uri);
    }

    private ResponseEntity<EmployeeDTO> getEmployeeById(final Integer employeeId)
            throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/employees/" + employeeId;
        URI uri = new URI(baseUrl);
        return restTemplate.getForEntity(uri, EmployeeDTO.class);
    }

    private ResponseEntity<EmployeeDTO[]> getEmployees()
            throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/employees/";
        URI uri = new URI(baseUrl);
        return restTemplate.getForEntity(uri, EmployeeDTO[].class);
    }

    private void updateEmployeeById(final Integer employeeId,
                                    final EmployeeRequestModel employeeRequestModel)
            throws URISyntaxException {
        final RestTemplate restTemplate = new RestTemplate();
        final String baseUrl = "http://localhost:" + randomServerPort + "/api/employees/" + employeeId;
        URI uri = new URI(baseUrl);
        HttpEntity<EmployeeRequestModel> request = new HttpEntity<>(employeeRequestModel);
        restTemplate.put(uri, request);
    }

    private void uploadEmployeeInventory() throws Exception {
        final String input = "Abhishek Onkar 29\n" +
                "Sachin Tendulkar 40\n" +
                "Saurav 42";
        final MockMultipartFile multipartFile = new MockMultipartFile("file", "testfile.txt", MediaType.TEXT_PLAIN_VALUE,
                input.getBytes());
        final MockMvc mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        mockMvc.perform(multipart("http://localhost:" + randomServerPort + "/api/employees/upload/").file(multipartFile))
                .andExpect(status().isOk());
    }

}