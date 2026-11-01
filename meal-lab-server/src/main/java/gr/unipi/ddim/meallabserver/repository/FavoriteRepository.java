package gr.unipi.ddim.meallabserver.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gr.unipi.ddim.meallabserver.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    boolean existsByMealId(String mealId);

    void deleteByMealId(String mealId);
}
