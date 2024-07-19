package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.post.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostListResDto {
	private Long id;
	private String title;
	// Author객체 그 자체를 return하게 되면 Author안에 Post가 재참조되어 순환참조 이슈 발생
	 private String author_email;
//	private Author author;
}
