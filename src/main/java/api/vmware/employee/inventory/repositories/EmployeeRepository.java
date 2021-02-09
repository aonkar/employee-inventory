package api.vmware.employee.inventory.repositories;

import api.vmware.employee.inventory.dtos.EmployeeDTO;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * The interface Employee repository.
 */
@Repository
public interface EmployeeRepository extends CrudRepository<EmployeeDTO, Long> {
}
