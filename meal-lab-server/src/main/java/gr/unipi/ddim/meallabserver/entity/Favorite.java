package gr.unipi.ddim.meallabserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "favorites")
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    @JoinColumn(name = "meal_id", unique = true)
    private StoredMeal meal;

    protected Favorite() {
    }

    public Favorite(StoredMeal meal) {
        this.meal = meal;
    }

    public StoredMeal getMeal() {
        return meal;
    }
}
