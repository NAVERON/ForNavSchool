/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ERON
 */
public class LogToWeb extends Application {

    public static String PROJECT_NAME = "武汉理工大学航运学院信息搜索平台";
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./Search.fxml"));
        SearchController logincontroller = new SearchController(primaryStage);
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
