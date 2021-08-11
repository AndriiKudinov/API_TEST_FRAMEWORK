package consts;

public enum EndPoints {
    GET_ALL_AUTHORS("/api/library/authors"),
    POST_OR_PUT_NEW_AUTHOR("/api/library/author"),
    GET_OR_DELETE_AUTHOR_BY_ID_REST("/api/library/author/{authorId}"),
    GET_OR_DELETE_AUTHOR_BY_ID_FORMAT("/api/library/author/%d");

    private final String path;
    EndPoints(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
