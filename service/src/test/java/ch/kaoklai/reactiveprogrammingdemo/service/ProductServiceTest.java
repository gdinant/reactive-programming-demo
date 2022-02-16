package ch.kaoklai.reactiveprogrammingdemo.service;

import static org.junit.jupiter.api.Assertions.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@Slf4j
class ProductServiceTest {

	private ProductService productService;

	@BeforeEach
	void before() {
		this.productService = new ProductService();
	}

	@Test
	void test() {

		var flux = productService.findProducts();

		flux.doOnNext(e -> log.info("Read element: {}", e))
			.doOnComplete(() -> log.info("Received all products!"))
			.collectList()
			.doOnNext(e -> log.info("Number of elements: {}", e.size()))
			.subscribe();
	}

}
