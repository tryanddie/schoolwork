package Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class ReturnController extends Application implements Initializable {

    public static Connection ct = null;
    static String url="jdbc:sqlserver://127.0.0.1:1433;databaseName=library";
    static String user="test";
    static String passerword = "12345";
    public static Connection connect(){
        try{
            return DriverManager.getConnection(url,user,passerword);
        }catch(Exception e){
            e.printStackTrace();
            String info = "连接失败";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("提示");
            alert.show();
            return ct;
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/Return.fxml"));
            Scene scene = new Scene(root, 600.0, 400.0);
            primaryStage.setTitle("还书");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ct = connect();
    }

    @FXML
    private TextField account;

    int flag = 0;
    String bookname;

    @FXML
    void search(ActionEvent event) {

        try {
            Statement state = ct.createStatement();
            ResultSet rs = state.executeQuery("select * from information where account = '"+account.getText()+"'");     //将sql语句传至数据库，返回的值为一个字符集用一个变量接收
            if(rs.next()){
                bookname = rs.getString(3);
                if(bookname==null){
                    String info = "该用户并未借书";
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
                    alert.setHeaderText(null);
                    alert.setTitle("提示");
                    alert.show();
                }else{
                    flag=1;
                    String info = "该用户所借书为  "+bookname;
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
                    alert.setHeaderText(null);
                    alert.setTitle("提示");
                    alert.show();
                }

            }else{
                String info = "该账号不存在";
                Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
                alert.setHeaderText(null);
                alert.setTitle("提示");
                alert.show();
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }

    @FXML
    void return_book(ActionEvent event) {
        if(flag==0){
            String info = "请先确认用户所借书籍";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("提示");
            alert.show();
        }else{
            try {
                flag = 0;
                Statement state = ct.createStatement();
                state.executeUpdate("update book set Booknumber = Booknumber+1 Where Bookname = '"+bookname+"'");
                state.executeUpdate("update information set book = null where account = '"+account.getText()+"'");

                String info = "还书完成";
                Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
                alert.setHeaderText(null);
                alert.setTitle("提示");
                alert.show();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
    }
}
