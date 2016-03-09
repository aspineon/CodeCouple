package pl.codecouple.optional.develop;


import pl.codecouple.optional.develop.model.Product;

import java.util.Optional;

public class Runner {
    public static void main(String[] args) {
        Product cup = new Product();
        if (cup.getProductName() != null) {
            if (cup.getProductName().equals("Cup")) {
                System.out.println("Cup!");
            }
        }


        System.out.println(cup.getProductName()
                .filter(productName -> productName.contains("tea"))
                .map(productName -> "Product: " + productName)
                .orElse("Without tea in name :(")
        );

        cup.setProductName("Test");

        Optional<String> productName = cup.getProductName();


        Optional.ofNullable(productName).ifPresent(System.out::println);

        if (productName.isPresent()){
            System.out.println(productName.get());
        }


    }
}
