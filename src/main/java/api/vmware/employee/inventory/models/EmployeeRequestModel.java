package api.vmware.employee.inventory.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * The type Employee request model.
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class EmployeeRequestModel {
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Age cannot be null")
    @Min(value = 1, message = "Age should be positive")
    @Max(value = 200, message = "Enter a valid age")
    private Integer age;

}
