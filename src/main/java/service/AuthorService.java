package service;

import client.HttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import consts.OrderTypes;
import entity.Author;
import entity.ListOptions;
import io.qameta.allure.Step;
import org.apache.log4j.Logger;
import response.BaseResponse;
import utils.EndpointBuilder;
import org.testng.Assert;

import java.util.List;

public class AuthorService {
    private static final Logger LOG = Logger.getLogger(AuthorService.class);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Step("Get List of authors")
    public BaseResponse<Author> getAuthors(ListOptions options) {
        EndpointBuilder endpoint = new EndpointBuilder().pathParameter("authors");
        if (options.orderType != null) endpoint.queryParam("orderType", options.orderType.getType());
        endpoint
                .queryParam("page", options.page)
                .queryParam("pagination", options.pagination)
                .queryParam("size", options.size);
        if (options.sortBy != null) endpoint.queryParam("sortBy", options.sortBy);
        return new BaseResponse<>(HttpClient.get(endpoint.get()), Author.class);
    }

    @Step("Get author {authorId}")
    public BaseResponse<Author> getAuthor(int authorId) {
        String endPoint = new EndpointBuilder().pathParameter("author").pathParameter(authorId).get();
        return new BaseResponse<>(HttpClient.get(endPoint), Author.class);
    }

    @Step("Get authorId")
    public int getAuthorId() {
        return getAuthors(new ListOptions().setSize(1)).getList().get(0).getAuthorId();
    }

    @Step("Verify author received with the same authors Id")
    public boolean isAuthorReceivedWithTheSameAuthorsId(int authorId, BaseResponse<Author> response) {
        return response.getBody().getAuthorId() == authorId;
    }

    @Step("Verify Status Code {statusCode}")
    public void verifyStatusCode(BaseResponse<Author> response, int statusCode) {
        Assert.assertEquals(response.getStatusCode(), statusCode,
                String.format("Expected status code: %d; Actual: %d; Message:%s", statusCode, response.getStatusCode(), response.getHeader("errorMessage")));
    }

    @Step("Verify author received with proper author id")
    public void verifyAuthorIsReceivedWithTheSameAuthorsId(int authorId, BaseResponse<Author> response) {
        Assert.assertTrue(isAuthorReceivedWithTheSameAuthorsId(authorId, response),
                "Received Author has different authorId.");
    }

    @Step("Get Maximum value of id from list of authors")
    public int getMaxUsedId() {
        return getAuthors(new ListOptions().setSize(1).setOrderType(OrderTypes.DESC)).getList().get(0).getAuthorId();
    }

    @Step("Get unselected Author id")
    public int getUnselectedAuthorId() {
        int biggestId = getMaxUsedId();
        biggestId++;
        return biggestId;
    }

    @Step("Create default author object")
    public Author createDefaultAuthor() {
        int defaultAuthorId = getUnselectedAuthorId();
        return Author.getDefaultAuthor(defaultAuthorId);
    }

    @Step("Create author with id - {author.authorId}")
    public BaseResponse<Author> createAuthor(Author author) {
        String authorJson = gson.toJson(author, Author.class);
        String endPoint = new EndpointBuilder().pathParameter("author").get();
        return new BaseResponse<>(HttpClient.post(endPoint, authorJson), Author.class);
    }

    @Step("Verify author and author from response are the same")
    public boolean areAuthorsTheSame(Author author, BaseResponse<Author> response) {
        return author.equals(response.getBody());
    }

    @Step("Verify Author {author.authorId} is created")
    public AuthorService verifyAuthorCreatedSuccessfully(Author author, BaseResponse<Author> response) {
        Assert.assertTrue(areAuthorsTheSame(author, response),
                "Author is not posted successfully");
        return this;
    }

    @Step("Delete author with author id - {authorId}")
    public BaseResponse<Author> deleteAuthor(int authorId) {
        String endPoint = new EndpointBuilder().pathParameter("author").pathParameter(authorId).get();
        return new BaseResponse<>(HttpClient.delete(endPoint), Author.class);
    }


    @Step("Verify author is deleted")
    public AuthorService verifyAuthorIsDeleted(BaseResponse<Author> response) {
        Assert.assertEquals(response.getStatusCode(), 204, "Author is not deleted");
        return this;
    }

    @Step("Update author with id {author.authorId}")
    public BaseResponse<Author> updateAuthor(Author author) {
        String authorJson = gson.toJson(author, Author.class);
        String endPoint = new EndpointBuilder().pathParameter("author").get();
        return new BaseResponse<>(HttpClient.put(endPoint, authorJson), Author.class);
    }

    @Step("Verify author with invalid id is not updated")
    public AuthorService verifyInvalidAuthorIsNotUpdated(BaseResponse<Author> response) {
        Assert.assertNotEquals(response.getStatusCode(), 200, "Author is updated");
        return this;
    }

}
