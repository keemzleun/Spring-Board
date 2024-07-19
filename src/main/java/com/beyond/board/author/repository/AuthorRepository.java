package com.beyond.board.author.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beyond.board.author.domain.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
	// finyBy컬럼명의 규칙으로 자동으로 where조건문을 사용한 쿼리 생성
	// 그외 : findByNameAndEmail, findByNameOrEmail
	// findByAgeBetween(int start, int end)
	// findByAgeLessThan(int age), findByAgeGreaterThan(int age)
	// fintByAgeIsNull, fintByAgeIsNotNull
	// findByAllOrderByEmail()

	Optional<Author> findByEmail(String email);
}
