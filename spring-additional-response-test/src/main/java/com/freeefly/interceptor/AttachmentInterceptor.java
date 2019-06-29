package com.freeefly.interceptor;

import com.freeefly.aspect.Attach;
import com.freeefly.attachment.AttachmentTypeHolder;
import com.freeefly.enumerate.AttachmentType;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Component
public class AttachmentInterceptor extends HandlerInterceptorAdapter {
    private final AttachmentTypeHolder attachmentTypeHolder;

    public AttachmentInterceptor(AttachmentTypeHolder attachmentTypeHolder) {
        this.attachmentTypeHolder = attachmentTypeHolder;
    }


    public static final String TARGET_PARAMETER_NAME = "attachment";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if(!(handlerMethod).hasMethodAnnotation(Attach.class)){
            return true;
        }
        Set<AttachmentType> types = resolveAttachmentType(request);
        attachmentTypeHolder.setTypes(types);

        return true;
    }

    private Set<AttachmentType> resolveAttachmentType(HttpServletRequest request){
        String attachments = request.getParameter(TARGET_PARAMETER_NAME);
        if(StringUtils.isEmpty(attachments)){
            return Collections.emptySet();
        }

        return Stream.of(attachments.split(","))
                .map(String::toUpperCase)
                .map(AttachmentType::valueOf)
                .collect(Collectors.toSet());
    }

}
