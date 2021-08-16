package entity;

import java.util.Objects;

public class Book {

    private int bookId;
    private String bookName;
    private String bookLanguage;
    private String bookDescription;
    private Additional additional;
    private int publicationYear;

    public Book() {}

    public Book(int bookId, String bookName, String bookLanguage, String bookDescription,
                int pageCount, float height, float width, float length, int publicationYear) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookLanguage = bookLanguage;
        this.bookDescription = bookDescription;
        additional = new Additional(pageCount, height, width, length);
        this.publicationYear = publicationYear;
    }

    public static Book getDefaultBook(int bookId) {
        return new Book(bookId, "TestBookName", "Russian", "TestBookDescription"
                , 15, 1, 2, 3, 2020);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return bookId == book.bookId && publicationYear == book.publicationYear
                && Objects.equals(bookName, book.bookName) && Objects.equals(bookLanguage, book.bookLanguage)
                && Objects.equals(bookDescription, book.bookDescription) && additional.equals(book.additional);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId, bookName, bookLanguage, bookDescription, additional, publicationYear);
    }

    public class Additional {
        private int pageCount;
        private Size size;

        public Additional() {}

        public Additional(int pageCount, float height, float width, float length) {
            this.pageCount = pageCount;
            size = new Size(width, height, length);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Additional that = (Additional) o;
            return pageCount == that.pageCount && size.equals(that.size);
        }

        @Override
        public int hashCode() {
            return Objects.hash(pageCount, size);
        }

        public int getPageCount() {
            return pageCount;
        }

        public void setPageCount(int pageCount) {
            this.pageCount = pageCount;
        }

        public Size getSize() {
            return size;
        }

        public void setSize(Size size) {
            this.size = size;
        }

        public class Size {
            private double height;
            private double width;
            private double length;

            public Size() {}

            public Size(float width, float height, float length) {
                this.width = width;
                this.height = height;
                this.length = length;
            }
            public double getWidth() {
                return width;
            }

            public void setWidth(float width) {
                this.width = width;
            }

            public double getHeight() {
                return height;
            }

            public void setHeight(float height) {
                this.height = height;
            }

            public double getLength() {
                return length;
            }

            public void setLength(float length) {
                this.length = length;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) return true;
                if (o == null || getClass() != o.getClass()) return false;
                Size size = (Size) o;
                return Double.compare(size.width, width) == 0 && Double.compare(size.height, height) == 0 && Double.compare(size.length, length) == 0;
            }

            @Override
            public int hashCode() {
                return Objects.hash(width, height, length);
            }
        }

    }

}
