package pl.codecouple.product.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

/**
 * Created by Krzysztof Chruściel.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@SolrDocument(solrCoreName = "products")
public class Product {

    @Id
    @Field
    private String id;

    @Field(value = "product_name")
    private String productName;

    public Product(String productName) {
        this.productName = productName;
    }
}
