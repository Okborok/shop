package com.okborok.web.shop.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * Created on 02.11.2017.
 */
@Builder
@Getter
@Setter
public class CategoryTile {

    private String name;
    private String imgPath;
}
