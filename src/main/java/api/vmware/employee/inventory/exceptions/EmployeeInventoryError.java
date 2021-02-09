package api.vmware.employee.inventory.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * The type Employee inventory error.
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
public class EmployeeInventoryError {
    private String message;
    private HttpStatus status;
}
