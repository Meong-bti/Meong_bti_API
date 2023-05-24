package com.prokectB.meongbti.common.exception.unauthorized;

public class ImageConvertFileException extends UnauthorizedException  {

    public ImageConvertFileException() {
        addValidation("image", "파일 전환 실패");
    }
}
