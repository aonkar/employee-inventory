package api.vmware.employee.inventory.aop;


import api.vmware.employee.inventory.exceptions.EmployeeInventoryException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The type Employee inventory entity exception handler.
 */
@ControllerAdvice
public class EmployeeInventoryEntityExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Employee inventory exception handler response entity.
     *
     * @param employeeInventoryException the employee inventory exception
     * @return the response entity
     */
    @ExceptionHandler(EmployeeInventoryException.class)
    public ResponseEntity<String> employeeInventoryExceptionHandler(final EmployeeInventoryException employeeInventoryException) {
        return new ResponseEntity<>(employeeInventoryException.getMessage(), employeeInventoryException.getStatus());
    }

    /**
     * Exception handler response entity.
     *
     * @param exception the exception
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exceptionHandler(final Exception exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
