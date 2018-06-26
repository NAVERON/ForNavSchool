/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.io.IOException;
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
public class LogToWeb extends  Application{
    
    public static String PROJECT_NAME = "武汉理工大学航运学院信息搜索平台";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        
        primaryStage.setTitle(LogToWeb.PROJECT_NAME);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private Parent createContent() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("./Login.fxml"));
        Parent root = loader.load();
        
        return root;
    }
    
    public static void main(String[] args){
        Application.launch(args);
    }
    
}








