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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InsertController extends Application implements Initializable {
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

    @FXML
    private TextField date;

    @FXML
    private TextField number;

    @FXML
    private TextField site;

    @FXML
    private TextField author;

    @FXML
    private TextField bookname;

    @FXML
    private TextField category;

    @FXML
    void insert(ActionEvent event)  {
        Pattern check_date = Pattern.compile("[1-9]\\d{3}\\.[1]?\\d\\.(([1-9])|(([12])[0-9])|30|31)$");
        Matcher CheckDate = check_date.matcher(date.getText());

        if(CheckDate.matches() && Pattern.matches("\\d+",number.getText())) {
        //对于日期或数量格式错误的插入不予插入，并显示错误
            try {
                Statement state = ct.createStatement();
                state.executeUpdate("insert into Book values(" + "'" + bookname.getText() + "','" +
                        number.getText() + "','" + author.getText() + "','" + date.getText() + "','" + site.getText() + "','" + category.getText() + "')");     //将sql语句传至数据库，返回的值为一个字符集用一个变量接收
                String info = "插入成功";
                Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
                alert.setHeaderText(null);
                alert.setTitle("提示");
                alert.show();
            } catch (SQLException e) {
                e.printStackTrace();
                String info = "插入错误，请检查输入";
                Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
                alert.setHeaderText(null);
                alert.setTitle("提示");
                alert.show();
            }
        }else{
            String info = "";
            if(!CheckDate.matches())
                info = "日期格式有误，应为形如2020.4.19的形式\n";
            if(!Pattern.matches("\\d+",number.getText())) info = info+"书籍数量格式有误，应为正整数";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("提示");
            alert.show();
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/insert.fxml"));
            Scene scene = new Scene(root, 600.0, 400.0);
            primaryStage.setTitle("书籍插入");
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
}
