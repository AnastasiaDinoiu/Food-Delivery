package service;

import domain.Product;
import exceptions.CustomException;
import persistence.ProductRepository;

import java.util.List;

public class ProductService {

    private final ProductRepository productRepository = ProductRepository.getInstance();

    public Product registerNewProduct(Integer restaurantID, String name, Double price) throws CustomException, InstantiationException, IllegalAccessException {
        return productRepository.save(new Product(restaurantID, name, price));
    }

    public List<Product> getAllProducts() throws InstantiationException, IllegalAccessException {
        return productRepository.findAll();
    }

    public void removeProduct(String name) throws CustomException, InstantiationException, IllegalAccessException {
        productRepository.findById(name)
                .orElseThrow(() -> new CustomException("Cannot delete the provided product. It does not exist!"));
        productRepository.delete(name);
    }
}
