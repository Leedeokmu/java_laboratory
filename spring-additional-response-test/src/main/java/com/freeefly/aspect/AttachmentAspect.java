package com.freeefly.aspect;

import com.freeefly.common.holder.AttachmentTypeHolder;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Aspect
public class AttachmentAspect {
    private final AttachmentTypeHolder attachmentTypeHolder;

    @Pointcut("@annotation(com.freeefly.aspect.Attach)")
    private void pointcut() { }

    @AfterReturning(pointcut = "pointcut()", returning = "returnValue")
    public Object afterReturning(Object returnValue) {
        if (attachmentTypeHolder.getTypes().isEmpty() && !(returnValue instanceof Attachable)) {
            return returnValue;
        }

        executeAttach((Attachable) returnValue);
        return returnValue;
    }

    private void executeAttach(Attachable attachable) {

    }

}
