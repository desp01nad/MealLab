package gr.unipi.ddim.meallabserver;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import gr.unipi.ddim.meallabapi.api.MealClient;

@SpringBootTest
@ActiveProfiles("test")
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
