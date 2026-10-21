package com.biblioteca.service;

import com.biblioteca.model.Book;
import com.biblioteca.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book cadastrar(Book book) {
        return bookRepository.save(book);
    }

    public List<Book> listar() {
        return bookRepository.findAll();
    }

    public Book buscarPorId(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado: " + id));
    }

    public Book atualizar(Long id, Book dadosAtualizados) {
        Book book = buscarPorId(id);
        book.setTitulo(dadosAtualizados.getTitulo());
        book.setAutor(dadosAtualizados.getAutor());
        book.setGenero(dadosAtualizados.getGenero());
        book.setAnoPublicacao(dadosAtualizados.getAnoPublicacao());
        book.setStatusLeitura(dadosAtualizados.getStatusLeitura());
        return bookRepository.save(book);
    }

    public void excluir(Long id) {
        Book book = buscarPorId(id);
        bookRepository.delete(book);
    }
}
