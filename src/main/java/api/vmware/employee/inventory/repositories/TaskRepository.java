package api.vmware.employee.inventory.repositories;

import api.vmware.employee.inventory.dtos.TaskDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Task repository.
 */
@Repository
public interface TaskRepository extends CrudRepository<TaskDTO, Long> {

}
