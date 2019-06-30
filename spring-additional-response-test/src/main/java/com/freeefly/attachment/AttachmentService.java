package com.freeefly.attachment;


import com.freeefly.aspect.Attachable;
import com.freeefly.dto.Attachment;
import com.freeefly.dto.AttachmentWrapperItem;
import com.freeefly.enumerate.AttachmentType;
import reactor.core.publisher.Mono;

public interface AttachmentService<T extends Attachable>{
    // 1. 어떤 AttachmentType에 대해서 동작하는가
    AttachmentType getSupportAttachmentType();

    // 2. 어떤 Attachment 클래스에 대해 동작하는지
    // 쪽지, 뉴스, 동영상 등등 여러 컨텐츠에 대해서 대응
    Class<T> getSupportType();

    // attachment 를 불러와 Attachment 로 변환
    Mono<AttachmentWrapperItem> getAttachment(Attachable attachment);
}
