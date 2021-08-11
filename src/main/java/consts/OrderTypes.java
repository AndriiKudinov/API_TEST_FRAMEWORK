package consts;

public enum OrderTypes {
    ASC("asc"), DESC("desc");

    private String type;
    OrderTypes(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
