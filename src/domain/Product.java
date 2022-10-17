package domain;

public class Product implements Cloneable {
    private Integer productID;
    private Integer restaurantID;
    private String name;
    private Double price;

    @Override
    public Object clone() throws CloneNotSupportedException {
        Product product = new Product();
        product.restaurantID = restaurantID;
        product.name = name;
        product.price = price;

        return product;
    }

    public Product() {
    }

    public Product(Integer restaurantID, String name, Double price) {
        this.restaurantID = restaurantID;
        this.name = name;
        this.price = price;
    }

    public Product(Product product) {
        this.productID = product.productID;
        this.restaurantID = product.restaurantID;
        this.name = product.name;
        this.price = product.price;
    }

    public Integer getProductID() {
        return productID;
    }

    public void setProductID(Integer productID) {
        this.productID = productID;
    }

    public Integer getRestaurantID() {
        return restaurantID;
    }

    public void setRestaurantID(Integer restaurantID) {
        this.restaurantID = restaurantID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Product{" +
                "productID=" + productID +
                ", restaurantID=" + restaurantID +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
