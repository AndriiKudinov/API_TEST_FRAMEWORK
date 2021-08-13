import entity.Author;
import org.testng.annotations.Test;
import response.BaseResponse;
import service.AuthorService;

public class AuthorTest {

//    @Test(description = "Verify Authors are given with status code 200")
//    public void verifyAuthorsAreGivenWithStatusCode200() {
//        new AuthorService()
//                .verifyGetAuthorsWithStatusCode(200);
//        //size, ListOptions
//    }
//
    @Test(description = "Verify valid author is postable")
    public void verifyValidAuthorIsPosted() {
        AuthorService authorService = new AuthorService();
        Author author = authorService.createDefaultAuthor();
        BaseResponse<Author> response = authorService.postAuthor(author);
        authorService.verifyAuthorPostedSuccessfully(author, response);
        authorService.deleteAuthor(author.getAuthorId());
    }

    @Test(description = "Verify author with invalid ID is not updatable")
    public void verifyAuthorWithInvalidIdIsNotUpdatable() {
        //before class service
        AuthorService authorService = new AuthorService();
        Author author = authorService.createDefaultAuthor();
        BaseResponse<Author> response = authorService.updateAuthor(author);
        authorService.verifyInvalidAuthorIsNotUpdated(response);
    }

    @Test(description = "Verify default author is deletable")
    public void verifyDefaultAuthorIsDeleted() {
        AuthorService authorService = new AuthorService();
        Author author = authorService.createDefaultAuthor();
        authorService.postAuthor(author);
        BaseResponse<Author> response = authorService.deleteAuthor(author.getAuthorId());
        authorService.verifyAuthorIsDeleted(response);
    }

}
