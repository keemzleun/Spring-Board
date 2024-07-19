package com.beyond.board.author.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.common.BaseTimeEntity;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.post.domain.Post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
// @Builder를 통해 매개변수의 순서, 매개변수의 갯수 등을 유연하게 셋팅하여 생성자로서 활용
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Author extends BaseTimeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(length = 20, nullable = false)
	private String name;
	@Column(nullable = false, length = 20, unique = true)
	private String email;
	@Column(nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	private Role role;

	// 일반적으로 부모엔티티(자식 객체에 영향을 끼칠 수 있는 엔티티)에 cascade 옵션을 설정
	@OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Post> posts = new ArrayList<>();

	public void updateAuthor(AuthorUpdateDto dto) {
		this.name = dto.getName();
		this.password = dto.getPassword();
	}
}

