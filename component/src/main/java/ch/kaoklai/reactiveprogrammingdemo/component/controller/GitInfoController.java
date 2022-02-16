package ch.kaoklai.reactiveprogrammingdemo.component.controller;

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

	@GetMapping("/git-info")
	public Flux<GitInfo> gitInfo() {
		return gitInfoService.findGitInfo();
	}

	@GetMapping(value = "/git-info-stream", produces = MediaType.APPLICATION_NDJSON_VALUE)
	public Flux<GitInfo> gitInfoStream() {
		return gitInfoService.findGitInfo();
	}

}
