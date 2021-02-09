package api.vmware.employee.inventory.enums;

import lombok.Getter;
import lombok.ToString;

/**
 * The enum Task status.
 */
@Getter
@ToString
public enum TaskStatus {

    /**
     * The Created.
     */
    CREATED("New task created"),
    /**
     * The Processing.
     */
    PROCESSING("Task under process"),
    /**
     * The Completed.
     */
    COMPLETED("Task completed"),
    /**
     * The Failed.
     */
    FAILED("Task failed");

    private final String message;

    TaskStatus(final String message) {
        this.message = message;
    }
}
