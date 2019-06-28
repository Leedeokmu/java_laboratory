package com.freeefly.dto;

import com.freeefly.enumerate.AttachmentType;
import lombok.experimental.Delegate;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

public class AttachmentWrapper {
    interface  AttachmentMap {
        void put(AttachmentType type, Attachment attachment);
        void putAll(Map<? extends Attachment, ? extends Attachment> attachmentMap);
        boolean isEmpty();
        Set<Map<AttachmentType, Attachment>> entrySet();
    }

    @Delegate(types = AttachmentMap.class)
    private Map<AttachmentType, Attachment> value = new EnumMap<>(AttachmentType.class);
}
