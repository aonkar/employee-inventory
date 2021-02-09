package api.vmware.employee.inventory.dtos;

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
 * The type Task dto.
 */
@Getter
@NoArgsConstructor
@ToString
@Entity
@Table(name = "task")
public class TaskDTO implements Serializable {

    @Id
    @GeneratedValue
    private Long taskId;
    @Column(nullable = false, length = 100)
    private String status;

    /**
     * Instantiates a new Task dto.
     *
     * @param status the status
     */
    public TaskDTO(final String status) {
        super();
        this.status = status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(final String status) {
        this.status = status;
    }
}
