package gr.unipi.ddim.meallabserver.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stored_meals")
public class StoredMeal {

    @Id
    private String id;

    private String name;

    private String thumbnailUrl;

    protected StoredMeal() {
    }

    public StoredMeal(String id, String name, String thumbnailUrl) {
        this.id = id;
        this.name = name;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }
}
