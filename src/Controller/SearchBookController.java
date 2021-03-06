package Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class SearchBookController extends Application implements Initializable {
    String[] Books_information = {"书名","馆藏数","作者","出版时间","位置","类别"};//对图书信息的总结


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
    private VBox book;

    @FXML
    private TextField bookname;



    @FXML
    void search(ActionEvent event) {
        try{
            book.getChildren().clear();
            if(refresh("select * from Book "+"where Bookname like"+"'%"+ bookname.getText() +"%'")==-1) {
                String info = "所查询书名不存在";
                Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
                alert.setHeaderText(null);
                alert.setTitle("提示");
                alert.show();
            }

        }catch(SQLException e){
            e.printStackTrace();
            String info = "查询失败";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("提示");
            alert.show();
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/SearchBook.fxml"));
            Scene scene = new Scene(root, 654.0, 744.0);
            primaryStage.setTitle("书籍查找");
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

        try {
            refresh( "select * from Book");

        }catch(SQLException e){
            e.printStackTrace();
            String info = "初始化失败";
            Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
            alert.setHeaderText(null);
            alert.setTitle("提示");
            alert.show();
        }
    }

    public int refresh(String sql)throws SQLException{
        Statement state = ct.createStatement();//容器
        ResultSet rs = state.executeQuery(sql);     //将sql语句传至数据库，返回的值为一个字符集用一个变量接收
        int flag=-1;                             //用于标记查询sql语句是否为空

        HBox information = new HBox(20);
        for (String s : Books_information) {
            information.setPrefSize(470, 100);

            Label test = new Label();
            test.setPrefSize(100, 100);
            test.setWrapText(true);
            test.setFont(Font.font("Agency FB", 15));
            test.setTranslateX(10);
            test.setText(s);

            information.getChildren().add(test);
        }
        book.getChildren().add(information);

        while(rs.next()) {
            HBox k = new HBox(20);
            for (int i = 1; i <= 6; i++) {

                k.setPrefSize(470, 100);

                Label test = new Label();
                test.setPrefSize(100, 100);
                test.setWrapText(true);
                test.setFont(Font.font("Agency FB", 15));
                test.setTranslateX(10);
                test.setText(rs.getString(i));

                k.getChildren().add(test);
            }
            book.getChildren().add(k);
            flag+=1;
        }
        return flag;
    }

}

