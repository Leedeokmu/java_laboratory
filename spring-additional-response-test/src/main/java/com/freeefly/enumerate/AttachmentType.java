package com.freeefly.enumerate;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public enum AttachmentType {
    COMMENTS, WRITER;

    private static Map<String, AttachmentType> nameToTypeMap;
    public String lowerCaseName(){
        return this.name().toLowerCase();
    }

    public static AttachmentType fromCaseIgnoredName(String name){
        if(nameToTypeMap == null){
            initMap();
        }

        return nameToTypeMap.get(name.toUpperCase());
    }

    private static void initMap() {
        Map<String, AttachmentType> tmp = Stream.of(AttachmentType.values())
                .collect(Collectors.toMap(type -> type.name().toUpperCase(), Function.identity()));
        nameToTypeMap = Collections.unmodifiableMap(tmp);
    }
}
