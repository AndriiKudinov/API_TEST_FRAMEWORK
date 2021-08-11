package entity;

import java.util.HashMap;
import java.util.Map;

public class Author {

    private int authorId;
    private Map<String, String> authorName;
    private String nationality;
    private Map<String, String> birth;
    private String authorDescription;

    public Author(int authorId, String first, String second, String nationality,
                  String date, String country, String city, String authorDescription) {
        this.authorId = authorId;
        this.nationality = nationality;
        this.authorDescription = authorDescription;
        authorName = new HashMap<>();
        authorName.put("first", first);
        authorName.put("second", second);
        birth = new HashMap<>();
        birth.put("date", date);
        birth.put("country", country);
        birth.put("city", city);
    }

    public static Author getDefaultAuthor(int authorId) {
        return new Author(authorId, "Andr", "Kud", "Ukr", "1968-10-28" ,
                "Ukraine", "Kiev", "Test Description");
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public Map<String, String> getAuthorName() {
        return authorName;
    }

    public void setAuthorName(Map<String, String> authorName) {
        this.authorName = authorName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Map<String, String> getBirth() {
        return birth;
    }

    public void setBirth(Map<String, String> birth) {
        this.birth = birth;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }
}
