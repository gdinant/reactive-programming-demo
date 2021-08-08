package ch.kaoklai.reactiveprogrammingdemo.model;

import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.jackson.Jacksonized;

@Getter
@Builder
@Jacksonized
@ToString
public class GitInfo {

	private String id;
	private String type;
	private Actor actor;
	private Repo repo;
	private Payload payload;
	@JsonProperty("public")
	private Boolean isPublic;
	@JsonProperty("created_at")
	private OffsetDateTime createdAt;

	@Getter
	@Builder
	@Jacksonized
	@ToString
	public static class Actor {

		private Long id;
		private String login;
		@JsonProperty("gravatar_id")
		private String gravatarId;
		private String url;
		@JsonProperty("avatar_url")
		private String avatarUrl;

	}

	@Getter
	@Builder
	@Jacksonized
	@ToString
	public static class Repo {

		private Long id;
		private String name;
		private String url;

	}

	@Getter
	@Builder
	@Jacksonized
	@ToString
	public static class Payload {

		private String ref;
		@JsonProperty("ref_type")
		private String refType;
		@JsonProperty("master_branch")
		private String masterBranch;
		private String description;
		@JsonProperty("pusher_type")
		private String pusherType;

	}

}
