//package com.freeefly;
//
//import com.freeefly.webflux.model.BoardPost;
//import com.freeefly.webflux.repository.BoardPostRepository;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.reactive.server.WebTestClient;
//
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
//@RunWith(SpringRunner.class)
//public class BoardPostTest {
//    @Autowired
//    private BoardPostRepository repository;
//    @Autowired
//    private WebTestClient webTestClient;
//
//    @Before
//    public void init() {
//        BoardPost post = new BoardPost();
//        post.setTitle("title");
//        post.setContent("content");
//        repository.save(post);
//    }
//
//    @Test
//    public void testFindAll() {
//        List<BoardPost> posts = repository.findAll();
//        assertThat(posts).isNotEmpty();
//    }
//
//    @Test
//    public void test_boardpostRouter(){
//        BoardPost model = webTestClient.get().uri("/board-post/list").exchange()
//                .expectStatus().isOk()
//                .expectBody(BoardPost.class)
//                .returnResult().getResponseBody();
//
//        assertThat(model).isEqualTo("title");
//    }
//
//}
