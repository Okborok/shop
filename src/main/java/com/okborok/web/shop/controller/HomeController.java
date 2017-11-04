package com.okborok.web.shop.controller;

import com.okborok.web.shop.model.Tile;
import com.okborok.web.shop.repository.TileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;

/**
 * Created on 28.10.2017.
 */
@AllArgsConstructor
@Controller
@RequestMapping("/")
public class HomeController {

    private final static String CATEGORIES_ATTR = "categoryTiles";
    private final TileRepository tileRepository;

    @GetMapping
    public String home(final Model model) {

        List<Tile> all = tileRepository.findAll();
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


    @GetMapping("/tile/create")
    public String createTile(final Model model, final Tile tile) {

        model.addAttribute("tile", tile);

        return "tile/create";
    }

    @PostMapping("/tile/create")
    public String saveTile(@Valid final Tile tile, final BindingResult result, final Model model) {
        if (result.hasErrors()) {
            return createTile(model, tile);
        }

        tileRepository.save(tile);
        return "redirect:/";
    }

    @GetMapping("/tile")
    public String tilesList(final Model model, final Tile tile) {
        model.addAttribute("tileList", tileRepository.findAll());
        return "tile/tilesList";
    }
}
