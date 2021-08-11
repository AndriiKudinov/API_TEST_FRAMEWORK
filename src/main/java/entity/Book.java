package entity;

import java.util.HashMap;
import java.util.Map;

public class Book {

    private int bookId;
    private String bookName;
    private String bookLanguage;
    private String bookDescription;
    private Additional additional;
    private int publicationYear;

    public Book(int bookId, String bookName, String bookLanguage, String bookDescription,
                int pageCount, float height, float width, float length, int publicationYear) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookLanguage = bookLanguage;
        this.bookDescription = bookDescription;
        additional = new Additional(pageCount, height, width, length);
        this.publicationYear = publicationYear;
    }

    public class Additional {
        private int pageCount;
        private Map<String, Float> size;

        public float getHeight() {
           return size.get("height");
        }

        public Additional(int pageCount, float height, float width, float length) {
            this.pageCount = pageCount;
            size = new HashMap<>();
            size.put("width", width);
            size.put("height", height);
            size.put("length", length);
        }

        public float getWidth() {
            return size.get("width");
        }

        public float getLength() {
            return size.get("length");
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public Map<String, Float> getSize() {
            return size;
        }

        public void setSize(Map<String, Float> size) {
            this.size = size;
        }
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookLanguage() {
        return bookLanguage;
    }

    public void setBookLanguage(String bookLanguage) {
        this.bookLanguage = bookLanguage;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }

    public Additional getAdditional() {
        return additional;
    }

    public void setAdditional(Additional additional) {
        this.additional = additional;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}
