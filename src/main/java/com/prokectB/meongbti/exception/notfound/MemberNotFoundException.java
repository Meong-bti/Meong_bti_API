package com.prokectB.meongbti.exception.notfound;

public class MemberNotFoundException  extends NotFoundException {

    public MemberNotFoundException() {
        addValidation("member", "사용자를 찾을 수 없습니다.");
    }
}
