package com.ll.demo03.global.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.Base64;

import static java.util.Objects.requireNonNull;
import static org.apache.commons.lang3.StringUtils.substringBetween;

@Data
@Slf4j
public class CursorBasedPageable {

    private int size = 5;
    private String nextPageCursor;
    private String prevPageCursor;

    // 기본 생성자 (필수)
    public CursorBasedPageable() {}

    public boolean hasNextPageCursor() {
        return nextPageCursor != null && !nextPageCursor.isEmpty();
    }

    public boolean hasPrevPageCursor() {
        return prevPageCursor != null && !prevPageCursor.isEmpty();
    }

    public boolean hasCursors() {
        return hasPrevPageCursor() || hasNextPageCursor();
    }

    public String getDecodedCursor(String cursorValue) {
        if (cursorValue == null || cursorValue.isEmpty()) {
            throw new IllegalArgumentException("Cursor value is not valid!");
        }
        var decodedBytes = Base64.getDecoder().decode(cursorValue);
        var decodedValue = new String(decodedBytes);
        log.info("decodedValue = {}", decodedValue);

        return substringBetween(decodedValue, "###", "###");
    }

    public String getEncodedCursor(String field, boolean hasPrevOrNextElements) {
        requireNonNull(field);

        if (!hasPrevOrNextElements) return null;

        var structuredValue = "###" + field + "### - " + LocalDateTime.now();
        return Base64.getEncoder().encodeToString(structuredValue.getBytes());
    }

    public String getSearchValue() {
        if (!hasCursors()) return null;

        return hasPrevPageCursor()
                ? getDecodedCursor(prevPageCursor)
                : getDecodedCursor(nextPageCursor);
    }
}