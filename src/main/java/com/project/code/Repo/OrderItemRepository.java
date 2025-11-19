package com.project.code.Repo;

import com.project.code.Model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

  @Modifying
  @Transactional
  @Query("DELETE FROM OrderItem o WHERE o.product.id = :productId")
  void deleteByProductId(Long productId);

}