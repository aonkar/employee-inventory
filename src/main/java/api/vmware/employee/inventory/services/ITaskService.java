package api.vmware.employee.inventory.services;

import api.vmware.employee.inventory.enums.TaskStatus;
import api.vmware.employee.inventory.exceptions.EmployeeInventoryException;

/**
 * The interface Task service.
 */
public interface ITaskService {
    /**
     * Create task integer.
     *
     * @return the integer
     * @throws EmployeeInventoryException the employee inventory exception
     */
    Integer createTask() throws EmployeeInventoryException;

    /**
     * Update task.
     *
     * @param id     the id
     * @param status the status
     * @throws EmployeeInventoryException the employee inventory exception
     */
    void updateTask(Integer id, TaskStatus status) throws EmployeeInventoryException;

    /**
     * Gets status.
     *
     * @param id the id
     * @return the status
     * @throws EmployeeInventoryException the employee inventory exception
     */
    String getStatus(Integer id) throws EmployeeInventoryException;
}
