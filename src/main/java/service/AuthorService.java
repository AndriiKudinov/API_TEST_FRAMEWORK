package service;

import client.HttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

    @Step("Get author response")
    public BaseResponse<Author> getAuthorResponse(int authorId) {
        String endPoint = new EndpointBuilder().pathParameter("author").pathParameter(authorId).get();
        return new BaseResponse<>(HttpClient.get(endPoint), Author.class);
    }

    @Step("Get authorId")
    public int getAuthorId() {
        return getAuthors(new ListOptions().setSize(1)).get(0).getAuthorId();
    }

    public boolean isAuthorReceivedWithTheSameAuthorsId(int authorId, BaseResponse<Author> response) {
        if(response == null) {
            LOG.info("Response is empty");
            return false;
        }
        if(response.getStatusCode()==200 && response.getBody().getAuthorId() == authorId) {
            LOG.info("Returned status: 200 Special Author object in JSON");
            return true;
        } else {
            LOG.info(String.format("Returned status: %d", response.getStatusCode()));
            return false;
        }
    }

    @Step("Verify author received with proper author id")
    public void verifyAuthorIsReceivedWithTheSameAuthorsId(int authorId, BaseResponse<Author> response) {
        Assert.assertTrue(isAuthorReceivedWithTheSameAuthorsId(authorId, response),
                "Received Author has different authorId.");
    }

    @Step("Get Maximum value of id from list of authors")
    public int getMaxUsedId(List<Author> list) {
        return list.stream().mapToInt(Author::getAuthorId).max().orElse(0);
    }

    @Step("Get unselected Author id")
    public int getUnselectedAuthorId(List<Author> list) {
        int biggestId = getMaxUsedId(list);
        biggestId++;
        return biggestId;
    }

    @Step("Create default author object")
    public Author createDefaultAuthor() {
        int defaultAuthorId = getUnselectedAuthorId(getAuthors(new ListOptions().setPagination(false)));
        return Author.getDefaultAuthor(defaultAuthorId);
    }

    @Step("Create author with id - {author.authorId}")
    public BaseResponse<Author> createAuthor(Author author) {
        String authorJson = gson.toJson(author, Author.class);
        String endPoint = new EndpointBuilder().pathParameter("author").get();
        return new BaseResponse<>(HttpClient.post(endPoint, authorJson), Author.class);
    }

    @Step("Verify post status code is valid")
    public boolean isPostStatusCodeValid(BaseResponse<Author> response) {
        if(response == null) {
            LOG.info("Response is empty");
            return false;
        }
        int statusCode = response.getStatusCode();
        if(statusCode == 201) {
            LOG.info("Returned status: 201 Author created successfully");
            return true;
        } else {
            LOG.info(String.format("Returned status code: %d Something wrong...", statusCode));
            return false;
        }
    }

    @Step("Verify author and author from response are the same")
    public boolean areAuthorsTheSame(Author author, BaseResponse<Author> response) {
        if(author == null || response == null) {
            LOG.info("Incorrect input");
            return false;
        }
        boolean areSame = false;
        if(isPostStatusCodeValid(response)) {
            if(author.equals(response.getBody())) {
                areSame = true;
            }
        }
        LOG.info(String.format("Are authors the same: %s", areSame));
        return areSame;
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

    @Step("Verify delete status code is valid")
    public boolean isDeleteStatusCodeValid(BaseResponse<Author> response) {
        if(response == null) {
            LOG.info("Response is empty");
            return false;
        }
        int statusCode = response.getStatusCode();
        if(statusCode == 204) {
            LOG.info("Returned status: 204 Author deleted successfully");
            return true;
        } else {
            LOG.info(String.format("Returned status code: %d Something wrong...", statusCode));
            return false;
        }
    }

    @Step("Verify author is deleted")
    public AuthorService verifyAuthorIsDeleted(BaseResponse<Author> response) {
        Assert.assertTrue(isDeleteStatusCodeValid(response),
                "Author is not deleted");
        return this;
    }

    @Step("Update author with id {author.authorId}")
    public BaseResponse<Author> updateAuthor(Author author) {
        String authorJson = gson.toJson(author, Author.class);
        String endPoint = new EndpointBuilder().pathParameter("author").get();
        return new BaseResponse<>(HttpClient.put(endPoint, authorJson), Author.class);
    }

    @Step("Verify Put status code is valid")
    public boolean isPutStatusCodeValid(BaseResponse<Author> response) {
        if(response == null) {
            LOG.info("Response is empty");
            return false;
        }
        int statusCode = response.getStatusCode();
        if(statusCode == 200) {
            LOG.info("Returned status: 200 updated Author object");
            return true;
        } else {
            LOG.info(String.format("Returned status code: %d Something wrong...", statusCode));
            return false;
        }
    }

    @Step("Verify author with invalid id is not updated")
    public AuthorService verifyInvalidAuthorIsNotUpdated(BaseResponse<Author> response) {
        Assert.assertFalse(isPutStatusCodeValid(response),
                "Author is updated");
        return this;
    }

}
