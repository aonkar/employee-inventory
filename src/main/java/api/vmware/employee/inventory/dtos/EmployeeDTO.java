package api.vmware.employee.inventory.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * The type Employee dto.
 */
@Getter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "employee")
public class EmployeeDTO implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 3)
    private Integer age;

    /**
     * Instantiates a new Employee dto.
     *
     * @param name the name
     * @param age  the age
     */
    public EmployeeDTO(final String name,
                       final Integer age) {
        this.name = name;
        this.age = age;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * Sets age.
     *
     * @param age the age
     */
    public void setAge(final Integer age) {
        this.age = age;
    }
}
