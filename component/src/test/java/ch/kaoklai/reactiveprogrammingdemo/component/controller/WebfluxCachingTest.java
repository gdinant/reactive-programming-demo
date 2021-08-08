package ch.kaoklai.reactiveprogrammingdemo.component.controller;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.DEFINED_PORT;

import ch.kaoklai.reactiveprogrammingdemo.model.GitInfo;
import ch.kaoklai.reactiveprogrammingdemo.service.GitInfoService;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@SpringBootTest(webEnvironment = DEFINED_PORT)
public class WebfluxCachingTest {

	@Autowired
	private GitInfoService gitInfoService;

	@Test
	void testCachingViaWebService() {

		var client = WebClient.builder().baseUrl("http://localhost:9000").build();

		retrieveGitInfo(client, 1);

		retrieveGitInfo(client, 2);

		retrieveGitInfo(client, 3);
	}

	private void retrieveGitInfo(WebClient client, int id) {

		client.get().uri("/git-info")
			.retrieve()
			.bodyToFlux(GitInfo.class)
			.doOnComplete(()  -> log.info("CALL [{}] All git info received!", id))
			.collectList()
			.doOnNext(c -> log.info("CALL [{}] number of info received [{}]", id, c.size()))
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
