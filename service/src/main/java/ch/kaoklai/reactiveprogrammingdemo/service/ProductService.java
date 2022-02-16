package ch.kaoklai.reactiveprogrammingdemo.service;

import java.util.stream.IntStream;

import ch.kaoklai.reactiveprogrammingdemo.service.model.Product;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class ProductService {

	public Flux<Product> findProducts() {

		var products = IntStream.range(0, 100).mapToObj(i -> new Product("product-" + i));

		return Flux.fromStream(products);
	}

}
