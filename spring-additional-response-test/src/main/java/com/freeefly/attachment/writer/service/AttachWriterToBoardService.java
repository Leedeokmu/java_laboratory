package com.freeefly.attachment.writer.service;

import com.freeefly.attachment.AttachmentService;
import com.freeefly.attachment.comment.service.CommentClient;
import com.freeefly.dto.Attachment;
import com.freeefly.dto.BoardDto;
import com.freeefly.dto.SimpleAttachmentCollection;
import com.freeefly.enumerate.AttachmentType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AttachWriterToBoardService implements AttachmentService {
    private final WriterClient writerClient;

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
    public Attachment getAttachment(Object attachment) {
        BoardDto boardDto = this.supportType.cast(attachment);

    }
}
