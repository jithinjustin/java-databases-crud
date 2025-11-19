package com.project.code.Repo;

import com.project.code.Model.Review;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface ReviewRepository extends MongoRepository<Review, String> {
  List<Review> findByStoreIdAndProductId(Long storeId, Long productId);

}