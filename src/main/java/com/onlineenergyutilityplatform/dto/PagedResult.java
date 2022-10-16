package com.onlineenergyutilityplatform.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * Dto to enable pagination at bulk requests
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PagedResult<T> {
    @JsonProperty("content")
    private List<T> content;
    @JsonProperty("page")
    private PageInfo page;
}
