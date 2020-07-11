package info;

import java.io.Serializable;

public class UserInfo implements Serializable {
    String account;     //账号
    String password;    //密码
    String book_lend;      //所借书

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBook_lend() {
        return book_lend;
    }

    public void setBook_lend(String book_lend) {
        this.book_lend = book_lend;
    }
}
