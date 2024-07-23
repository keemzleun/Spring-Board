package com.beyond.board.author.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.post.domain.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.repository.AuthorRepository;

@Service
// 조회 작업시에 readOnly 설정하면 성능 향상.
// 다만, 저장 작업시에는 Transactional 필요
@Transactional(readOnly = true)
public class AuthorService {
	private final AuthorRepository authorRepository;

	@Autowired
	AuthorService(AuthorRepository authorRepository) {
		this.authorRepository = authorRepository;
	}

	// 생성
	@Transactional
	public Author authorCreate(AuthorSaveReqDto dto){
		if (authorRepository.findByEmail(dto.getEmail()).isPresent()) {
			throw new IllegalArgumentException("이미 존재하는 email입니다.");
		}
		if(dto.getPassword().length() < 8){
			throw new IllegalArgumentException("비밀번호가 너무 짧아욘");
		}
		Author author = dto.toEntity();
		// cascade persist 테스트. remove 테스트는 회원 삭제로 대체
		author.getPosts().add(Post.builder().title("가입인사").author(author).contents("안녕하세요" + dto.getName()+"입니다.").build());
		Author savedAuthor = authorRepository.save(author);

		// 오류메시지가 같다면 여기에서 authorService.authorFindByEmail을 사용할 수 있음
		return savedAuthor;
	}

	// 	리스트 조회
	public List<AuthorListResDto> authorList(){
		List<Author> authorList = authorRepository.findAll();
		List<AuthorListResDto> authorListResDtos = new ArrayList<>();
		for (Author author : authorList) {
			authorListResDtos.add(new AuthorListResDto().fromEntity(author));
		}
		return authorListResDtos;
	}

	// 	상세 조회
	public AuthorDetResDto authorDetail(Long id){
		Author author = authorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("member is not found"));

		return new AuthorDetResDto().fromEntity(author);
	}

	public Author authorFindByEmail(String email) {
		return authorRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("해당 이메일의 사용자는 없습니다"));
	}

	@Transactional
	public void delete(Long id){
//		Author author = authorRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("그런 회원 없습니다;;;"));
//		authorRepository.delete(author);
		authorRepository.deleteById(id);
	}

	@Transactional
	public void update(Long id, AuthorUpdateDto dto){
		Author author = authorRepository.findById(id).orElseThrow(()->new EntityNotFoundException("그런 회원 없습니다;;;"));
		author.updateAuthor(dto);
		// jpa가 특정 엔티티의 변경을 자동으로 인지하고 변경사항을 DB에 반영하는 것이 dirtyChecking(변경 감지)
		// authorRepository.save(author);  // 그래서 save 안해줘도 됨. 대신 @Transactional은 반드시 붙어있어야 함
	}
}
