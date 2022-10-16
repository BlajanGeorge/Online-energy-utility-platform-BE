package com.onlineenergyutilityplatform.dto;

import lombok.*;

/**
 * Dto for providing info about pagination
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PageInfo {
    private int pageIndex;
    private int pageSize;
    private long elementCount;
    private int pageCount;
}
