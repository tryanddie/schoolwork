package info;

public class BookInfo {
    private String Bookname;        //书名
    private String Booknumber;      //书的数量
    private String author;          //作者
    private String date;            //书的出版日期
    private String site;            //书的位置
    private String category;        //书的种类

    public String getBookname() {
        return Bookname;
    }

    public void setBookname(String bookname) {
        Bookname = bookname;
    }

    public String getBooknumber() {
        return Booknumber;
    }

    public void setBooknumber(String booknumber) {
        Booknumber = booknumber;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSite() {
        return site;
    }

    public void setSite(String site) {
        this.site = site;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
