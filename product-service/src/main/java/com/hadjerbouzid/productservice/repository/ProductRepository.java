package com.hadjerbouzid.productservice.repository;

import com.hadjerbouzid.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ProductRepository  extends MongoRepository<Product, String> {
}
