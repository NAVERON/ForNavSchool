/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import Logic.LoginController;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 *
 * @author ERON
 */
public class LogToWeb extends Application {

    public static String PROJECT_NAME = "武汉理工大学航运学院信息搜索平台";

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./Login.fxml"));
        LoginController logincontroller = new LoginController(primaryStage);
        loader.setController(logincontroller);
        
        Scene scene = new Scene(loader.load());
        primaryStage.setScene(scene);
        primaryStage.setTitle(LogToWeb.PROJECT_NAME);
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

}
