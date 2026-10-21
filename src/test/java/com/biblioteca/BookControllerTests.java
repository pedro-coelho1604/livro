package com.biblioteca;

import com.biblioteca.model.Book;
import com.biblioteca.model.StatusLeitura;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Book livroExemplo() {
        Book book = new Book();
        book.setTitulo("O Hobbit");
        book.setAutor("J.R.R. Tolkien");
        book.setGenero("Fantasia");
        book.setAnoPublicacao(1937);
        book.setStatusLeitura(StatusLeitura.NAO_LIDO);
        return book;
    }

    @Test
    void deveCadastrarListarConsultarAtualizarEExcluirLivro() throws Exception {
        // Cadastrar
        String resposta = mockMvc.perform(post("/livros")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(livroExemplo())))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("O Hobbit"))
                .andReturn().getResponse().getContentAsString();

        Long id = objectMapper.readTree(resposta).get("id").asLong();

        // Listar
        mockMvc.perform(get("/livros"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("O Hobbit"));

        // Consultar
        mockMvc.perform(get("/livros/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.autor").value("J.R.R. Tolkien"));

        // Atualizar
        Book atualizado = livroExemplo();
        atualizado.setStatusLeitura(StatusLeitura.LIDO);
        mockMvc.perform(put("/livros/{id}", id)
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(atualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusLeitura").value("LIDO"));

        // Excluir
        mockMvc.perform(delete("/livros/{id}", id))
                .andExpect(status().isNoContent());

        // Consultar após exclusão -> 404
        mockMvc.perform(get("/livros/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void deveRejeitarLivroSemTitulo() throws Exception {
        Book invalido = livroExemplo();
        invalido.setTitulo("");

        mockMvc.perform(post("/livros")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(invalido)))
                .andExpect(status().isBadRequest());
    }
}
