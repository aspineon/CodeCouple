package pl.codecouple.optional.develop.model;

import java.util.Optional;

public class Product {

    private long productID;
    private String productName;
    private String productDescription;

    public Product() {
    }

    public Product(long productID, String productName, String productDescription) {
        this.productID = productID;
        this.productName = productName;
        this.productDescription = productDescription;
    }

    public Optional<Long> getProductID() {
        return Optional.ofNullable(productID);
    }

    public void setProductID(long productID) {
        this.productID = productID;
    }

    public Optional<String> getProductName() {
        return Optional.ofNullable(productName);
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Optional<String> getProductDescription() {
        return Optional.ofNullable(productDescription);
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", productName='" + productName + '\'' +
                ", productDescription='" + productDescription + '\'' +
                '}';
    }
}
