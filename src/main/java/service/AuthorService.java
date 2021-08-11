package service;

import client.HttpClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import config.ServiceConfig;
import consts.EndPoints;
import entity.Author;
import io.restassured.RestAssured;
import org.apache.log4j.Logger;
import static org.hamcrest.Matchers.*;

import static io.restassured.RestAssured.*;

import java.util.List;

public class AuthorService {
    private static final Logger LOG = Logger.getLogger(AuthorService.class);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private int defaultAuthorId;

    static {
        RestAssured.baseURI = ServiceConfig.HOST;
    }

    public List<Author> getAllAuthors() {
        return get(EndPoints.GET_ALL_AUTHORS.getPath()).jsonPath().getList("$", Author.class);
    }

    public Author getAuthor(int authorId) {
        return get(String.format(EndPoints.GET_OR_DELETE_AUTHOR_BY_ID_FORMAT.getPath(), authorId))
                .jsonPath()
                .getObject("$", Author.class);
    }

    public int getMaxUsedId(List<Author> list) {
        return list.stream().mapToInt(Author::getAuthorId).max().orElse(0);
    }

    public int getUnselectedAuthorId(List<Author> list) {
        int biggestId = getMaxUsedId(list);
        biggestId++;
        return biggestId;
    }

    public AuthorService postDefaultAuthor() {
        defaultAuthorId = getUnselectedAuthorId(getAllAuthors());
        Author author = Author.getDefaultAuthor(defaultAuthorId);
        String authorJson = gson.toJson(author, Author.class);
        HttpClient.post(EndPoints.POST_OR_PUT_NEW_AUTHOR.getPath(), authorJson);
        return this;
    }

    public AuthorService verifyGetAuthorsWithStatusCode(int statusCode) {
        get(EndPoints.GET_ALL_AUTHORS.getPath())
                .then()
                .statusCode(statusCode);
        return this;
    }

    public AuthorService verifyDefaultAuthorIsPosted() {
        given()
                .pathParam("authorId", defaultAuthorId)
                .get(EndPoints.GET_OR_DELETE_AUTHOR_BY_ID_REST.getPath())
                .then()
                .body("authorName.first", equalTo("Andr"));
        return this;
    }

    public AuthorService deleteDefaultAuthor() {
        if(defaultAuthorId != 0) {
            HttpClient.delete(String.format(EndPoints.GET_OR_DELETE_AUTHOR_BY_ID_FORMAT.getPath(), defaultAuthorId));
        }
        return this;
    }

    public AuthorService verifyDefaultAuthorIsDeleted() {
        if(defaultAuthorId != 0) {
            given()
                    .pathParam("authorId", defaultAuthorId)
                    .get(EndPoints.GET_OR_DELETE_AUTHOR_BY_ID_REST.getPath())
                    .then()
                    .statusCode(404);
        } else {
            LOG.info("Default author has not been created previously");
        }
        return this;
    }

    public AuthorService verifyInvalidDefaultAuthorIsNotUpdatable() {
        Author author = Author.getDefaultAuthor(getUnselectedAuthorId(getAllAuthors()));
        String authorBody = gson.toJson(author, Author.class);
        given()
                .header("Content-type", "application/json;charset=UTF-8 ")
                .and()
                .body(authorBody)
                .when()
                .put("/api/library/author")
                .then()
                .statusCode(404);
        return this;
    }
}
