//package com.freeefly.webflux.model;
//
//import lombok.*;
//
//import javax.persistence.*;
//import java.util.Date;
//
////@Builder
//@Data
//@Entity(name = "board_post")
//public class BoardPost {
//    public BoardPost(){}
//    @Column(name = "id", nullable = false)
//    @Id
//    @GeneratedValue(generator = "board-post-seq-gen", strategy = GenerationType.SEQUENCE)
//    @SequenceGenerator(name = "board-post-seq-gen", sequenceName = "board_post_id_seq")
//    private long id;
//
//    @Column(name = "title", length = 200, nullable = false)
//    private String title;
//
//    @Column(name = "content", nullable = false)
//    private String content;
//
//    @Column(name = "created_date", nullable = false)
//    private Date createdAt = new Date();
//
//    @Column(name = "updated_date", nullable = false)
//    private Date updatedAt = new Date();
//
//    @Column(name = "author_id", nullable = false)
//    private int authorId;
//
//    @Column(name = "visibleYn")
//    private Boolean visibleYn = true;
//}
