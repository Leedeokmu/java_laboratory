package com.freeefly.attachment;

import com.freeefly.aspect.Attachable;
import com.freeefly.dto.AttachmentWrapperItem;
import com.freeefly.enumerate.AttachmentType;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
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
public class AttachExecutor {
    private final Map<AttachmentType, List<AttachmentService<? extends Attachable>>> typeToServiceMap;

    @Autowired
    public AttachExecutor(@NonNull List<AttachmentService<? extends Attachable>> attachmentServices){
        this.typeToServiceMap = attachmentServices
                .stream()
                .collect(Collectors.groupingBy(AttachmentService::getSupportAttachmentType, Collectors.toList()));
    }

    public Mono<Attachable> attach(Attachable attachable, AttachmentTypeHolder holder){
        if (holder.isEmpty()) {
            return Mono.just(attachable);
        }

        return executeAttach(attachable, holder.getTypes());
    }

    private Mono<Attachable> executeAttach(Attachable attachable, Set<AttachmentType> types) {
        Class attachmentClass = attachable.getClass();

        Mono<List<AttachmentWrapperItem>> items = Flux.fromIterable(types)
                .log()
                .subscribeOn(Schedulers.elastic())
                .flatMap(type -> Flux.fromIterable(typeToServiceMap.get(type)))
                .filter(service -> service.getSupportType().isAssignableFrom(attachmentClass))
                .flatMap(service -> Mono.from(service.getAttachment(attachable)))
                .map(AttachmentWrapperItem.class::cast)
                .filter(item -> item != AttachmentWrapperItem.ON_ERROR)
                .doOnError(e -> log.warn(e.getMessage(), e))
                .collectList()
                .onErrorReturn(Collections.emptyList())
                ;

        return items.map(attachable::attach)
                .doOnError(e -> log.warn(e.getMessage(), e))
                .onErrorReturn(attachable);
    }

}
