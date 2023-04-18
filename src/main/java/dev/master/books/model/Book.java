package dev.master.books.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name="books_spring")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "title", length = 50 ,nullable = false, unique = true)
    private String title;
    @Column(name = "author", length = 50, nullable = false)
    private String author;
    @Column(name = "publisher", length = 50, nullable = false)
    private String publisher;
    @Column(name = "publication_date", nullable = false)
    private LocalDate publicationDate;
    @Column(name = "num_pages", nullable = false)
    private int numPages;
    @Column(name = "price", nullable = false)
    private BigDecimal price;

    /*public Book(String naslov, String autor, String izdavac, LocalDate datumIzdavanja, int brojStranica, int cena) {
        this.naslov = naslov;
        this.autor = autor;
        this.izdavac = izdavac;
        this.datumIzdavanja = datumIzdavanja;
        this.brojStranica = brojStranica;
        this.cena = cena;
    }*///samo ako koristimo konstruktor za inicijalizaciju objekta klase Book
}
