package com.freeefly.attachment.comment.service;

import com.freeefly.aspect.Attachable;
import com.freeefly.attachment.AttachmentService;
import com.freeefly.dto.Attachment;
import com.freeefly.dto.AttachmentWrapperItem;
import com.freeefly.dto.BoardDto;
import com.freeefly.dto.SimpleAttachmentCollection;
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

@Slf4j
@Service
public class AttachCommentToBoardService implements AttachmentService {
    private final CommentClient commentClient;
    private final Duration timeout;
    @Autowired
    public AttachCommentToBoardService(@NonNull CommentClient commentClient
            ,@Value("${attach.writer.timeoutMillis:5000}") long timeout) {
        this.commentClient = commentClient;
        this.timeout = Duration.ofMillis(timeout);
    }

    private static final AttachmentType supportAttachmentType = AttachmentType.COMMENTS;
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
    public Mono<AttachmentWrapperItem> getAttachment(Attachable attachment) {
        return Mono.defer(() -> executeGetAttachment(attachment))
                .subscribeOn(Schedulers.elastic())
                .timeout(timeout)
                .doOnError((e) -> log.warn(e.getMessage(), e))
                .onErrorReturn(AttachmentWrapperItem.ON_ERROR);
    }

    private Mono<AttachmentWrapperItem> executeGetAttachment(Attachable attachment) {
        BoardDto boardDto = this.supportType.cast(attachment);
        SimpleAttachmentCollection simpleAttachmentCollection = new SimpleAttachmentCollection(commentClient.getComments(boardDto.getId()));
        return Mono.just(new AttachmentWrapperItem(supportAttachmentType, simpleAttachmentCollection));
    }
}

