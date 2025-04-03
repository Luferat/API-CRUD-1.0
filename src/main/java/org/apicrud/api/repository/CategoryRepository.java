package org.apicrud.api.repository;

import org.apicrud.api.model.Category;
import org.apicrud.api.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    List<Category> findByStatusOrderByNameAsc(Status status);

    Optional<Category> findByIdAndStatus(Integer id, Status status);

}