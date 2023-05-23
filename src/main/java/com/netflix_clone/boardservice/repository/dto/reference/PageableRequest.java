package com.netflix_clone.boardservice.repository.dto.reference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PageableRequest {
    private Integer page;
    private Integer limit;
}
