package gr.unipi.ddim.meallabserver;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import gr.unipi.ddim.meallabapi.api.MealClient;

@SpringBootTest
class MealLabServerApplicationTests {

	@Autowired
	private MealClient mealClient;

	@Test
	void contextLoads() {
	}

	@Test
	void mealClientBeanIsAvailable() {
		assertNotNull(mealClient);
	}

}
