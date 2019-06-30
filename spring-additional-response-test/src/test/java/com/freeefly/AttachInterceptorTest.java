//package com.freeefly;
//
//
//import com.freeefly.aspect.Attach;
//import com.freeefly.attachment.AttachmentTypeHolder;
//import com.freeefly.enumerate.AttachmentType;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.Spy;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.mock.web.MockHttpServletResponse;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.web.method.HandlerMethod;
//
//import static org.hamcrest.core.IsCollectionContaining.hasItem;
//import static org.junit.Assert.assertThat;
//import static org.mockito.BDDMockito.given;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@RunWith(SpringRunner.class)
//public class AttachInterceptorTest {
//
//    @InjectMocks
//    private AttachmentInterceptor attachInterceptor;
//    @Spy
//    private AttachmentTypeHolder attachmentTypeHolder;
//    @Mock
//    private HandlerMethod handlerMethod;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }
//
//    @Test
//    public void preHandle() throws Exception {
//        // given
//        given(handlerMethod.hasMethodAnnotation(Attach.class)).willReturn(true);
//        MockHttpServletRequest request = new MockHttpServletRequest();
//        request.setParameter(AttachmentInterceptor.TARGET_PARAMETER_NAME, AttachmentType.COMMENTS.name().toLowerCase());
//        MockHttpServletResponse response = new MockHttpServletResponse();
//
//        // when
//        attachInterceptor.preHandle(request, response, handlerMethod);
//
//        // then
//        assertThat(attachmentTypeHolder.getTypes(), hasItem(AttachmentType.COMMENTS));
//    }
//}
