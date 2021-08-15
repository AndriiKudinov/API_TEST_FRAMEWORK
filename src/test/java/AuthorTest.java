import entity.Author;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import response.BaseResponse;
import service.AuthorService;

public class AuthorTest {
    private AuthorService authorService;

    @Test(description = "Verify getting Author by Id gives you right Author")
    public void verifyAuthorsAreGivenWithStatusCode200() {
        int authorId = authorService.getAuthorId();
        BaseResponse<Author> response = authorService.getAuthorResponse(authorId);
        authorService.verifyAuthorIsReceivedWithTheSameAuthorsId(authorId, response);
    }

    @BeforeClass
    public void beforeMethod() {
        authorService = new AuthorService();
    }

    @Test(description = "Verify valid author is creatable")
    public void verifyValidAuthorIsCreated() {

        Author author = authorService.createDefaultAuthor();
        BaseResponse<Author> response = authorService.createAuthor(author);
        authorService.verifyAuthorCreatedSuccessfully(author, response);
        authorService.deleteAuthor(author.getAuthorId());
    }

    @Test(description = "Verify author with invalid ID is not updatable")
    public void verifyAuthorWithInvalidIdIsNotUpdatable() {
        authorService = new AuthorService();
        Author author = authorService.createDefaultAuthor();
        BaseResponse<Author> response = authorService.updateAuthor(author);
        authorService.verifyInvalidAuthorIsNotUpdated(response);
    }

    @Test(description = "Verify default author is deletable")
    public void verifyDefaultAuthorIsDeleted() {
        authorService = new AuthorService();
        Author author = authorService.createDefaultAuthor();
        authorService.createAuthor(author);
        BaseResponse<Author> response = authorService.deleteAuthor(author.getAuthorId());
        authorService.verifyAuthorIsDeleted(response);
    }

}
