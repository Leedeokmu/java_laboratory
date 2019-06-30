package com.freeefly.attachment.writer.service;

import com.freeefly.aspect.Attachable;
import com.freeefly.attachment.AttachmentService;
import com.freeefly.attachment.comment.service.CommentClient;
import com.freeefly.dto.*;
import com.freeefly.enumerate.AttachmentType;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.util.Random;

@Slf4j
@Service
public class AttachWriterToBoardService implements AttachmentService {
    private final WriterClient writerClient;
    private final Duration timeout;
    @Autowired
    public AttachWriterToBoardService(@NonNull WriterClient writerClient,
                                      @Value("${attach.writer.timeoutMillis:5000}") long timeout) {
        this.writerClient = writerClient;
        this.timeout = Duration.ofMillis(timeout);
    }

    private static final AttachmentType supportAttachmentType = AttachmentType.WRITER;
    private static final Class<BoardDto> supportType = BoardDto.class;

    @Override
    public AttachmentType getSupportAttachmentType() {
        return this.supportAttachmentType;
    }

    @Override
    public Class getSupportType() {
        return this.supportType;
    }

    @Override
    public Mono<AttachmentWrapperItem> getAttachment(Attachable attachable) {
        return Mono.defer(() -> executeGetAttachment(attachable)
                .subscribeOn(Schedulers.elastic())
                .timeout(timeout)
                .doOnError(e -> log.warn(e.getMessage(), e))
                .onErrorReturn(AttachmentWrapperItem.ON_ERROR));
    }

    private Mono<AttachmentWrapperItem> executeGetAttachment(Attachable attachable) {
        BoardDto boardDto = this.supportType.cast(attachable);
        Attachment attachment = writerClient.getWriter(boardDto.getWriterId());
        return Mono.just(new AttachmentWrapperItem(supportAttachmentType, attachment));
    }


}
