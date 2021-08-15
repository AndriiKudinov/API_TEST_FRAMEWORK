package entity;

import java.util.Objects;

public class Author {

    private int authorId;
    private AuthorName authorName;
    private String nationality;
    private Birth birth;
    private String authorDescription;

    public Author() {}

    public class AuthorName {
        public String first;
        public String second;

        public AuthorName() {}

        public AuthorName(String first, String second) {
            this.first = first;
            this.second = second;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            AuthorName that = (AuthorName) o;
            return Objects.equals(first, that.first) && Objects.equals(second, that.second);
        }

        @Override
        public int hashCode() {
            return Objects.hash(first, second);
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getSecond() {
            return second;
        }

        public void setSecond(String second) {
            this.second = second;
        }
    }

    public class Birth {
        public String date;
        public String country;
        public String city;

        public Birth() {}

        public Birth(String date, String country, String city) {
            this.date = date;
            this.country = country;
            this.city = city;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getCountry() {
            return country;
        }

        public void setCountry(String country) {
            this.country = country;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Birth birth = (Birth) o;
            return Objects.equals(date, birth.date) && Objects.equals(country, birth.country) && Objects.equals(city, birth.city);
        }

        @Override
        public int hashCode() {
            return Objects.hash(date, country, city);
        }
    }

    public Author(int authorId, String first, String second, String nationality,
                  String date, String country, String city, String authorDescription) {
        this.authorId = authorId;
        this.nationality = nationality;
        this.authorDescription = authorDescription;
        authorName = new AuthorName(first, second);
        birth = new Birth(date, country, city);
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

    public AuthorName getAuthorName() {
        return authorName;
    }

    public void setAuthorName(AuthorName authorName) {
        this.authorName = authorName;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public Birth getBirth() {
        return birth;
    }

    public void setBirth(Birth birth) {
        this.birth = birth;
    }

    public String getAuthorDescription() {
        return authorDescription;
    }

    public void setAuthorDescription(String authorDescription) {
        this.authorDescription = authorDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Author author = (Author) o;
        return authorId == author.authorId && authorName.equals(author.authorName)
                && Objects.equals(nationality, author.nationality) && birth.equals(author.birth)
                && Objects.equals(authorDescription, author.authorDescription);
    }

    @Override
    public int hashCode() {
        return Objects.hash(authorId, authorName, nationality, birth, authorDescription);
    }
}
