package com.prokectB.meongbti.support.paging;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CursorRequest {
    public static final Long NONE_KEY = -1L;
    private static final Integer DEFAULT_SIZE = 10;
    private static final Integer MAX_SIZE = 100;

    private Long key = NONE_KEY;
    private Integer size = DEFAULT_SIZE;

    public CursorRequest(Long key, Integer size) {
        this.key = (key == null) ? NONE_KEY : key;
        this.size = size == null ? DEFAULT_SIZE : Math.min(size, MAX_SIZE);
    }

    public Boolean hasKey() {
        return key != null && !key.equals(NONE_KEY);
    }

    public CursorRequest next(Long key) {
        return new CursorRequest(key, this.size);
    }
}