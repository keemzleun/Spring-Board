package com.beyond.board.post.controller;

import java.util.List;

import com.beyond.board.post.dto.PostUpdateDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.beyond.board.post.dto.PostDetResDto;
import com.beyond.board.post.dto.PostSaveReqDto;
import com.beyond.board.post.dto.PostListResDto;
import com.beyond.board.post.service.PostService;

@Controller
public class PostController {

	private final PostService postService;

	@Autowired
	PostController(PostService postService) {
		this.postService = postService;
	}

	// 	생성
	@GetMapping("/post/register")
	public String postCreateScreen(){
		return "post/post_register";
	}
	@PostMapping("/post/register")
	public String postCreate(@ModelAttribute PostSaveReqDto dto) {
		postService.postCreate(dto);
		return "redirect:/post/list";
	}

	// 	조회
	@GetMapping("/post/list")
	public String postList(Model model) {
		model.addAttribute("postList", postService.postList());
		return "post/post_list";
	}

	@GetMapping("/post/detail/{id}")
	public String postDetail(@PathVariable Long id, Model model){
		model.addAttribute("post", postService.postDetail(id));
		return "post/post_detail";
	}

	@GetMapping("/post/delete/{id}")
	public String postDelete(@PathVariable Long id, Model model){
		postService.delete(id);
		return "redirect:/post/list";
	}

	@PostMapping("/post/update/{id}")
	public String postUpdate(@PathVariable Long id, @ModelAttribute PostUpdateDto dto, Model model){
		postService.update(id, dto);
		return "redirect:/post/detail/" + id;
	}

}
