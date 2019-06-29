package com.freeefly.enumerate;

public enum AttachmentType {
    COMMENTS, WRITER;

    public String lowerCaseName(){
        return this.name().toLowerCase();
    }
}
