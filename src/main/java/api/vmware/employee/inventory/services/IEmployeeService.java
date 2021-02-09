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
     * Create employee dto.
     *
     * @param employeeDTO the employee dto
     * @return the employee dto
     */
    EmployeeDTO create(EmployeeDTO employeeDTO) throws EmployeeInventoryException;

    /**
     * Update employee dto.
     *
     * @param employeeDTO the employee dto
     * @param employeeId  the employee id
     * @return the employee dto
     */
    EmployeeDTO update(EmployeeDTO employeeDTO,
                       final Integer employeeId) throws EmployeeInventoryException;

    /**
     * Delete.
     *
     * @param id the id
     */
    void delete(Integer id) throws EmployeeInventoryException;

    /**
     * Find employee dto.
     *
     * @param id the id
     * @return the employee dto
     */
    EmployeeDTO find(Integer id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<EmployeeDTO> findAll();
}
