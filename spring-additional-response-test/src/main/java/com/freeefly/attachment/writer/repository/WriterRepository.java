package com.freeefly.attachment.writer.repository;

import com.freeefly.attachment.writer.model.Writer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

public interface WriterRepository extends JpaRepository<Writer, Long> {

}
