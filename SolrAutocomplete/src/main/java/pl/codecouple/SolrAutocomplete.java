package pl.codecouple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.codecouple.product.service.ProductService;

/**
 * Created by Krzysztof Chruściel.
 */
@SpringBootApplication
public class SolrAutocomplete implements CommandLineRunner{

    @Autowired
    private ProductService productService;

    public static void main(String[] args) {
        SpringApplication.run(SolrAutocomplete.class, args);
    }

    @Override
    public void run(String... strings) throws Exception {
        productService.addProduct("Code");
        productService.addProduct("Couple");
        productService.addProduct(".pl");
    }
}
