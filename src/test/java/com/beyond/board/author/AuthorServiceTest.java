package com.beyond.board.author;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.domain.Role;
import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorUpdateDto;
import com.beyond.board.author.repository.AuthorRepository;
import com.beyond.board.author.service.AuthorService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@SpringBootTest
@Transactional
public class AuthorServiceTest {
    @Autowired
    private AuthorService authorService;
    @Autowired
    private AuthorRepository authorRepository;

    // SAVE 및 DETAIL 조회
    @Test
    void saveAndFind(){
        AuthorSaveReqDto authorDto = new AuthorSaveReqDto("park", "park123@naver.com", "12341234", Role.ADMIN);
        Author author = authorService.authorCreate(authorDto);
        Author authorDetail = authorRepository.findById(author.getId()).orElseThrow(()->new EntityNotFoundException("없어요"));
        Assertions.assertEquals(authorDetail.getEmail(), authorDto.getEmail());
    }

    // UPDATE 검증
    @Test
    void updateTest(){
        // 1. 객체 create
        AuthorSaveReqDto createAuthor = new AuthorSaveReqDto("jeong", "jeong@naver.com", "12341234", Role.USER);
        Author author = authorService.authorCreate(createAuthor);

        // 2. 수정 작업 (name, password)
        authorService.update(author.getId(), new AuthorUpdateDto("sujeong", "88888888"));

        // 3. 수정 후 재조회 : name과 password가 맞는지 각각 검증
        Author savedAuthor = authorRepository.findById(author.getId()).orElseThrow(()-> new EntityNotFoundException("없음"));
        Assertions.assertEquals("sujeong", savedAuthor.getName());
        Assertions.assertEquals("88888888", savedAuthor.getPassword());

    }

    // findAll 검증
    @Test
    public void findAllTest(){
        // 1. 3개 author객체 저장
        int count = authorRepository.findAll().size();
        AuthorSaveReqDto createAuthor1 = new AuthorSaveReqDto("jeong1", "jeong1@naver.com", "12341234", Role.USER);
        Author author1 = authorService.authorCreate(createAuthor1);
        AuthorSaveReqDto createAuthor2 = new AuthorSaveReqDto("jeong2", "jeong2@naver.com", "12341234", Role.USER);
        Author author2 = authorService.authorCreate(createAuthor2);
        AuthorSaveReqDto createAuthor3 = new AuthorSaveReqDto("jeong3", "jeong3@naver.com", "12341234", Role.USER);
        Author author3 = authorService.authorCreate(createAuthor3);

        // 2. authorList를 조회한 후 저장한 개수와 저장된 개수 비교
        Assertions.assertEquals(count + 3, authorRepository.findAll().size());
        Assertions.assertEquals(count + 3, authorService.authorList().size());
    }
}
