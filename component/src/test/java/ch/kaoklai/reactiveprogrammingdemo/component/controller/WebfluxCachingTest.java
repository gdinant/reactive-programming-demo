package ch.kaoklai.reactiveprogrammingdemo.component.controller;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import java.time.Duration;

import ch.kaoklai.reactiveprogrammingdemo.model.GitInfo;
import ch.kaoklai.reactiveprogrammingdemo.service.GitInfoService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class WebfluxCachingTest {

	@Autowired
	private GitInfoService gitInfoService;

	@Test
	void test() {

		Flux.range(1, 100)
			.log()
			.concatMap(x -> Mono.delay(Duration.ofMillis(100))) // simulate that processing takes time
			.blockLast();

		Flux.interval(Duration.ofMillis(1))
			.log()
			.concatMap(x -> Mono.delay(Duration.ofMillis(100))) // simulate that processing takes time
			.blockLast();
	}

	@Test
	void testCachingViaWebService() {

		var client = WebClient.builder().baseUrl("http://localhost:9000").build();

		retrieveGitInfo(client, 1);

		//retrieveGitInfo(client, 2);

		//retrieveGitInfo(client, 3);
	}

	private void retrieveGitInfo(WebClient client, int id) {

		client.get().uri("/persons")
			.retrieve()
			.bodyToFlux(GitInfo.class)
			.log()
			.limitRate(1000)
			.collectList()
			.block();
	}

	@Test
	void testCachingDirectCallToService() throws InterruptedException {

		var jsonFlux = gitInfoService.findGitInfo();

		jsonFlux.doOnComplete(() -> log.info("[1] Retrieved the file successfully"))
			.collectList()
			.doOnNext(i -> log.info("NB elements: [{}]", i.size()))
			.subscribe();

		Thread.sleep(5000);

		jsonFlux.doOnComplete(() -> log.info("[2] Retrieved the file successfully"))
			.collectList()
			.doOnNext(i -> log.info("NB elements: [{}]", i.size()))
			.subscribe();

		Thread.sleep(5000);
	}

}
