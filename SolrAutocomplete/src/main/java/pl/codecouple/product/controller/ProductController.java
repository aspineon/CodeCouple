package pl.codecouple.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetFieldEntry;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import pl.codecouple.product.model.Product;
import pl.codecouple.product.service.ProductService;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Created by Krzysztof Chruściel.
 */
@Slf4j
@Controller
public class ProductController {

    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping(value = "/autocomplete", produces = "application/json")
    public @ResponseBody
    Set<String> autoComplete(@RequestParam("term") String query,
                             @PageableDefault(page = 0, size = 1) Pageable pageable) {
        if (!StringUtils.hasText(query)) {
            return Collections.emptySet();
        }

        FacetPage<Product> result = productService.autocomplete(query, pageable);

        Set<String> titles = new LinkedHashSet<>();
        for (Page<FacetFieldEntry> page : result.getFacetResultPages()) {
            for (FacetFieldEntry entry : page) {
                Optional<String> entryValue = Optional.ofNullable(entry.getValue());
                if(entryValue.isPresent() && entryValue.get().contains(query.toLowerCase())){
                    titles.add(StringUtils.capitalize(entryValue.get()));
                }
            }
        }
        return titles;
    }

    @GetMapping("/")
    public String showSearchPage(){
        return "index";
    }

}
