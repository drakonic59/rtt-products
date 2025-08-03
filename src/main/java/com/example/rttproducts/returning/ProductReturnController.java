package com.example.rttproducts.returning;

import java.util.List;

import com.example.rttproducts.product.Product;
import com.example.rttproducts.product.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/returns")
public class ProductReturnController {

    private final ProductReturnRepository returnRepository;
    private final ProductRepository productRepository;

    public ProductReturnController(ProductReturnRepository returnRepository,
                                   ProductRepository productRepository) {
        this.returnRepository = returnRepository;
        this.productRepository = productRepository;
    }

    @GetMapping
    public List<ProductReturn> list() {
        return returnRepository.findAll();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductReturn create(@RequestBody CreateReturnRequest request) {
        Product product = productRepository.findById(request.productId()).orElseThrow();
        ProductReturn pr = new ProductReturn();
        pr.setProduct(product);
        pr.setReason(request.reason());
        return returnRepository.save(pr);
    }

    public record CreateReturnRequest(Long productId, String reason) {}
}
