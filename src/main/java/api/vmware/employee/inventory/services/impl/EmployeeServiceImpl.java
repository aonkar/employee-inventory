package api.vmware.employee.inventory.services.impl;

import api.vmware.employee.inventory.dtos.EmployeeDTO;
import api.vmware.employee.inventory.enums.TaskStatus;
import api.vmware.employee.inventory.exceptions.EmployeeInventoryException;
import api.vmware.employee.inventory.repositories.EmployeeRepository;
import api.vmware.employee.inventory.services.IEmployeeService;
import api.vmware.employee.inventory.services.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.IterableUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static api.vmware.employee.inventory.contants.ErrorMessage.CREATION_ERROR;
import static api.vmware.employee.inventory.contants.ErrorMessage.PROCESSING_ERROR;
import static api.vmware.employee.inventory.contants.ErrorMessage.RESOURCE_NOT_FOUND;
import static api.vmware.employee.inventory.contants.ErrorMessage.TASK_UPDATE_ERROR;

/**
 * The type Employee service.
 */
@Service
@Slf4j
public class EmployeeServiceImpl implements IEmployeeService {

    @Autowired
    private final EmployeeRepository employeeRepository;

    @Autowired
    private final ITaskService taskService;

    /**
     * Instantiates a new Employee service.
     *
     * @param employeeRepository the employee repository
     * @param taskService        the task service
     */
    public EmployeeServiceImpl(final EmployeeRepository employeeRepository,
                               final ITaskService taskService) {
        this.employeeRepository = employeeRepository;
        this.taskService = taskService;
    }

    @Override
    public void persistEmployeeDetails(final MultipartFile file,
                                       final Integer taskID) throws EmployeeInventoryException {

        taskService.updateTask(taskID, TaskStatus.PROCESSING);
        CompletableFuture.supplyAsync(() -> {
            try {
                final BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
                reader.lines().parallel()
                        .map(s -> s.trim())
                        .filter(s -> !s.isEmpty())
                        .forEach(employee -> {
                            try {
                                final String[] employeeDetails = employee.split(" ");
                                final StringBuilder employeeName = new StringBuilder();
                                for (int i = 0; i < employeeDetails.length - 1; i++) {
                                    employeeName.append(employeeDetails[i]);
                                    if(i != employeeDetails.length - 2){
                                        employeeName.append(" ");
                                    }
                                }
                                employeeRepository.save(new EmployeeDTO(employeeName.toString(),
                                        Integer.valueOf(employeeDetails[employeeDetails.length - 1])));
                            } catch (final Exception exception) {
                                log.error(PROCESSING_ERROR, exception);
                            }
                        });
            } catch (final Exception exception) {
                log.error(PROCESSING_ERROR, exception);
            }
            return taskID;
        }).thenAccept(taskId -> {
            try {
                taskService.updateTask(taskID, TaskStatus.COMPLETED);
            } catch (EmployeeInventoryException exception) {
                log.error(TASK_UPDATE_ERROR, exception);
            }
        });
    }

    @Override
    public EmployeeDTO createEmployee(final EmployeeDTO employeeDTO) throws EmployeeInventoryException {
        try {
            return employeeRepository.save(employeeDTO);
        } catch (final Exception exception) {
            log.error(CREATION_ERROR, exception);
            throw new EmployeeInventoryException(HttpStatus.INTERNAL_SERVER_ERROR, CREATION_ERROR);
        }
    }

    @Override
    public EmployeeDTO updateEmployee(final EmployeeDTO employeeDTO,
                                      final Integer employeeId) throws EmployeeInventoryException {
        final Optional<EmployeeDTO> optionalEmployee = employeeRepository.findById(Long.valueOf(employeeId));
        if (optionalEmployee.isPresent()) {
            final EmployeeDTO updatedEmployeeDTO = optionalEmployee.get();
            final ModelMapper modelMapper = new ModelMapper();
            modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
            modelMapper.map(employeeDTO, updatedEmployeeDTO);
            employeeRepository.save(updatedEmployeeDTO);
            return updatedEmployeeDTO;
        } else {
            log.error(RESOURCE_NOT_FOUND);
            throw new EmployeeInventoryException(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public void deleteEmployee(final Integer id) throws EmployeeInventoryException {
        try {
            employeeRepository.deleteById(Long.valueOf(id));
        } catch (final Exception exception){
            log.error(RESOURCE_NOT_FOUND);
            throw new EmployeeInventoryException(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public EmployeeDTO findEmployeeById(final Integer id) {
        final Optional<EmployeeDTO> optionalEmployee = employeeRepository.findById(Long.valueOf(id));
        return optionalEmployee.orElse(null);
    }

    @Override
    public List<EmployeeDTO> findAll() {
        final Iterable<EmployeeDTO> employees = employeeRepository.findAll();
        return IterableUtils.toList(employees);
    }
}
