package gr.unipi.ddim.meallabserver.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "cooked_entries")
public class CookedEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "meal_id")
    private StoredMeal meal;

    @Column(nullable = false)
    private int rating;

    @Column(nullable = false)
    private Instant cookedAt;

    protected CookedEntry() {
    }

    public CookedEntry(StoredMeal meal, int rating) {
        this.meal = meal;
        this.rating = rating;
    }

    @PrePersist
    void setCookedAt() {
        cookedAt = Instant.now();
    }

    public Long getId() {
        return id;
    }

    public StoredMeal getMeal() {
        return meal;
    }

    public int getRating() {
        return rating;
    }

    public Instant getCookedAt() {
        return cookedAt;
    }
}
