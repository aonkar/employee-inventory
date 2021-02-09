package api.vmware.employee.inventory.controllers;

import api.vmware.employee.inventory.dtos.EmployeeDTO;
import api.vmware.employee.inventory.exceptions.EmployeeInventoryException;
import api.vmware.employee.inventory.models.EmployeeInventoryResponse;
import api.vmware.employee.inventory.models.EmployeeRequestModel;
import api.vmware.employee.inventory.services.IEmployeeService;
import api.vmware.employee.inventory.services.ITaskService;
import io.swagger.annotations.ApiOperation;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    @Autowired
    private final ITaskService taskService;

    @Autowired
    private final IEmployeeService employeeService;

    public EmployeeController(final ITaskService taskService,
                              final IEmployeeService employeeService) {
        this.taskService = taskService;
        this.employeeService = employeeService;
    }

    @PostMapping("/upload")
    @ApiOperation("Bulk insertion of employee records. Upload a file with multiple employees line separated")
    public ResponseEntity<EmployeeInventoryResponse> uploadEmployeeInventory(@RequestParam("file") final MultipartFile file)
            throws EmployeeInventoryException {

        if (file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        final Integer taskID = taskService.createTask();
        employeeService.persistEmployeeDetails(file, taskID);
        return new ResponseEntity<>(new EmployeeInventoryResponse(taskID), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("Creates an Employee record and returns unique employeeId")
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody @Valid final EmployeeRequestModel employee)
            throws EmployeeInventoryException {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        final EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        return new ResponseEntity<>(employeeService.createEmployee(employeeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{employeeId}")
    @ApiOperation("Updates an Employee record for the given employeeId")
    public ResponseEntity<String> updateEmployee(@RequestBody @Valid final EmployeeRequestModel employee,
                                              @PathVariable final Integer employeeId) throws EmployeeInventoryException {
        final ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        final EmployeeDTO employeeDTO = modelMapper.map(employee, EmployeeDTO.class);
        employeeService.updateEmployee(employeeDTO, employeeId);
        return new ResponseEntity<>("Updated Successfully", HttpStatus.OK);
    }

    @DeleteMapping("/{employeeId}")
    @ApiOperation("Deletes an Employee record for the given employeeId")
    public void deleteEmployee(@PathVariable final Integer employeeId) throws EmployeeInventoryException {
        employeeService.deleteEmployee(employeeId);
    }

    @GetMapping("/{employeeId}")
    @ApiOperation("Finds an Employee record for the given employeeId")
    public ResponseEntity<EmployeeDTO> findEmployeeById(@PathVariable final Integer employeeId) {
        final EmployeeDTO employee = employeeService.findEmployeeById(employeeId);
        if (employee == null) {
            return new ResponseEntity<>(new EmployeeDTO(), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @GetMapping()
    @ApiOperation("Gets all the Employee records from the Database")
    public ResponseEntity<List<EmployeeDTO>> findAll() {
        return new ResponseEntity<>(employeeService.findAll(), HttpStatus.OK);
    }
}
