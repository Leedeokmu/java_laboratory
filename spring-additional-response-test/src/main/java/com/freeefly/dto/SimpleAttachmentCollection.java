package com.freeefly.dto;

import lombok.Value;
import lombok.experimental.Delegate;

import java.util.Collection;

@Value
public class SimpleAttachmentCollection<T extends Attachment> implements AttachmentCollection<T> {
    @Delegate
    private Collection<T> value;
}
