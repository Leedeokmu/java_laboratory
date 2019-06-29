package com.freeefly.attachment;

import com.freeefly.enumerate.AttachmentType;
import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.RequestScope;

import java.util.Set;

@Data
@RequestScope
@Component
public class AttachmentTypeHolder {
    private Set<AttachmentType> types;
}
