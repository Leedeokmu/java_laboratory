package com.freeefly.aspect;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.freeefly.dto.Attachment;
import com.freeefly.dto.AttachmentWrapper;
import com.freeefly.enumerate.AttachmentType;

import java.util.Map;
import java.util.stream.Collectors;

public interface Attachable {
    AttachmentWrapper getAttachmentWrapper();

    default void attach(AttachmentType type, Attachment attachment){
        getAttachmentWrapper().put(type, attachment);
    }
    default void attach(Map<? extends AttachmentType, ? extends Attachment> attachment) {
        getAttachmentWrapper().putAll(attachment);
    }

    @JsonAnyGetter
    default Map<String, Object> getAttachment() {
        AttachmentWrapper wrapper = getAttachmentWrapper();

        if (wrapper.isEmpty()) {
            return null;
        }

        return wrapper.entrySet()
                .stream()
                .collect(Collectors.toMap(e -> e.getKey().lowerCaseName(), Map.Entry::getValue));
    }



}
