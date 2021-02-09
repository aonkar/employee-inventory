package api.vmware.employee.inventory.services;

import api.vmware.employee.inventory.dtos.EmployeeDTO;
import api.vmware.employee.inventory.exceptions.EmployeeInventoryException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * The interface Employee service.
 */
public interface IEmployeeService {
    /**
     * Persist employee details.
     *
     * @param file   the file
     * @param taskID the task id
     * @throws EmployeeInventoryException the employee inventory exception
     */
    void persistEmployeeDetails(MultipartFile file, Integer taskID) throws EmployeeInventoryException;

    /**
     * Create employee employee dto.
     *
     * @param employeeDTO the employee dto
     * @return the employee dto
     * @throws EmployeeInventoryException the employee inventory exception
     */
    EmployeeDTO createEmployee(EmployeeDTO employeeDTO) throws EmployeeInventoryException;

    /**
     * Update employee employee dto.
     *
     * @param employeeDTO the employee dto
     * @param employeeId  the employee id
     * @return the employee dto
     * @throws EmployeeInventoryException the employee inventory exception
     */
    EmployeeDTO updateEmployee(EmployeeDTO employeeDTO,
                               final Integer employeeId) throws EmployeeInventoryException;

    /**
     * Delete employee.
     *
     * @param id the id
     * @throws EmployeeInventoryException the employee inventory exception
     */
    void deleteEmployee(Integer id) throws EmployeeInventoryException;

    /**
     * Find employee by id employee dto.
     *
     * @param id the id
     * @return the employee dto
     */
    EmployeeDTO findEmployeeById(Integer id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<EmployeeDTO> findAll();
}
