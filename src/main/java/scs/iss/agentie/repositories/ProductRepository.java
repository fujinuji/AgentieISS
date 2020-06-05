package scs.iss.agentie.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import scs.iss.agentie.entities.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    @Query("SELECT p FROM Product p ")
    List<Product> getAll();

    @Query("SELECT p FROM Product p WHERE p.id=?1")
    Product getById(@Param("id") Integer id);

    @Modifying
    @Transactional
    @Query(value="UPDATE Product SET productName=:#{#product.productName}, price=:#{#product.price}, amount=:#{#product.amount} WHERE id=:#{#product.id}")
    void updateProduct(@Param("product") Product product);

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO Product SET product_name=:#{#product.productName}, price=:#{#product.price}, amount=:#{#product.amount}", nativeQuery=true)
    void addProduct(@Param("product") Product product);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM Product WHERE id=?1", nativeQuery=true)
    void deleteProduct(@Param("productId") Integer productId);

}
