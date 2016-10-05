package com.asu.cloudclan.controller;

public class GetFile {

	private final long id;
    private final String content;

    public GetFile(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        return content;
    }
	
}
