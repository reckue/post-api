package com.reckue.post.exceptions.models.post;

import com.reckue.post.exceptions.ModelNotFoundException;

public class PostNotFoundException extends ModelNotFoundException {

    private final String message;

    public PostNotFoundException() {
        this.message = "Post Not Found";
    }

    public PostNotFoundException(String id) {
        this.message = "Post by id " + id + " is not found";
    }
}