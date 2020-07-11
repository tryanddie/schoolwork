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

public class RegisteredController extends Application implements Initializable {

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
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/Registered.fxml"));
            Scene scene = new Scene(root, 600.0, 400.0);
            primaryStage.setTitle("账户注册");
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
    private TextField password;

    @FXML
    private TextField account;

    @FXML
    void Create(ActionEvent event)  {

        try {
            Statement state = ct.createStatement();
            if(state.executeQuery("select * from information where account="+account.getText()).next())
            {
                String info = "账号已存在";
                Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
                alert.setHeaderText(null);
                alert.setTitle("提示");
                alert.show();
            }else{
            state.executeUpdate("insert into information(account,password) values("+"'"+account.getText()+"','"+password.getText()+"')");     //将sql语句传至数据库，返回的值为一个字符集用一个变量接收
            String info = "插入成功";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("提示");
            alert.show();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            String info = "插入错误，请检查输入";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("提示");
            alert.show();
        }

    }
}
