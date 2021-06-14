package com.kubstu.programm.service;

import com.kubstu.programm.entity.Book;
import com.kubstu.programm.exception.BookNotFoundException;
import com.kubstu.programm.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;


@Service
@Transactional
public class BookService {
    public static final int BOOK_PER_PAGE = 100;

    @Autowired
    private BookRepository bookRepository;

    public Page<Book> listByPage(int pageNum, String sortField, String sortDir, String keyword) {
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();

        Pageable pageable = PageRequest.of(pageNum - 1, BOOK_PER_PAGE, sort);

        if(keyword != null){
            return bookRepository.findAll(keyword, pageable);
        }
        return bookRepository.findAll(pageable);
    }

    public Book save(Book book) {
        return bookRepository.save(book);
    }


    public boolean isNameUnique(Integer id, String name){
        Book bookByName = bookRepository.getBookByName(name);

        if( id != null && bookByName == null) {
            return true;
        }
        else if( id != null && bookByName != null) {
            if(!bookByName.getId().equals(id))
                return false;
            else
                return true;
        }
        else if( id == null && bookByName != null) {
            return false;
        }
        else if(id == null && bookByName == null) {
            return true;
        }
        return false;
    }

    public Book getBookById(Integer id) throws BookNotFoundException {
        try {
            return bookRepository.findById(id).get();
        } catch (NoSuchElementException exception){
            throw new BookNotFoundException("Could not find any book with ID");
        }
    }

    public List<Book> getBooks(){
        return (List<Book>) bookRepository.findAll();
    }

    public void delete(Integer id) throws BookNotFoundException{
        Long countById = bookRepository.countById(id);
        if(countById == null || countById == 0){
            throw new BookNotFoundException("Could not find any book with ID" + id);
        }
        bookRepository.deleteById(id);
    }

}