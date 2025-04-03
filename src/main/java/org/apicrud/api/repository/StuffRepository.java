package org.apicrud.api.repository;

import org.apicrud.api.model.Status;
import org.apicrud.api.model.Stuff;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StuffRepository extends JpaRepository<Stuff, Integer> {

    List<Stuff> findByStatusOrderByDateDesc(Status status);

    @Query("SELECT s FROM Stuff s LEFT JOIN FETCH s.categories WHERE s.status = :status ORDER BY s.date DESC")
    List<Stuff> findAllWithCategories(@Param("status") Status status);

    @Query("SELECT s FROM Stuff s LEFT JOIN FETCH s.categories WHERE s.id = :id AND s.status = :status")
    Optional<Stuff> findByIdAndStatus(@Param("id") Integer id, @Param("status") Status status);

    @Modifying
    @Query("UPDATE Stuff s SET s.status = 'OFF' WHERE s.id = :id AND s.status = 'ON'")
    int softDelete(@Param("id") Integer id);

    @Query("SELECT s FROM Stuff s LEFT JOIN FETCH s.categories WHERE s.id = :id")
    Optional<Stuff> findByIdWithCategories(@Param("id") Integer id);
}