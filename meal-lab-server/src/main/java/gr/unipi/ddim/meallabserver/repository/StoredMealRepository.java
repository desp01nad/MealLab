package gr.unipi.ddim.meallabserver.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.unipi.ddim.meallabserver.entity.StoredMeal;

public interface StoredMealRepository extends JpaRepository<StoredMeal, String> {

    List<StoredMeal> findByNameContainingIgnoreCase(String name);
}
