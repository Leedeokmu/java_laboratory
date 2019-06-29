package com.freeefly.dto;

import com.freeefly.enumerate.AttachmentType;
import lombok.experimental.Delegate;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class AttachmentWrapper {
    interface AttachmentMap {
        void put(AttachmentType type, Attachment attachment);
        void putAll(Map<? extends AttachmentType, ? extends Attachment> attachmentMap);
        boolean isEmpty();
        Set<Map.Entry<AttachmentType, Attachment>> entrySet();
    }

    @Delegate(types = AttachmentMap.class)
    private Map<AttachmentType, Attachment> value = new EnumMap<>(AttachmentType.class);

    public void putAll(Collection<AttachmentWrapperItem> items) {
        this.value.putAll(items.stream().collect(Collectors.toMap(AttachmentWrapperItem::getType, AttachmentWrapperItem::getAttachment)));
    }
}
