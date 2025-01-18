package com.Rushikesh.Ecommerce.Controller;

import com.Rushikesh.Ecommerce.Model.Product;
import com.Rushikesh.Ecommerce.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService service;

    @GetMapping("/")
    public String  greet()
    {
        return "Hello World!";
    }

    @GetMapping("/products")
    public List<Product> getAllProducts()
    {
        return service.getAllProducts();
    }

    @GetMapping("/product/{id}")
    public Product getProductById(@PathVariable int id)
    {
        return service.getProductById(id);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {
        try {
            Product product1 = service.addProduct(product, imageFile);
            return new ResponseEntity<>(product1, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id)
    {
        Product product = service.getProductById(id);
        byte[] image = product.getImageData();

        return ResponseEntity.ok()
                .contentType(MediaType.
                        valueOf(product.getImageType()))
                .body(image);
    }

    @PutMapping("/product/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable int id, @RequestPart Product product, @RequestPart MultipartFile imageFile)
    {
        Product product1 = null;
        try {
            product1 = service.updateProduct(id, product, imageFile);
        } catch (IOException e) {
            return new ResponseEntity<>("Update Failed", HttpStatus.BAD_REQUEST);
        }

        if(product1 != null)
        {
            return new ResponseEntity<>("Updated Sucessfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Update Failed", HttpStatus.BAD_REQUEST);
        }
    }


    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id)
    {

        Product p = service.getProductById(id);

        if(p != null)
        {
            service.deleteProduct(id);
            return new ResponseEntity<>("Deleted Sucessfully", HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>("Product NOt Found", HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword)
    {
        List<Product> products = service.searchProducts(keyword);
        System.out.println("Searching with: "+keyword);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
}
