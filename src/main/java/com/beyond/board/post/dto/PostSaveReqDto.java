package com.beyond.board.post.dto;

import com.beyond.board.author.domain.Author;
import com.beyond.board.post.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostSaveReqDto {
	private String title;
	private String contents;

	private String email;	// 추후 로그인 기능 이후에는 없어질 dto

	private String appointment;
	private String appointmentTime;

	public Post toEntity(Author author, LocalDateTime appointmentTime){
		return Post.builder()
			.title(this.title)
			.contents(this.contents)
			.author(author)
			.appointment(this.appointment)
			.appointmentTime(appointmentTime)
			.build();
	}




}
