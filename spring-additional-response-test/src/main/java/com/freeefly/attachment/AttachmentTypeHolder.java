package com.freeefly.attachment;

import com.freeefly.enumerate.AttachmentType;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Collections;
import java.util.Set;

@Value
public class AttachmentTypeHolder {
    private Set<AttachmentType> types;

    public AttachmentTypeHolder(@NonNull Set<AttachmentType> types) { this.types = Collections.unmodifiableSet(types); }

    public boolean isEmpty() {
        return types.isEmpty();
    }

    public Set<AttachmentType> getTypes() {
        return types;
    }
}
