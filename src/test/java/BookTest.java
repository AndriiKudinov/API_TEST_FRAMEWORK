import client.HttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import config.ServiceConfig;
import entity.Author;
import entity.Book;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import response.BaseResponse;

import static io.restassured.RestAssured.*;

import java.util.List;

import static io.restassured.RestAssured.get;

public class BookTest {
    public static void main(String[] args) {
//        BaseResponse response = HttpClient.get("/api/library/books");
//        String responseBody = response.getBody();
//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        Book[] bookList = gson.fromJson(responseBody, Book[].class);
//        String jsonInList = gson.toJson(bookList);
//        System.out.println(jsonInList);

        verify();

//        Book[] bookList= gson.fromJson(books, Book[].class);
//        for(Book book : bookList) {
//            System.out.println(book.getAdditional().getLength());
//        }
       // Response response = get("/api/library/books");
//
//        List<Author> as = gson.fromJson(response.jsonPath(), Author.class);
//        List<Book> books = response.getBody().jsonPath().getList("$", Book.class);
//        books.forEach(x -> System.out.println(x.getAdditional().getHeight()));
    }

    public static void verify() {

//        BaseResponse responseAuthors = HttpClient.get("/api/library/authors");
//        String responseAuthorsBody = responseAuthors.getBody();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
//        Author[] authorsList = gson.fromJson(responseAuthorsBody, Author[].class);
//        int tempAuthorId = 0;
//        for(Author author : authorsList) {
//            if (author.getAuthorId() > tempAuthorId ) tempAuthorId = author.getAuthorId();
//        }
//        tempAuthorId++;
//        Author author = new Author(1566, "Andr", "Kud", "Ukr", "1968-10-28" ,
//                "Ukraine", "Kiev", "Test Description");
//        RestAssured.baseURI = ServiceConfig.HOST;
//        String authorBody = gson.toJson(author, Author.class);
//        Response response = given()
//                .header("Content-type", "application/json;charset=UTF-8 ")
//                .and()
//                .body(authorBody)
//                .when()
//                .post("/api/library/author")
//                .then()
//                .extract().response();
//        System.out.println(response.getStatusCode());
//        BaseResponse response5 = HttpClient.post("/api/library/author", authorBody);
//        System.out.println(response5.getStatusCode());
        String end = String.format("/api/library/author/%d", 1566);
        Response response2 = get(end);
        Author author1= response2.jsonPath().getObject("", Author.class);
        String s = gson.toJson(author1);
        System.out.println(s);
//        BaseResponse responseB = HttpClient.get("/api/library/books");
//        String responseBody = responseB.getBody();
//        Book[] bookList = gson.fromJson(responseBody, Book[].class);
//        int tempId = 0;
//        for(Book book : bookList) {
//            if (book.getBookId() > tempId ) tempId = book.getBookId();
//        }
//        tempId++;
//        Book book = new Book(tempId, "TestBook1", "russian", "TestDescription",
//                99, 1.0f, 2.0f, 3.0f, 2021);
//        String endPoint = String.format("/api/library/book/%d/105", tempAuthorId);
//        String body = gson.toJson(book, Book.class);
//        HttpClient.post(endPoint, body);
//        BaseResponse response1 = HttpClient.get(String.format("/api/library/book/%d", tempId));
//        JsonElement niceJson = gson.fromJson(response1.getBody(), JsonElement.class);
//        String niceString = gson.toJson(niceJson);
//        System.out.println(niceString);
//        HttpClient.delete(String.format("/api/library/book/%d", tempId));
//        HttpClient.delete(String.format("/api/library/author/%d", tempAuthorId));
    }

    public static void beforeMethod() {

    }
}
