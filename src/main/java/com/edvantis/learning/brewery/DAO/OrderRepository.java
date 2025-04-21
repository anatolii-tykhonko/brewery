package com.edvantis.learning.brewery.DAO;


import com.edvantis.learning.brewery.model.OrderBeer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface OrderRepository extends JpaRepository<OrderBeer, String> {

    @Modifying
    @Transactional
    @Query("UPDATE OrderBeer o SET o.status = :status WHERE o.id = :id")
    void updateStatusById(@Param("id") String id, @Param("status") String status);

}
