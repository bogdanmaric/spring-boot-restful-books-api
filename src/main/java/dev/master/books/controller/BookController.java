package dev.master.books.controller;

import dev.master.books.model.Book;
import dev.master.books.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/v1/books")
public class BookController {
    @Autowired
    private BookService bookService;
    @Async
    @GetMapping
    public CompletableFuture<ResponseEntity<List<Book>>> getAllBooks() {
        return bookService.getAllBooks()
                .thenApply(books -> ResponseEntity.ok().body(books))
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    @Async
    @GetMapping("/{id}")
    public CompletableFuture<ResponseEntity<Book>> getBookById(@PathVariable Long id) {
        return bookService.getBookById(id)
                .thenApply(book -> ResponseEntity.ok().body(book))
                .exceptionally(ex -> ResponseEntity.notFound().build());
    }
    @Async
    @GetMapping("/author/{name}")
    public CompletableFuture<ResponseEntity<List<Book>>> getBookByAuthor(@PathVariable String name) {
        return bookService.getBookByAuthor(name)
                .thenApply(books -> ResponseEntity.ok().body(books))
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    @Async
    @PostMapping
    public CompletableFuture<ResponseEntity<Book>> createBook(@RequestBody Book book) {
        return bookService.addBook(book)
                .thenApply(savedBook -> ResponseEntity.status(HttpStatus.CREATED).body(savedBook))
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    @Async
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> updateBook(@PathVariable Long id, @RequestBody Book book) {
        return bookService.updateBook(id, book)
                .thenApply(updateBook -> {
                    if (updateBook == null) {
                        return ResponseEntity.notFound().build();
                    }
                    return ResponseEntity.ok().body(updateBook);
                })
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
    @Async
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteBook(@PathVariable Long id) {
        return bookService.deleteBook(id)
                .thenApply(r -> ResponseEntity.noContent().<Void>build())
                .exceptionally(ex -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
