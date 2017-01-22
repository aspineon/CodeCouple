package pl.codecouple.product.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;
import pl.codecouple.product.model.Product;

/**
 * Created by Krzysztof Chruściel.
 */
@Repository
public interface ProductRepository extends SolrCrudRepository<Product, String> {

    @Facet(fields = { "product_name" })
    FacetPage<Product> findByProductNameIgnoreCaseStartingWith(String productName, Pageable pageable);

}
