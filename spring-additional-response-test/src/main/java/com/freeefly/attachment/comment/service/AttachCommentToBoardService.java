package com.freeefly.attachment.comment.service;

import com.freeefly.attachment.AttachmentService;
import com.freeefly.dto.Attachment;
import com.freeefly.dto.BoardDto;
import com.freeefly.dto.SimpleAttachmentCollection;
import com.freeefly.enumerate.AttachmentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AttachCommentToBoardService implements AttachmentService {
    private final CommentClient commentClient;

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
    public Attachment getAttachment(Object attachment) {
        BoardDto boardDto = this.supportType.cast(attachment);
        return new SimpleAttachmentCollection(commentClient.getComments(boardDto.getId()));
    }
}

