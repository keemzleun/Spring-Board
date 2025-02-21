package com.beyond.board.post.domain;

import java.time.LocalDateTime;

import javax.persistence.*;

import com.beyond.board.common.BaseTimeEntity;
import com.beyond.board.post.dto.PostUpdateDto;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.beyond.board.author.domain.Author;
import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostListResDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Post extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 50, nullable = false)
	private String title;
	@Column(length = 30000)
	private String contents;

	// 연관관계의 주인은 fk가 있는 post - 보통 Many 쪽이 연관관계의 주인이라고 들음
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "author_id")
	private Author author;

	private String appointment;
	private LocalDateTime appointmentTime;



	public PostListResDto listFromEntity(){
		return PostListResDto.builder()
			.id(this.id)
			.title(this.getTitle())
//			.author(this.author)
			.author_email(this.author.getEmail())
			.build();
	}

	public PostDetResDto detFromEntity(){
		return PostDetResDto.builder()
			.id(this.id)
			.title(this.title)
			.contents(this.contents)
			.author_email(this.author.getEmail())
			.createdTime(this.getCreatedTime())
			.updatedTime(this.getUpdatedTime())
			.build();
	}

	public void updateAppointment(String yn){
		this.appointment = yn;
	}

	public void updatePost(PostUpdateDto dto){
		this.title = dto.getTitle();
		this.contents = dto.getContents();
	}
}
