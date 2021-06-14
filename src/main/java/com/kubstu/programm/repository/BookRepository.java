package com.kubstu.programm.repository;

import com.kubstu.programm.entity.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Integer> {
    @Query("SELECT o FROM Book o")
    Book getBookByName(@Param("title") String name);

    Long countById(Integer id);

    @Query("SELECT o FROM Book o")
    Page<Book> findAll(String keyword, Pageable pageable);
}
