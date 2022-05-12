package com.kruger.test.enitity.filter;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class PageFilter {
    Integer page;
    Integer size;
}
