package service;

import client.HttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import consts.EndPoints;
import entity.Author;
import entity.ListOptions;
import org.apache.log4j.Logger;
import response.BaseResponse;
import utils.EndpointBuilder;
import org.testng.Assert;

import static io.restassured.RestAssured.*;

import java.util.List;
import java.util.Objects;

public class AuthorService {
    private static final Logger LOG = Logger.getLogger(AuthorService.class);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public List<Author> getAuthors(ListOptions options) {
        EndpointBuilder endpoint = new EndpointBuilder().pathParameter("authors");
        if (options.orderType != null) endpoint.queryParam("orderType", options.orderType.getType());
        endpoint
                .queryParam("page", options.page)
                .queryParam("pagination", options.pagination)
                .queryParam("size", options.size);
        if (options.sortBy != null) endpoint.queryParam("sortBy", options.sortBy);
        return new BaseResponse<>(HttpClient.get(endpoint.get()), Author.class).getList();
    }

    public BaseResponse<Author> getAuthor(int authorId) {
        String endPoint = new EndpointBuilder().pathParameter("author").pathParameter(authorId).get();
        return new BaseResponse<>(HttpClient.get(endPoint), Author.class);
    }

    public int getMaxUsedId(List<Author> list) {
        return list.stream().mapToInt(Author::getAuthorId).max().orElse(0);
    }

    public int getUnselectedAuthorId(List<Author> list) {
        int biggestId = getMaxUsedId(list);
        biggestId++;
        return biggestId;
    }

    public Author createDefaultAuthor() {
        int defaultAuthorId = getUnselectedAuthorId(getAuthors(new ListOptions().setPagination(false)));
        return Author.getDefaultAuthor(defaultAuthorId);
    }

    //response + validation
    public BaseResponse<Author> postAuthor(Author author) {
        String authorJson = gson.toJson(author, Author.class);
        String endPoint = new EndpointBuilder().pathParameter("author").get();
        return new BaseResponse<>(HttpClient.post(endPoint, authorJson), Author.class);
    }

    public boolean isPostStatusCodeValid(BaseResponse<Author> response) {
        if(response == null) {
            LOG.info("Response is empty");
            return false;
        }
        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case(201):
                LOG.info("Returned status: 201 Author posted successfully");
                return true;
            case(409):
                LOG.info("Returned status: 409 Author with such id already exists");
                return false;
            default:
                LOG.info("Something wrong...");
                return false;
        }
    }

    public boolean areAuthorsTheSame(Author author, BaseResponse<Author> response) {
        if(author == null || response == null) {
            LOG.info("Incorrect input");
            return false;
        }
        boolean areSame = false;
        if(isPostStatusCodeValid(response)) {
            if(Objects.equals(author.getAuthorName().get("first"), response.getBody().getAuthorName().get("first"))) {
                areSame = true;
            }
        }
        LOG.info(String.format("Are first names the same: %s", areSame));
        return areSame;
    }

    public AuthorService verifyAuthorPostedSuccessfully(Author author, BaseResponse<Author> response) {
        Assert.assertTrue(areAuthorsTheSame(author, response),
                "Author is not posted successfully");
        return this;
    }

    public AuthorService verifyGetAuthorsWithStatusCode(int statusCode) {
        get(EndPoints.GET_ALL_AUTHORS.getPath())
                .then()
                .statusCode(statusCode);
        return this;
    }

    public BaseResponse<Author> deleteAuthor(int authorId) {
        String endPoint = new EndpointBuilder().pathParameter("author").pathParameter(authorId).get();
        return new BaseResponse<>(HttpClient.delete(endPoint), Author.class);
    }

    public boolean isDeleteStatusCodeValid(BaseResponse<Author> response) {
        if(response == null) {
            LOG.info("Response is empty");
            return false;
        }
        int statusCode = response.getStatusCode();
        //
        switch (statusCode) {
            case(204):
                LOG.info("Returned status: 204 Author deleted successfully");
                return true;
            case(404):
                LOG.info("Returned status: 404 Author to delete not found");
                return false;
            default:
                LOG.info("Something wrong...");
                return false;
        }
    }

    public AuthorService verifyAuthorIsDeleted(BaseResponse<Author> response) {
        Assert.assertTrue(isDeleteStatusCodeValid(response),
                "Author is not deleted");
        return this;
    }

    public BaseResponse<Author> updateAuthor(Author author) {
        String authorJson = gson.toJson(author, Author.class);
        String endPoint = new EndpointBuilder().pathParameter("author").get();
        return new BaseResponse<>(HttpClient.put(endPoint, authorJson), Author.class);
    }

    public boolean isPutStatusCodeValid(BaseResponse<Author> response) {
        if(response == null) {
            LOG.info("Response is empty");
            return false;
        }
        int statusCode = response.getStatusCode();
        switch (statusCode) {
            case(200):
                LOG.info("Returned status: 200 updated Author object");
                return true;
            case(404):
                LOG.info("Returned status: 404 Author to update not found");
                return false;
            default:
                LOG.info("Something wrong...");
                return false;
        }
    }

    public AuthorService verifyInvalidAuthorIsNotUpdated(BaseResponse<Author> response) {
        Assert.assertFalse(isPutStatusCodeValid(response),
                "Author is updated");
        return this;
    }

}
