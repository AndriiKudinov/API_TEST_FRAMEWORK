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
import java.util.Objects;

public class GenreService {
    private static final Logger LOG = Logger.getLogger(GenreService.class);
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Step("Get genre by genreId - {genreId}")
    public BaseResponse<Genre> getGenre(int genreId) {
        String endpoint = new EndpointBuilder().pathParameter("genre").pathParameter(genreId).get();
        return new BaseResponse<>(HttpClient.get(endpoint), Genre.class);
    }

    @Step("Get List of Genres")
    public List<Genre> getGenres(ListOptions options) {
        EndpointBuilder endpoint = new EndpointBuilder().pathParameter("genres");
        if (options.orderType != null) endpoint.queryParam("orderType", options.orderType.getType());
        endpoint
                .queryParam("page", options.page)
                .queryParam("pagination", options.pagination)
                .queryParam("size", options.size);
        if (options.sortBy != null) endpoint.queryParam("sortBy", options.sortBy);
        return new BaseResponse<>(HttpClient.get(endpoint.get()), Genre.class).getList();
    }

    @Step("Create Genre")
    public BaseResponse<Genre> createGenre(Genre genre) {
        String genreJson = gson.toJson(genre, Genre.class);
        String endPoint = new EndpointBuilder().pathParameter("genre").get();
        return new BaseResponse<>(HttpClient.post(endPoint, genreJson), Genre.class);
    }

    @Step("Get value of the biggest genreId")
    public int getMaxUsedId(List<Genre> list) {
        return list.stream().mapToInt(Genre::getGenreId).max().orElse(0);
    }

    @Step("Get unselected genreId from List")
    public int getUnselectedGenreId(List<Genre> list) {
        int biggestId = getMaxUsedId(list);
        biggestId++;
        return biggestId;
    }

    @Step("Create default test Genre object")
    public Genre createDefaultGenre() {
        int defaultGenreId = getUnselectedGenreId(getGenres(new ListOptions().setPagination(false)));
        return Genre.getDefaultGenre(defaultGenreId);
    }

    @Step("Verify genre is created with status code 201")
    public void verifyGenreCreatedWithStatusCode201(BaseResponse<Genre> response) {
        Assert.assertEquals(response.getStatusCode(), 201,
                String.format("Expected status code 201, Got - %d", response.getStatusCode()));
    }

    @Step("Verify created genre and response genre are the same")
    public void verifyGenresAreTheSame(Genre genre, BaseResponse<Genre> response) {
        Assert.assertEquals(response.getBody(), genre,
                "Genres are not the same");
    }

    @Step("Verify genre/genres is/are received with status code 200")
    public void verifyGotGenreWithStatusCode200(BaseResponse<Genre> response) {
        Assert.assertEquals(response.getStatusCode(), 200,
                String.format("Expected status code 200, Got - %d", response.getStatusCode()));
    }

    @Step("Verify created genre and genre from response have the same Id")
    public void verifyGenresHaveTheSameId(Genre genre, BaseResponse<Genre> response) {
        Assert.assertEquals(genre.getGenreId(), response.getBody().getGenreId(),
                "Genres have different Ids");
    }

    @Step("Delete genre with genreId - {genreId}")
    public BaseResponse<Genre> deleteGenre(int genreId) {
        String endPoint = new EndpointBuilder().pathParameter("genre").pathParameter(genreId).get();
        return new BaseResponse<>(HttpClient.delete(endPoint), Genre.class);
    }

    @Step("Verify genre is deleted with status code 204")
    public void verifyGenreIsDeletedWithStatusCode204(BaseResponse<Genre> response) {
        Assert.assertEquals(response.getStatusCode(), 204,
                String.format("Expected status code 204, Got - %d", response.getStatusCode()));
    }

    @Step("Update genre")
    public BaseResponse<Genre> updateGenre(Genre genre) {
        String genreJson = gson.toJson(genre, Genre.class);
        String endPoint = new EndpointBuilder().pathParameter("genre").get();
        return new BaseResponse<>(HttpClient.put(endPoint, genreJson), Genre.class);
    }

    @Step("Verify invalid genre is not updated")
    public void verifyGenreIsNotUpdated(BaseResponse<Genre> response) {
        Assert.assertNotEquals(response.getStatusCode(), 200, "Genre with invalid id is updated");
    }

    @Step("Search genres by name - {name}")
    public BaseResponse<Genre> searchByGenreName(String name) {
        EndpointBuilder endpoint = new EndpointBuilder().pathParameter("genres").pathParameter("search");
        endpoint.queryParam("query", name);
        return new BaseResponse<>(HttpClient.get(endpoint.get()), Genre.class);
    }

    @Step("Verify genres from list have proper genre name")
    public void verifyGenresHaveGenreName(List<Genre> list, String name) {
        Assert.assertTrue(list.stream().allMatch(genre -> Objects.equals(genre.getGenreName(), name)));
    }
}