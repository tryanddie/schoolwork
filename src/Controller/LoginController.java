package Controller;

import info.UserInfo;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController extends Application implements Initializable {

    public static Connection ct = null;
    static String url="jdbc:sqlserver://127.0.0.1:1433;databaseName=library";
    public static Connection connect(String user,String passerword){
        try{
            return DriverManager.getConnection(url,user,passerword);
        }catch(Exception e){
            return ct;
        }
    }

    @FXML
    private PasswordField password;

    @FXML
    private TextField ID;

    /*
        用户信息,用来在controller之间传递用户信息
        一开始为默认用户
     */
    private final UserInfo userInfo = new UserInfo();

    @FXML
    void login(ActionEvent event) {
            if(connect(ID.getText(),password.getText())!=null){

            ControlBookController test = new ControlBookController();
            try {
                test.start(new Stage());
                return ;
            } catch (Exception e) {
                e.printStackTrace();
            }
           }else {
            ct = connect("test","12345");
         }
            try {
                Statement state = ct.createStatement();
                ResultSet rs = state.executeQuery("select * from information where account= '"+ID.getText()+"'And password='"+password.getText()+"'");     //将sql语句传至数据库，返回的值为一个字符集用一个变量接收
                if(rs.next()){
                    userInfo.setAccount(rs.getString(1));
                    userInfo.setPassword(rs.getString(2));
                    userInfo.setBook_lend(rs.getString(3));

                    UserController test = new UserController();
                    try {
                        test.start(new Stage());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }else{
                    String info = "账号不存在";
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, info, new ButtonType("确定", ButtonBar.ButtonData.YES));
                    alert.setHeaderText(null);
                    alert.setTitle("提示");
                    alert.show();
                    return ;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try(
                ObjectOutputStream out =new ObjectOutputStream(new FileOutputStream("src/resource/user.ser")))
        {
            //存储当前用户对象
            out.writeObject(userInfo);
        }catch(IOException e){
            e.printStackTrace();
        }

            ct=null;
    }

    @FXML
    void signUp(ActionEvent event) {
        //游客登录直接打开书籍查找界面
        SearchBookController test = new SearchBookController();
        try {
            test.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/Login.fxml"));
            Scene scene = new Scene(root, 288.0, 400.0);
            primaryStage.setTitle("用户登录");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
