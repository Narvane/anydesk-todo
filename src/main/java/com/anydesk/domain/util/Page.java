package com.anydesk.domain.util;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public record Page<T>(List<T> content, int page, int size, long totalElements) {

    public <R> Page<R> map(Function<? super T, ? extends R> mapper) {
        List<R> mappedContent = content.stream()
                .map(mapper)
                .collect(Collectors.toList());
        return new Page<>(mappedContent, page, size, totalElements);
    }

}
