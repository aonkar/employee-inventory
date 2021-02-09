package api.vmware.employee.inventory.controllers;

import api.vmware.employee.inventory.exceptions.EmployeeInventoryException;
import api.vmware.employee.inventory.services.ITaskService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private final ITaskService taskService;

    public TaskController(final ITaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping("/{taskId}")
    @ApiOperation("Gets task for the given taskId")
    public ResponseEntity<String> getTaskById(@PathVariable("taskId") final Integer taskId) throws EmployeeInventoryException {
        return new ResponseEntity<>(taskService.getStatus(taskId), HttpStatus.OK);
    }
}
