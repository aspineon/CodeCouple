package pl.codecouple.product.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.SolrResultPage;
import org.springframework.stereotype.Service;
import pl.codecouple.product.model.Product;
import pl.codecouple.product.repository.ProductRepository;

import java.util.Collections;

/**
 * Created by Krzysztof Chruściel.
 */
@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public FacetPage<Product> autocomplete(String query, Pageable pageable) {
        if(StringUtils.isBlank(query)) {
            return new SolrResultPage<>(Collections.emptyList());
        }
        return productRepository.findByProductNameIgnoreCaseStartingWith(query, pageable);
    }

    @Override
    public void addProduct(String productName) {
        productRepository.save(new Product(productName));
    }
}
