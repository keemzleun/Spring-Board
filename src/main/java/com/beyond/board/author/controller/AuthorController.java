package com.beyond.board.author.controller;

import java.util.List;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorUpdateDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.service.AuthorService;

@Controller
@Slf4j
public class AuthorController {

	private final AuthorService authorService;

	@Autowired
	AuthorController(AuthorService authorService) {
		this.authorService = authorService;
	}

	// 로그인
	@GetMapping("author/login-screen")
	public String authorLoginScreen(){
		return "author/login-screen";
	}


	// 	생성
	@GetMapping("author/register")
	public String createAuthor(){
		return "author/author_register";
	}
	@PostMapping("/author/register")
	public String createAuthor(AuthorSaveReqDto authorSaveReqDto){
		authorService.authorCreate(authorSaveReqDto);
		return "redirect:/";
	}

	// 	리스트 조회
	@GetMapping("/author/list")
	public String authorList(Model model){
//		return authorService.authorList();
		List<AuthorListResDto> authorListResDtos = authorService.authorList();
		model.addAttribute("authorList", authorListResDtos);
		return "author/author_list";

	}

	// 	상세조회
	@GetMapping("/author/detail/{id}")
	public String authorDetail(@PathVariable Long id, Model model) {
//		try {
//			AuthorDetResDto dto = authorService.authorDetail(id);
//			model.addAttribute("author", dto);
//		} catch (IllegalArgumentException e){
////			e.printStackTrace();
////			log.error(id + e.getMessage());	이런식으로 에러 캐치. @Slf4j 추가해줘야함
//		}
//		//		return authorService.authorDetail(id);

//		log.info("get 요청 & parameter = " + id);
//		log.info("method명 : authorDetail");
		AuthorDetResDto dto = authorService.authorDetail(id);
		model.addAttribute("author", dto);

		return "author/author_detail";
	}

	// author 삭제
	@GetMapping("/author/delete/{id}")
	public String authorDelete(@PathVariable Long id, Model model){
		authorService.delete(id);
		return "redirect:/author/list";
	}

	// author 업데이트
	@PostMapping("/author/update/{id}")
	public String authorUpdate(@PathVariable Long id, @ModelAttribute AuthorUpdateDto dto, Model model){
		authorService.update(id, dto);
		return "redirect:/author/detail/"+id;
	}
}
