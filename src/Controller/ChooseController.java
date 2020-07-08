package Controller;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChooseController extends Application implements Initializable {


    @Override
    public void start(Stage primaryStage) throws IOException {
        try {
            AnchorPane root = FXMLLoader.load(getClass().getResource("/fxml/Choose.fxml"));
            Scene scene = new Scene(root, 400.0, 500.0);
            primaryStage.setTitle("用户选择");
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void management(ActionEvent event) {
        ControlBookController test = new ControlBookController();
        try {
            test.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void search_book(ActionEvent event) {
        SearchBookController test = new SearchBookController();
        try {
            test.start(new Stage());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
