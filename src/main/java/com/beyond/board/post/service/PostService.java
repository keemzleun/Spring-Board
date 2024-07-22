package com.beyond.board.post.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.beyond.board.post.dto.PostUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.author.service.AuthorService;
import com.beyond.board.post.domain.Post;
import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.repository.PostRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PostService {
	private final PostRepository postRepository;
	private final AuthorService authorService;
	// service 또는 repository를 autowired할지는 상황에 따라 다르나,
	// serviceㄷ레벨에서 코드가 고도화되어있고, 코드의 중복이 심할경우, service레이어를 autowired
	// 그러나, 순환참조가 발생하는 경우에는 repository를 autowired
	// 때문에 상황에 따라 repository, service 중 어떤 것을 autowired할지 선택해야한다.

	@Autowired
	PostService(PostRepository postRepository, AuthorService authorService) {
		this.postRepository = postRepository;
		this.authorService = authorService;
	}

	// 	생성
	// authorservice에서 author객체를 찾아 post의 toEntity에 넘기고
	// jpa가 author객체에서 author_id를 찾아 db에는 author_id가 들어감.
	public Post postCreate(PostSaveReqDto dto) {
		// Author author = authorRepository.findByEmail(dto.getEmail())
		// 	.orElseThrow(() -> new EntityNotFoundException("author not found"));

		Author author = authorService.authorFindByEmail(dto.getEmail());
		String appointment = null;
		LocalDateTime appointmentTime = null;

		if (dto.getAppointment().equals("Y") && !dto.getAppointmentTime().isEmpty()){
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
			appointmentTime = LocalDateTime.parse(dto.getAppointmentTime(), dateTimeFormatter);
			LocalDateTime now = LocalDateTime.now();
			if(appointmentTime.isBefore(now)){
				throw new IllegalArgumentException("시간 입력이 잘못되었습니다.");
			}

		}
		System.out.println(dto);
		Post post = postRepository.save(dto.toEntity(author, appointmentTime));
		Post savedPost = postRepository.save(post);

		return savedPost;
	}

	// 	조회
	public Page<PostListResDto> postList(Pageable pageable) {
//		List<Post> postList = postRepository.findAllFetch();
//		List<PostListResDto> postListResDtos = new ArrayList<PostListResDto>();
//		for (Post post : postList) {
//			postListResDtos.add(post.listFromEntiy());
//		}
//		Page<Post> posts = postRepository.findAll(pageable);
		Page<Post> posts = postRepository.findByAppointment(pageable, "N");
		Page<PostListResDto> postListResDtos = posts.map(a->a.listFromEntiy());
		return postListResDtos;
	}

	public Page<PostListResDto> postListPage(Pageable pageable){
		Page<Post> posts = postRepository.findAll(pageable);
		Page<PostListResDto> postListResDtos = posts.map(a->a.listFromEntiy());
		return postListResDtos;
	}

	// 상세 조회
	public PostDetResDto postDetail(Long id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("post is not found"));
		return post.detFromEntity();
	}

	// 삭제
	@Transactional
	public void delete(Long id){
		postRepository.deleteById(id);
	}

	// 수정
	@Transactional
	public Post update(Long id, PostUpdateDto dto){
		Post post = postRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("없는 id입니다"));
		post.updatePost(dto);
		return postRepository.save(post);
	}


}
