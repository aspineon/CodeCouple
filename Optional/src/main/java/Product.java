import org.junit.platform.commons.util.StringUtils;

import java.util.Optional;

/**
 * Created by CodeCouple.pl
 */
class Product {

    private final String productName;

    Product(String productName) {
        this.productName = productName;
    }

    void methodWithOptional(
            final String value,
            final Optional<String> secondValue) {
        // some logic here
    }

    void methodOverload1(final String value) {
        // some logic here
    }

    void methodOverload2(
            final String value,
            final String secondValue) {
        // some logic here
    }

    String getProductValueBy(final String id, final String productName) {
        return getValueBy(id)
                .filter(product -> product.productName.equals(productName))
                .flatMap(product -> getValueBy(product.productName))
                .map(product -> product.productName)
                .map(String::toUpperCase)
                .orElseGet(this::getDefaultValue);
    }

    String getDefaultValue() {
        return null;
    }

    Optional<Product> getValueBy(final String id) {
        if (StringUtils.isBlank(id)) {
            return Optional.empty();
        }
        return Optional.ofNullable(readFromDB(id));
    }

    Product readFromDB(final String id){
        return null;
    }


}
