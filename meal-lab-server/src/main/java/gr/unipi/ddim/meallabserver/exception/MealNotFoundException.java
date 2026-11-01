package gr.unipi.ddim.meallabserver.exception;

public class MealNotFoundException extends RuntimeException {

    public MealNotFoundException(String id) {
        super("No meal found with id " + id);
    }
}
