package com.hadjerbouzid.productservice.controller;

import com.hadjerbouzid.productservice.dto.ProductRequest;
import com.hadjerbouzid.productservice.dto.ProductResponse;
import com.hadjerbouzid.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private void  createProduct(@RequestBody ProductRequest productRequest){
        productService.createProduct(productRequest);
    }


    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getALLProducts(){
        return productService.getAllProducts();
    }


}
