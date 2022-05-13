package com.kruger.test.dto.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PageFilter {
    Integer page;
    Integer size;
}
