package com.biblioteca.controller;

import com.biblioteca.model.Book;
import com.biblioteca.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/livros")
public class BookController {

    private final BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Book> cadastrar(@Valid @RequestBody Book book) {
        return new ResponseEntity<>(bookService.cadastrar(book), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Book> listar() {
        return bookService.listar();
    }

    @GetMapping("/{id}")
    public Book consultar(@PathVariable Long id) {
        return bookService.buscarPorId(id);
    }

    @PutMapping("/{id}")
    public Book atualizar(@PathVariable Long id, @Valid @RequestBody Book book) {
        return bookService.atualizar(id, book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id) {
        bookService.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
