package api.vmware.employee.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * The type Employee inventory application.
 */
@SpringBootApplication
@EnableSwagger2
public class EmployeeInventoryApplication {

	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(EmployeeInventoryApplication.class, args);
	}

}
