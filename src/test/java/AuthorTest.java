import entity.Author;
import io.qameta.allure.Attachment;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import response.BaseResponse;
import service.AuthorService;

public class AuthorTest {
    private AuthorService authorService;

    @BeforeClass
    public void beforeMethod() {
        authorService = new AuthorService();
    }

    @Test(description = "Verify getting Author by Id gives you right Author")
    public void verifyAuthorsAreGivenWithStatusCode200() {
        int authorId = authorService.getAuthorId();
        BaseResponse<Author> response = authorService.getAuthor(authorId);
        saveResponse(response);
        authorService.verifyStatusCode(response,200);
        authorService.verifyAuthorIsReceivedWithTheSameAuthorsId(authorId, response);
    }

    @Test(description = "Verify valid author is creatable")
    public void verifyValidAuthorIsCreated() {
        Author author = authorService.createDefaultAuthor();
        BaseResponse<Author> response = authorService.createAuthor(author);
        saveResponse(response);
        authorService.verifyStatusCode(response, 201);
        authorService.verifyAuthorCreatedSuccessfully(author, response);
        authorService.deleteAuthor(author.getAuthorId());
    }

    @Test(description = "Verify author with invalid ID is not updatable")
    public void verifyAuthorWithInvalidIdIsNotUpdatable() {
        Author author = authorService.createDefaultAuthor();
        BaseResponse<Author> response = authorService.updateAuthor(author);
        saveResponse(response);
        authorService.verifyInvalidAuthorIsNotUpdated(response);
    }

    @Test(description = "Verify default author is deletable")
    public void verifyDefaultAuthorIsDeleted() {
        Author author = authorService.createDefaultAuthor();
        authorService.createAuthor(author);
        BaseResponse<Author> response = authorService.deleteAuthor(author.getAuthorId());
        saveResponse(response);
        authorService.verifyAuthorIsDeleted(response);
    }

    @Attachment
    public String saveResponse(BaseResponse<Author> response) {
        return response.toString();
    }
}
