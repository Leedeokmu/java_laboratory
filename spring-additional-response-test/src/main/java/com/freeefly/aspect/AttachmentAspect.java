package com.freeefly.aspect;

import com.freeefly.attachment.AttachmentService;
import com.freeefly.attachment.AttachmentTypeHolder;
import com.freeefly.dto.Attachment;
import com.freeefly.dto.AttachmentWrapperItem;
import com.freeefly.enumerate.AttachmentType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@Aspect
public class AttachmentAspect {
    private final AttachmentTypeHolder attachmentTypeHolder;
    private final Map<AttachmentType, List<AttachmentService<? extends Attachable>>> typeToServiceMap;


    @Autowired
    public AttachmentAspect(@NonNull AttachmentTypeHolder attachmentTypeHolder,
                            @NonNull List<AttachmentService<? extends Attachable>> attachmentServices){
        this.attachmentTypeHolder = attachmentTypeHolder;
        this.typeToServiceMap = attachmentServices
                .stream()
                .collect(Collectors.groupingBy(AttachmentService::getSupportAttachmentType, Collectors.toList()));
    }

    @Pointcut("@annotation(com.freeefly.aspect.Attach)")
    private void pointcut(){ }

    @AfterReturning(pointcut = "pointcut()", returning = "returnValue")
    public Object afterReturning(Object returnValue) {
        if (attachmentTypeHolder.getTypes().isEmpty() && !(returnValue instanceof Attachable)) {
            return returnValue;
        }

        executeAttach((Attachable) returnValue);
        return returnValue;
    }

    private void executeAttach(Attachable attachable) {
        Set<AttachmentType> types = attachmentTypeHolder.getTypes();
        Class attachmentClass = attachable.getClass();

        Mono<List<AttachmentWrapperItem>> items = Flux.fromIterable(types)
                .log()
                .subscribeOn(Schedulers.elastic())
                .flatMap(type -> Flux.fromIterable(typeToServiceMap.get(type)))
                .filter(service -> service.getSupportType().isAssignableFrom(attachmentClass))
                .flatMap(service -> Mono.from(service.getAttachment(attachable)))
                .filter(item -> item != AttachmentWrapperItem.ON_ERROR)
                .doOnError(e -> log.warn(e.getMessage(), e))
                .collectList()
                .onErrorReturn(Collections.emptyList())
                ;

        items.subscribe((item) -> attachable.attach(item));
    }
}
