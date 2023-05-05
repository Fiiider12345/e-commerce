package com.ecommerce.dao;

import org.springframework.data.repository.CrudRepository;

import com.ecommerce.entity.Product;

public interface ProductDao extends CrudRepository<Product, Integer> {

}
