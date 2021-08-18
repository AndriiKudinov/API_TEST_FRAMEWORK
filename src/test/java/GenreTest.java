import entity.Genre;
import io.qameta.allure.Attachment;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import response.BaseResponse;
import service.GenreService;

import java.util.ArrayList;
import java.util.List;

public class GenreTest {

    private GenreService genreService;
    private final List<Genre> list = new ArrayList<>();

    @AfterMethod
    public void afterMethod() {
        list.forEach(genre -> genreService.deleteGenre(genre.getGenreId()));
    }

    @BeforeClass
    public void beforeMethod() {
        genreService = new GenreService();
    }

    @Test(description = "Verify valid genre is creatable")
    public void verifyValidGenreIsCreated() {
        Genre genre = genreService.createDefaultGenre();
        BaseResponse<Genre> response = genreService.createGenre(genre);
        list.add(genre);
        saveResponse(response);
        genreService.verifyStatusCode(response, 201);
        genreService.verifyGenresAreTheSame(genre, response);
        //genreService.deleteGenre(genre.getGenreId());
    }

    @Test(description = "Verify response Genre has valid id")
    public void verifyGenreReceivedWithTheSameId() {
        Genre genre = genreService.createDefaultGenre();
        BaseResponse<Genre> createResponse = genreService.createGenre(genre);
        list.add(genre);
        genreService.verifyStatusCode(createResponse, 201);
        BaseResponse<Genre> response = genreService.getGenre(genre.getGenreId());
        saveResponse(response);
        genreService.verifyStatusCode(response, 200);
        genreService.verifyGenresHaveTheSameId(genre, response);
        //genreService.deleteGenre(genre.getGenreId());
    }

    @Test(description = "Verify genre is deletable")
    public void verifyGenreIsDeleted() {
        Genre genre = genreService.createDefaultGenre();
        BaseResponse<Genre> createResponse = genreService.createGenre(genre);
        list.add(genre);
        genreService.verifyStatusCode(createResponse, 201);
        BaseResponse<Genre> response = genreService.deleteGenre(genre.getGenreId());
        saveResponse(response);
        genreService.verifyStatusCode(response, 204);
    }

    @Test(description = "Verify genre with invalid ID is not updatable")
    public void verifyGenreWithInvalidIdIsNotUpdatable() {
        Genre genre = genreService.createDefaultGenre();
        BaseResponse<Genre> response = genreService.updateGenre(genre);
        saveResponse(response);
        genreService.verifyStatusCode(response, 404);
    }

    @Test(description = "Verify searching genre by genre name returns genres with proper genre name")
    public void verifyGenreIsSearchableByGenreName() {
        Genre genre = genreService.createDefaultGenre();
        BaseResponse<Genre> createResponse = genreService.createGenre(genre);
        list.add(genre);
        genreService.verifyStatusCode(createResponse, 201);
        BaseResponse<Genre> response = genreService.searchByGenreName(genre.getGenreName());
        saveResponse(response);
        genreService.verifyStatusCode(response, 200);
        genreService.verifyGenresHaveGenreName(response.getList(), genre.getGenreName());
       // genreService.deleteGenre(genre.getGenreId());
    }

    @Attachment
    public String saveResponse(BaseResponse<Genre> response) {
        return response.toString();
    }
}
