package dev.master.books.service;

import dev.master.books.model.Book;
import dev.master.books.repository.BookRepository;
import dev.master.books.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    @Async
    public CompletableFuture<List<Book>> getAllBooks() {
        return CompletableFuture.completedFuture(bookRepository.findAll());
    }
    @Async
    public CompletableFuture<Book> getBookById(Long id) {
        return bookRepository.findById(id)
                .map(CompletableFuture::completedFuture)
                .orElseThrow(() -> new NotFoundException("Book not found"));

    }
    @Async
    public CompletableFuture<List<Book>> getBookByAuthor(String name) {
        return CompletableFuture.completedFuture(bookRepository.findByAuthor(name));
    }
    @Async
    public CompletableFuture<Book> addBook(Book book) {
        return CompletableFuture.completedFuture(bookRepository.save(book));
    }
    @Async
    public CompletableFuture<Book> updateBook(Long id, Book updatedBook) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (!optionalBook.isPresent()) {
            throw new NotFoundException("Book not found");
        }

        Book book = optionalBook.get();
        book.setTitle(updatedBook.getTitle());
        book.setAuthor(updatedBook.getAuthor());
        book.setPublisher(updatedBook.getPublisher());
        book.setPublicationDate(updatedBook.getPublicationDate());
        book.setNumPages(updatedBook.getNumPages());
        book.setPrice(updatedBook.getPrice());
        return CompletableFuture.completedFuture(bookRepository.save(book));
    }
    @Async
    public CompletableFuture<Void> deleteBook(Long id) {
        bookRepository.deleteById(id);
        return CompletableFuture.completedFuture(null);
    }
}
