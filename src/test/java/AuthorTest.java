import org.testng.annotations.Test;
import service.AuthorService;

public class AuthorTest {

    @Test(description = "Verify Authors are given with status code 200")
    public void verifyAuthorsAreGivenWithStatusCode200() {
        new AuthorService()
                .verifyGetAuthorsWithStatusCode(200);
    }

    @Test(description = "Verify valid author is postable")
    public void verifyValidAuthorIsPosted() {
        new AuthorService()
                .postDefaultAuthor()
                .verifyDefaultAuthorIsPosted()
                .deleteDefaultAuthor();
    }

    @Test(description = "Verify author with invalid ID is not updatable")
    public void verifyAuthorWithInvalidIdIsNotUpdatable() {
        new AuthorService()
                .verifyInvalidDefaultAuthorIsNotUpdatable();
    }

    @Test(description = "Verify default author is deletable")
    public void verifyDefaultAuthorIsDeleted() {
        new AuthorService()
                .postDefaultAuthor()
                .deleteDefaultAuthor()
                .verifyDefaultAuthorIsDeleted();
    }

}
