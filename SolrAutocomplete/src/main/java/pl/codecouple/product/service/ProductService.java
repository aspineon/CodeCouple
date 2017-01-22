package pl.codecouple.product.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import pl.codecouple.product.model.Product;

/**
 * Created by Krzysztof Chruściel.
 */
public interface ProductService {

    FacetPage<Product> autocomplete(String query, Pageable pageable);
    void addProduct(String productName);

}
