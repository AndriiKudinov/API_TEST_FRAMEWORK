package service;

import client.HttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import consts.OrderTypes;
import entity.Author;
import entity.Book;
import entity.Genre;
import entity.ListOptions;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import org.testng.Assert;
import response.BaseResponse;
import utils.EndpointBuilder;
import java.util.List;

public class BookService {
    private static final Logger LOG = Logger.getLogger(BookService.class);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Step("Get book by bookId - {bookId}")
    public BaseResponse<Book> getBook(int bookId) {
        String endpoint = new EndpointBuilder().pathParameter("book").pathParameter(bookId).get();
        return new BaseResponse<>(HttpClient.get(endpoint), Book.class);
    }

    @Step("Get Books")
    public BaseResponse<Book> getBooks(ListOptions options) {
        EndpointBuilder endpoint = new EndpointBuilder().pathParameter("books");
        if (options.orderType != null) endpoint.queryParam("orderType", options.orderType.getType());
        endpoint
                .queryParam("page", options.page)
                .queryParam("pagination", options.pagination)
                .queryParam("size", options.size);
        if (options.sortBy != null) endpoint.queryParam("sortBy", options.sortBy);
        return new BaseResponse<>(HttpClient.get(endpoint.get()), Book.class);
    }

    @Step("Create Book {book.bookId}")
    public BaseResponse<Book> createBook(Book book, int authorId, int genreId) {
        String bookJson = gson.toJson(book, Book.class);
        String endPoint = new EndpointBuilder().pathParameter("book").pathParameter(authorId)
                .pathParameter(genreId).get();
        return new BaseResponse<>(HttpClient.post(endPoint, bookJson), Book.class);
    }

    @Step("Get value of the biggest bookId")
    public int getMaxUsedId() {
        Book book = getBooks(new ListOptions().setSize(1).setOrderType(OrderTypes.DESC)).getList().get(0);
        if(book!=null) {
            return book.getBookId();
        }
        return 1;
    }

    @Step("Get unselected bookId from List")
    public int getUnselectedBookId() {
        int biggestId = getMaxUsedId();
        biggestId++;
        return biggestId;
    }

    @Step("Create default test Book object")
    public Book createDefaultBook() {
        int defaultBookId = getUnselectedBookId();
        return Book.getDefaultBook(defaultBookId);
    }

    @Step("Verify created book and response book are the same")
    public void verifyBooksAreTheSame(Book book, BaseResponse<Book> response) {
        Assert.assertEquals(response.getBody(), book,
                "Books are not the same");
    }

    @Step("Verify Status Code {statusCode}")
    public void verifyStatusCode(BaseResponse<Book> response, int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode,
                String.format("Expected status code: %d; Actual: %d; Message:%s", statusCode, response.getStatusCode(), response.getHeader("errorMessage")));
    }

    @Step("Verify created book and book from response have the same Id")
    public void verifyBooksHaveTheSameId(Book book, BaseResponse<Book> response) {
        Assert.assertEquals(book.getBookId(), response.getBody().getBookId(),
                "Books have different Ids");
    }

    @Step("Delete book with bookId - {bookId}")
    public BaseResponse<Book> deleteBook(int bookId) {
        String endPoint = new EndpointBuilder().pathParameter("book").pathParameter(bookId).get();
        return new BaseResponse<>(HttpClient.delete(endPoint), Book.class);
    }

    @Step("Update book {book.bookId}")
    public BaseResponse<Book> updateBook(Book book) {
        String bookJson = gson.toJson(book, Book.class);
        String endPoint = new EndpointBuilder().pathParameter("book").get();
        return new BaseResponse<>(HttpClient.put(endPoint, bookJson), Book.class);
    }

    @Step("Verify invalid book is not updated")
    public void verifyBookIsNotUpdated(BaseResponse<Book> response) {
        Assert.assertNotEquals(response.getStatusCode(), 200, "Book with invalid id is updated");
    }
}
