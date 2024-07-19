package com.beyond.board.author.dto;

import java.time.LocalDateTime;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthorDetResDto {
	private Long id;
	private String name;
	private String email;
	private String password;
	private Role role;
	private int postCounts;

	private LocalDateTime createdTime;


	public AuthorDetResDto fromEntity(Author author) {
		return AuthorDetResDto.builder()
			.id(author.getId())
			.name(author.getName())
			.email(author.getEmail())
			.password(author.getPassword())
			.role(author.getRole())
			.postCounts(author.getPosts().size())
			.createdTime(author.getCreatedTime())
			.build();
	}

}
