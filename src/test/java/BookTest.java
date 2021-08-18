import entity.Book;
import io.qameta.allure.Attachment;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import response.BaseResponse;
import service.AuthorService;
import service.BookService;
import service.GenreService;

public class BookTest {
    private BookService bookService;
    private AuthorService authorService;
    private GenreService genreService;

    @BeforeClass
    public void beforeMethod() {
        bookService = new BookService();
        authorService = new AuthorService();
        genreService = new GenreService();
    }

    @Test(description = "Verify valid book is creatable")
    public void verifyValidBookIsCreated() {
        int authorId = authorService.getAuthorId();
        int genreId = genreService.getFirstGenreId();
        Book book = bookService.createDefaultBook();

        BaseResponse<Book> response = bookService.createBook(book, authorId, genreId);
        bookService.verifyStatusCode(response, 201);
        bookService.deleteBook(book.getBookId());
    }

    @Test(description = "Verify response Book has valid id")
    public void verifyBookReceivedWithTheSameId() {
        int authorId = authorService.getAuthorId();
        int genreId = genreService.getFirstGenreId();
        Book book = bookService.createDefaultBook();
        BaseResponse<Book> createResponse = bookService.createBook(book, authorId, genreId);
        bookService.verifyStatusCode(createResponse, 201);

        BaseResponse<Book> response = bookService.getBook(book.getBookId());
        bookService.verifyStatusCode(response, 200);
        bookService.verifyBooksAreTheSame(book, response);
        bookService.deleteBook(book.getBookId());
    }

    @Test(description = "Verify book is deletable")
    public void verifyBookIsDeleted() {
        int authorId = authorService.getAuthorId();
        int genreId = genreService.getFirstGenreId();
        Book book = bookService.createDefaultBook();
        BaseResponse<Book> createResponse = bookService.createBook(book, authorId, genreId);
        bookService.verifyStatusCode(createResponse, 201);

        BaseResponse<Book> response = bookService.deleteBook(book.getBookId());
        bookService.verifyStatusCode(response, 204);
    }

    @Test(description = "Verify book with invalid ID is not updatable")
    public void verifyBookWithInvalidIdIsNotUpdatable() {
        Book book = bookService.createDefaultBook();
        BaseResponse<Book> response = bookService.updateBook(book);
        bookService.verifyBookIsNotUpdated(response);
    }

    @Attachment
    public String saveResponse(BaseResponse<Book> response) {
        return response.toString();
    }
}