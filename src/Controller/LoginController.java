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
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ResourceBundle;

public class LoginController extends Application implements Initializable {

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

        try(
                ObjectOutputStream out =new ObjectOutputStream(new FileOutputStream("src/resource/user.ser")))
        {
            //存储当前用户对象
            out.writeObject(userInfo);
        }catch(IOException e){
            e.printStackTrace();
        }
        if(ID.getText().equals("test")&&password.getText().equals("12345")){
            ChooseController test = new ChooseController();
            try {
                test.start(new Stage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
