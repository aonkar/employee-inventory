package api.vmware.employee.inventory.exceptions;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

/**
 * The type Employee inventory exception.
 */
@Getter
@Setter
@ToString
public class EmployeeInventoryException extends Exception {

    private final HttpStatus status;
    private final String error;

    /**
     * Instantiates a new Employee inventory exception.
     *
     * @param code  the code
     * @param error the error
     */
    public EmployeeInventoryException(@NonNull final HttpStatus code,
                                      @NonNull final String error) {
        super(error);
        this.status = code;
        this.error = error;
    }
}
