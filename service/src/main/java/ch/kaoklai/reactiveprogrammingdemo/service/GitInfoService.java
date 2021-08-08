package ch.kaoklai.reactiveprogrammingdemo.service;

import ch.kaoklai.reactiveprogrammingdemo.model.GitInfo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ResolvableType;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Slf4j
@Service
@AllArgsConstructor
public class GitInfoService {

	public Flux<GitInfo> findGitInfo() {

		var file = DataBufferUtils.read(
			new DefaultResourceLoader().getResource("file.json"),
			new DefaultDataBufferFactory(),
			4096
		);

		var jacksonDecoder = new Jackson2JsonDecoder();
		jacksonDecoder.setMaxInMemorySize(30 * 1024 * 1024);

		return jacksonDecoder
			.decodeToMono(file, ResolvableType.forClass(GitInfo[].class), null, null)
			.doOnNext(c -> log.info("File is loaded!"))
			.map(i -> ((GitInfo[]) i))
			.flatMapMany(Flux::fromArray)
			.cache();
	}

}