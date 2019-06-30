package com.freeefly.attachment.writer.repository;

import com.freeefly.attachment.writer.model.Writer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Repository
public class WriterRepository {

    private long generatedId = 0L;
    private Map<Long, Writer> writerMap = new ConcurrentHashMap<>();

    public void saveAll(List<Writer> writers) {
        writers.forEach(writer -> {
            writer.setId(++generatedId);
            writerMap.put(writer.getId(), writer);
        });
    }

    public Writer findById(Long id){
        return writerMap.get(id);
    }

}
