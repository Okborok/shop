package com.okborok.web.shop.controller;

import com.okborok.web.shop.dto.CategoryTile;
import com.okborok.web.shop.enums.I18n;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

/**
 * Created on 28.10.2017.
 */
@Controller
@RequestMapping("/")
public class HomeController {

    private final static String CATEGORIES_ATTR = "categoryTiles";

    @GetMapping
    public String home(final Model model) {

        /*ArrayList<CategoryTile> categoryTiles = new ArrayList<CategoryTile>() {{ // imgPath: /images/tiles/tile1.png
            add(CategoryTile.builder().name(I18n.getMessage("")).imgPath(I18n.getMessage("")).build());
            add(CategoryTile.builder().name(I18n.getMessage("")).imgPath(I18n.getMessage("")).build());
            add(CategoryTile.builder().name(I18n.getMessage("")).imgPath(I18n.getMessage("")).build());
        }};
/*
        final ArrayList<String> categories = new ArrayList<String>() {{
            add(I18n.getMessage("category.1"));
            add(I18n.getMessage("category.2"));
            add(I18n.getMessage("category.3"));
            add(I18n.getMessage("category.4"));
        }};*/
//        model.addAttribute(CATEGORIES_ATTR, categoryTiles);
        return "main";
    }

    @GetMapping("/contact")
    public String contacts(final Model model) {

        return "contact";
    }
}
