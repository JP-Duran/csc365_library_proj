package query_classes;

public class book {
    private String book_name;
    private String isbn;
    private String genre;
    private int holds = 0;

    public book(String bn, String isbn, String genre, int holds) {
        this.book_name = bn;
        this.isbn = isbn;
        this.genre = genre;
        this.holds = holds;
    }

    public String get_book_name() {
        return this.book_name;
    }

    public String get_isbn() {
        return this.isbn;
    }

    public String get_genre() {
        return this.genre;
    }

    public int get_holds() {
        return this.holds;
    }
}