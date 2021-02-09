package api.vmware.employee.inventory.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * The type Monitor logging.
 */
@Aspect
@Configuration
@Slf4j
public class EmployeeInventoryLogging {

    private static final String ENTER = "Entering: ";
    private static final String EXIT = "Exiting: ";

    /**
     * Before advice.
     *
     * @param joinPoint the join point
     */
    @Before("employeeControllerMethods() " +
            "|| taskControllerMethods() " +
            "|| employeeServiceMethods() " +
            "|| taskServiceMethods() ")
    public void beforeAdvice(final JoinPoint joinPoint) {
        logClassWithMethodInfo(joinPoint, ENTER);
        final Object[] arguments = joinPoint.getArgs();
        logArguments(joinPoint, arguments);
    }

    /**
     * Returning advice.
     *
     * @param joinPoint the join point
     */
    @AfterReturning("employeeControllerMethods() " +
            "|| taskControllerMethods() " +
            "|| employeeServiceMethods() " +
            "|| taskServiceMethods() ")
    public void returningAdvice(final JoinPoint joinPoint) {
        logClassWithMethodInfo(joinPoint, EXIT);
        final Object[] arguments = joinPoint.getArgs();
        logArguments(joinPoint, arguments);
    }


    @Pointcut("execution(* api.vmware.employee.inventory.controllers.EmployeeController.*(..))")
    private void employeeControllerMethods() {
        //This method is just a placeholder for targeting the regular expression of all methods within EmployeeController. Used in
        // the advices.
    }

    @Pointcut("execution(* api.vmware.employee.inventory.controllers.TaskController.*(..))")
    private void taskControllerMethods() {
        //This method is just a placeholder for targeting the regular expression of all methods within TaskController. Used in
        // the advices.
    }

    @Pointcut("execution(* api.vmware.employee.inventory.services.IEmployeeService.*(..))")
    private void employeeServiceMethods() {
        //This method is just a placeholder for targeting the regular expression of all methods within IEmployeeService. Used in
        // the advices.
    }

    @Pointcut("execution(* api.vmware.employee.inventory.services.ITaskService.*(..))")
    private void taskServiceMethods() {
        //This method is just a placeholder for targeting the regular expression of all methods within ITaskService. Used in
        // the advices.
    }

    private void logClassWithMethodInfo(final JoinPoint joinPoint,
                                        final String message) {
        final String logMessage = String.format("%s %s :: %s", message, joinPoint.getTarget().getClass().getName(),
                joinPoint.getSignature().getName());
        log.info(logMessage);
    }

    private void logArguments(final JoinPoint joinPoint,
                              final Object[] arguments) {
        if (arguments != null) {
            final String argumentsString = Arrays.asList(arguments)
                    .stream()
                    .map(argument -> String.valueOf(argument))
                    .collect(Collectors.joining(","));
            log.info("Arguments of method: {} :: {} is : {}",
                    joinPoint.getTarget().getClass().getName(),
                    joinPoint.getSignature().getName(),
                    argumentsString);

        }
    }
}
