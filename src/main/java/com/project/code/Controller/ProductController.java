package com.project.code.Controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.code.Model.Product;
import com.project.code.Repo.InventoryRepository;
import com.project.code.Repo.OrderItemRepository;
import com.project.code.Repo.ProductRepository;
import com.project.code.Service.ServiceClass;

@RequestMapping("/product")
@RestController
public class ProductController {

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private OrderItemRepository orderItemRepository;

  @Autowired
  private ServiceClass serviceClass;

  @Autowired
  private InventoryRepository inventoryRepository;

  @PostMapping
  public Map<String, String> addProduct(@RequestBody Product product) {

    Map<String, String> map = new HashMap<>();
    if (!serviceClass.validateProduct(product)) {
      map.put("message", "Product already present in database");
      return map;
    }
    try {
      productRepository.save(product);
      map.put("message", "Product added successfully");
    }

    catch (DataIntegrityViolationException e) {
      map.put("message", "SKU should be unique");
    }
    return map;
  }

  @GetMapping("/{id}")
  public Map<String, Object> getProductbyId(@PathVariable Long id) {
    System.out.println("result: ");
    System.out.println("result: ");
    System.out.println("result: ");
    Map<String, Object> map = new HashMap<>();
    Product result = productRepository.findByid(id);

    System.out.println("result: "+result);
    map.put("products", result);
    return map;
  }

  @PutMapping
  public ResponseEntity<Map<String,String>> updateProduct(@RequestBody Product product) {
    Map<String, String> map = new HashMap<>();
    Long id = product.getId();
    try {

      if (id==null || !serviceClass.ValidateProductId(id)) {
        map.put("message", "Id " + id + " not present in database");
        return ResponseEntity.badRequest().body(map);
      }

      productRepository.save(product);
      map.put("message", "Data upated sucessfully");
      return ResponseEntity.ok(map);
    } catch (Exception e) {
      map.put("message", "Error occured");
      return new ResponseEntity(map, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/category/{name}/{category}")
  public Map<String, Object> filterbyCategoryProduct(@PathVariable String name,@PathVariable String category) {
    Map<String, Object> map = new HashMap<>();

    if(name.equals("null"))
    {
      map.put("products", productRepository.findByCategory(category));
      return map;
    }
    else if(category.equals("null"))
    {
      map.put("products", productRepository.findProductBySubName(name));
      return map;

    }
    map.put("products",productRepository.findProductBySubNameAndCategory(name,category));
    return map;

  }

  @GetMapping
  public Map<String, Object> listProduct() {

    Map<String, Object> map = new HashMap<>();
    map.put("products",productRepository.findAll());
    return map;
  }




  @GetMapping("filter/{category}/{storeid}")
  public Map<String, Object> getProductbyCategoryAndStoreId(@PathVariable String category,@PathVariable long storeid) {
    Map<String, Object> map = new HashMap<>();
    List<Product> result = productRepository.findByCategoryAndStoreId(storeid, category);

    map.put("product", result);
    return map;
  }

  @DeleteMapping("/{id}")
  public Map<String, String> deleteProduct(@PathVariable Long id) {
    Map<String, String> map = new HashMap<>();

    if (!serviceClass.ValidateProductId(id)) {
      map.put("message", "Id " + id + " not present in database");
      return map;
    }
    inventoryRepository.deleteByProductId(id);
    orderItemRepository.deleteByProductId(id);
    productRepository.deleteById(id);

    map.put("message", "Deleted product successfully with id: " + id);
    return map;
  }

  @GetMapping("/searchProduct/{name}")
  public Map<String, Object> searchProduct(@PathVariable String name) {
    Map<String, Object> map = new HashMap<>();
    map.put("products", productRepository.findProductBySubName(name));
    return map;
  }


}