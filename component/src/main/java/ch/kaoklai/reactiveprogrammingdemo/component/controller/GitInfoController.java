package ch.kaoklai.reactiveprogrammingdemo.component.controller;

import java.time.Duration;

import ch.kaoklai.reactiveprogrammingdemo.model.GitInfo;
import ch.kaoklai.reactiveprogrammingdemo.service.GitInfoService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@AllArgsConstructor
public class GitInfoController {

	private final GitInfoService gitInfoService;

	@GetMapping(path = "/git-info", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_NDJSON_VALUE })
	public Flux<GitInfo> gitInfo() {

		// ~11K elements
		return gitInfoService.findGitInfo();

		//return gitInfoService.findGitInfo().delayElements(Duration.ofMillis(10));
	}

}
