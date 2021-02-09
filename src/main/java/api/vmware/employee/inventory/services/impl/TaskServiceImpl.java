package api.vmware.employee.inventory.services.impl;

import api.vmware.employee.inventory.dtos.TaskDTO;
import api.vmware.employee.inventory.enums.TaskStatus;
import api.vmware.employee.inventory.exceptions.EmployeeInventoryException;
import api.vmware.employee.inventory.repositories.TaskRepository;
import api.vmware.employee.inventory.services.ITaskService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static api.vmware.employee.inventory.contants.ErrorMessage.CREATION_ERROR;
import static api.vmware.employee.inventory.contants.ErrorMessage.RESOURCE_NOT_FOUND;

/**
 * The type Task service.
 */
@Service
@Slf4j
public class TaskServiceImpl implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public Integer createTask() throws EmployeeInventoryException {
        try {
            final TaskDTO taskDTO = taskRepository.save(new TaskDTO(TaskStatus.CREATED.getMessage()));
            return taskDTO.getTaskId().intValue();
        } catch (final Exception exception) {
            log.error(CREATION_ERROR, exception);
            throw new EmployeeInventoryException(HttpStatus.INTERNAL_SERVER_ERROR, CREATION_ERROR);
        }
    }

    @Override
    public void updateTask(final Integer id,
                           final TaskStatus status) throws EmployeeInventoryException {
        final Optional<TaskDTO> task = taskRepository.findById(Long.valueOf(id));
        if (task.isPresent()) {
            final TaskDTO taskDTO = task.get();
            taskDTO.setStatus(status.getMessage());
            taskRepository.save(taskDTO);
        } else {
            log.error(RESOURCE_NOT_FOUND);
            throw new EmployeeInventoryException(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND);
        }
    }

    @Override
    public String getStatus(final Integer id) throws EmployeeInventoryException {
        final Optional<TaskDTO> task = taskRepository.findById(Long.valueOf(id));
        if (task.isPresent()) {
            return task.get().getStatus();
        } else {
            log.error(RESOURCE_NOT_FOUND);
            throw new EmployeeInventoryException(HttpStatus.NOT_FOUND, RESOURCE_NOT_FOUND);
        }
    }
}