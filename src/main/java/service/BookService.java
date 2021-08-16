package service;

import client.HttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    @Step("Get List of Books")
    public List<Book> getBooks(ListOptions options) {
        EndpointBuilder endpoint = new EndpointBuilder().pathParameter("books");
        if (options.orderType != null) endpoint.queryParam("orderType", options.orderType.getType());
        endpoint
                .queryParam("page", options.page)
                .queryParam("pagination", options.pagination)
                .queryParam("size", options.size);
        if (options.sortBy != null) endpoint.queryParam("sortBy", options.sortBy);
        return new BaseResponse<>(HttpClient.get(endpoint.get()), Book.class).getList();
    }

    @Step("Create Book")
    public BaseResponse<Book> createBook(Book book, Author author, Genre genre) {
        String bookJson = gson.toJson(book, Book.class);
        System.out.println(bookJson);
        String endPoint = new EndpointBuilder().pathParameter("book").pathParameter(author.getAuthorId())
                .pathParameter(genre.getGenreId()).get();
        return new BaseResponse<>(HttpClient.post(endPoint, bookJson), Book.class);
    }

    @Step("Get value of the biggest bookId")
    public int getMaxUsedId(List<Book> list) {
        return list.stream().mapToInt(Book::getBookId).max().orElse(0);
    }

    @Step("Get unselected bookId from List")
    public int getUnselectedBookId(List<Book> list) {
        int biggestId = getMaxUsedId(list);
        biggestId++;
        return biggestId;
    }

    @Step("Create default test Book object")
    public Book createDefaultBook() {
        int defaultBookId = getUnselectedBookId(getBooks(new ListOptions().setPagination(false)));
        LOG.info(defaultBookId);
        return Book.getDefaultBook(defaultBookId);
    }

    @Step("Verify book is created with status code 201")
    public void verifyBookCreatedWithStatusCode201(BaseResponse<Book> response) {
        Assert.assertEquals(response.getStatusCode(), 201,
                String.format("Expected status code 201, Got - %d", response.getStatusCode()));
    }

    @Step("Verify created book and response book are the same")
    public void verifyBooksAreTheSame(Book book, BaseResponse<Book> response) {
        Assert.assertEquals(response.getBody(), book,
                "Books are not the same");
    }

    @Step("Verify book is received with status code 200")
    public void verifyGotBookWithStatusCode200(BaseResponse<Book> response) {
        Assert.assertEquals(response.getStatusCode(), 200,
                String.format("Expected status code 200, Got - %d", response.getStatusCode()));
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

    @Step("Verify book is deleted with status code 204")
    public void verifyBookIsDeletedWithStatusCode204(BaseResponse<Book> response) {
        Assert.assertEquals(response.getStatusCode(), 204,
                String.format("Expected status code 204, Got - %d", response.getStatusCode()));
    }

    @Step("Update book")
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
