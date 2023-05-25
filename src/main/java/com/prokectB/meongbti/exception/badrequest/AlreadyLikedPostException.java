package com.prokectB.meongbti.exception.badrequest;

public class AlreadyLikedPostException extends InvalidRequestException {

    public AlreadyLikedPostException() {
        addValidation("post", "이미 좋아요 한 글입니다.");
    }
}