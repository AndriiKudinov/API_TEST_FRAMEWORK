import entity.Author;
import entity.Genre;
import io.qameta.allure.Attachment;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import response.BaseResponse;
import service.GenreService;

public class GenreTest {

    private GenreService genreService;

    @BeforeClass
    public void beforeMethod() {
        genreService = new GenreService();
    }

    @Test(description = "Verify valid genre is creatable")
    public void verifyValidGenreIsCreated() {
        Genre genre = genreService.createDefaultGenre();
        BaseResponse<Genre> response = genreService.createGenre(genre);
        saveResponse(response);
        genreService.verifyGenreCreatedWithStatusCode201(response);
        genreService.verifyGenresAreTheSame(genre, response);
        genreService.deleteGenre(genre.getGenreId());
    }

    @Test(description = "Verify response Genre has valid id")
    public void verifyGenreReceivedWithTheSameId() {
        Genre genre = genreService.createDefaultGenre();
        BaseResponse<Genre> createResponse = genreService.createGenre(genre);
        genreService.verifyGenreCreatedWithStatusCode201(createResponse);

        BaseResponse<Genre> response = genreService.getGenre(genre.getGenreId());
        saveResponse(response);
        genreService.verifyGotGenreWithStatusCode200(response);
        genreService.verifyGenresHaveTheSameId(genre, response);
        genreService.deleteGenre(genre.getGenreId());
    }

    @Test(description = "Verify genre is deletable")
    public void verifyGenreIsDeleted() {
        Genre genre = genreService.createDefaultGenre();
        BaseResponse<Genre> createResponse = genreService.createGenre(genre);
        genreService.verifyGenreCreatedWithStatusCode201(createResponse);
        BaseResponse<Genre> response = genreService.deleteGenre(genre.getGenreId());
        saveResponse(response);
        genreService.verifyGenreIsDeletedWithStatusCode204(response);
    }

    @Test(description = "Verify genre with invalid ID is not updatable")
    public void verifyGenreWithInvalidIdIsNotUpdatable() {
        Genre genre = genreService.createDefaultGenre();
        BaseResponse<Genre> response = genreService.updateGenre(genre);
        saveResponse(response);
        genreService.verifyGenreIsNotUpdated(response);
    }

    @Test(description = "Verify searching genre by genre name returns genres with proper genre name")
    public void verifyGenreIsSearchableByGenreName() {
        Genre genre = genreService.createDefaultGenre();
        BaseResponse<Genre> createResponse = genreService.createGenre(genre);
        genreService.verifyGenreCreatedWithStatusCode201(createResponse);
        BaseResponse<Genre> response = genreService.searchByGenreName(genre.getGenreName());
        saveResponse(response);
        genreService.verifyGenresHaveGenreName(response.getList(), genre.getGenreName());
        genreService.deleteGenre(genre.getGenreId());
    }

    @Attachment
    public String saveResponse(BaseResponse<Genre> response) {
        return response.saveResponse();
    }
}
