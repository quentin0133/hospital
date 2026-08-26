package fr.cfa.hospital.core.logs;

import fr.cfa.hospital.core.tools.LogUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * The type Controller logging.
 */
@Aspect
@Component
public class ControllerLogging {
    /**
     * Log controller object.
     *
     * @param joinPoint the join point
     * @return the object
     * @throws Throwable the throwable
     */
    @Around("@annotation(LogController)")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        Class<?> targetClass = joinPoint.getTarget().getClass();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String methodName = signature.getMethod().getName();
        Logger targetLogger = LoggerFactory.getLogger(targetClass);

        LogUtils.logEnter(targetLogger, methodName);

        Object result = joinPoint.proceed();

        LogUtils.logExit(targetLogger, methodName);

        return result;
    }
}
