package com.Rushikesh.Ecommerce.Service;

import com.Rushikesh.Ecommerce.Model.Product;
import com.Rushikesh.Ecommerce.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    ProductRepo repo;

    public List<Product> getAllProducts()
    {
        return repo.findAll();
    }

    public Product getProductById(int id)
    {
        return repo.findById(id).orElse(new Product());
    }

    public Product addProduct(Product p, MultipartFile image) throws IOException {
        p.setImageName(image.getOriginalFilename());
        p.setImageType(image.getContentType());
        p.setImageData(image.getBytes());
        return repo.save(p);
    }

    public Product updateProduct(int id, Product product, MultipartFile image) throws IOException {
        product.setImageData(image.getBytes());
        product.setImageName(image.getOriginalFilename());
        product.setImageType(image.getContentType());
        return repo.save(product);
    }

    public void deleteProduct(int id) {
        repo.deleteById(id);
    }

    public List<Product> searchProducts(String keyword)
    {
        return repo.searchProducts(keyword);
    }
}
