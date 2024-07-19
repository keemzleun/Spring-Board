package com.beyond.board.post.dto;

import java.time.LocalDateTime;

import com.beyond.board.post.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class PostDetResDto {
	private Long id;
	private String title;
	private String contents;
	private LocalDateTime createdTime;
	private LocalDateTime updatedTime;
	private String author_email;
}
