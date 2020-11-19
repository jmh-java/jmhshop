package com.common.utils.responseResult;

import lombok.Data;

import java.util.List;

@Data
public class PageData<E>  {
    private int currentPage;//当前页码
    private int pageSize;
    private int startRow;
    private int endRow;
    private long totalCount;
    private int pages;

    private List<E> data;
}
