package response;

import io.restassured.response.Response;

import java.util.List;

public class BaseResponse<T> {
    protected Response response;
    private Class<T> responseClass;

    public BaseResponse(Response response, Class<T> responseClass) {
        this.response = response;
        this.responseClass = responseClass;
    }

    public int getStatusCode() {
        return this.response.getStatusCode();
    }

    public String getHeader(String header) {
        return this.response.getHeader(header);
    }

    public T getBody() {
        return this.response.body().as(this.responseClass);
    }

    public List<T> getList() {
       return this.response.jsonPath().getList("$", this.responseClass);
    }

    @Override
    public String toString() {
        String saveString = response.headers().toString();
        if(response.getBody() != null) {
            saveString = saveString + "\r\n" + response.asPrettyString();
        }
        return saveString;
    }
}

