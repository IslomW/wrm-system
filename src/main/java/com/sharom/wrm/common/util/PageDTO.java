package com.sharom.wrm.common.util;

import lombok.*;

import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class PageDTO<T> {

    private int pageNumber;

    private boolean previous;

    private boolean next;

    private int size;

    private long totalElements;

    private int totalPages;

    private List<T> items;
}
