package com.prokectB.meongbti.common.exception.notfound;

public class PostNotFoundException extends NotFoundException {

    public PostNotFoundException() {
        addValidation("post", "post를 찾을 수 없습니다.");
    }
}