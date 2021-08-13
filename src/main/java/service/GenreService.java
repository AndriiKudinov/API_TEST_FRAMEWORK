package service;

import client.HttpClient;
import entity.ListOptions;
import response.BaseResponse;
import utils.EndpointBuilder;

public class GenreService {

    public BaseResponse<Object> getGenre(int genreId) {
        String endpoint = new EndpointBuilder().pathParameter("genre").pathParameter(genreId).get();
        return new BaseResponse<>(HttpClient.get(endpoint), Object.class);
    }

    public BaseResponse<Object> getGenres(ListOptions options) {
        EndpointBuilder endpoint = new EndpointBuilder().pathParameter("genres");
        if (options.orderType != null) endpoint.queryParam("orderType", options.orderType.getType());
        endpoint
                .queryParam("page", options.page)
                .queryParam("pagination", options.pagination)
                .queryParam("size", options.size);
        if (options.sortBy != null) endpoint.queryParam("sortBy", options.sortBy);
        return new BaseResponse<>(HttpClient.get(endpoint.get()), Object.class);
    }

    // TODO properly handle genre entity
    public BaseResponse<Object> createGenre(Object genre) {
        String endpoint = new EndpointBuilder().pathParameter("genre").get();
        return new BaseResponse<>(HttpClient.post(endpoint, genre.toString()), Object.class);
    }
}