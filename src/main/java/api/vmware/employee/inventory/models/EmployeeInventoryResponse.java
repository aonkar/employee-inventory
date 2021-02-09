package api.vmware.employee.inventory.models;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The type Employee inventory response.
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
public class EmployeeInventoryResponse {
    /**
     * Instantiates a new Employee inventory response.
     *
     * @param taskId the task id
     */
    public EmployeeInventoryResponse(final Integer taskId) {
        this.taskId = taskId;
    }
    private Integer taskId;
}
