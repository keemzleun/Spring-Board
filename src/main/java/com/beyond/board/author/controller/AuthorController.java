package com.beyond.board.author.controller;

import java.util.List;

import com.beyond.board.author.domain.Author;
import com.beyond.board.author.dto.AuthorUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.beyond.board.author.dto.AuthorDetResDto;
import com.beyond.board.author.dto.AuthorSaveReqDto;
import com.beyond.board.author.dto.AuthorListResDto;
import com.beyond.board.author.service.AuthorService;

@Controller
public class AuthorController {

	private final AuthorService authorService;

	@Autowired
	AuthorController(AuthorService authorService) {
		this.authorService = authorService;
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
		//		return authorService.authorDetail(id);
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
