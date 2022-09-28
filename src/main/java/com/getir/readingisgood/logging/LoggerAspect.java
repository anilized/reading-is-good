package com.getir.readingisgood.logging;

import com.getir.readingisgood.auth.entity.User;
import com.getir.readingisgood.auth.service.impl.UserDetailsImpl;
import com.getir.readingisgood.auth.service.impl.UserDetailsServiceImpl;
import com.getir.readingisgood.exception.BusinessFault;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Aspect
@Slf4j
@Component
@RequiredArgsConstructor
public class LoggerAspect {
    private final LogService logService;

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void controllerPointcut() {

    }

    @AfterThrowing(pointcut = "controllerPointcut()", throwing = "ex")
    public void logError(JoinPoint joinPoint, Exception ex) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        LogEntity.LogEntityBuilder builder = LogEntity.builder()
                .endpoint(request.getRequestURL().toString())
                .method(request.getMethod())
                .message(ex.getMessage())
                .errorType(ex.getClass().getName())
                .operation(joinPoint.getSignature().getName());
        if (ex instanceof BusinessFault) {
            builder.status(((BusinessFault) ex).getCode());
        } else {
            builder.status("500");
        }
        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            builder.userId(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        }
        logService.logToDatabase(builder .build());
    }

    @AfterReturning(value = "controllerPointcut()", returning = "result")
    public void logAfterReturning(JoinPoint joinPoint, Object result) throws IOException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        LogEntity.LogEntityBuilder builder = LogEntity.builder()
                .endpoint(request.getRequestURL().toString())
                .method(request.getMethod())
                .response(new Gson().toJson(result));

        if (!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
            builder.userId(((UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId());
        }
        logService.logToDatabase(builder.build());
    }
}
