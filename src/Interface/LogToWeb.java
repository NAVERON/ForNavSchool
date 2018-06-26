/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

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
public class LogToWeb extends  Application{
    
    public static String PROJECT_NAME = "武汉理工大学航运学院信息搜索平台";

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createContent());
        
        primaryStage.setScene(scene);
        primaryStage.setTitle(LogToWeb.PROJECT_NAME);
        primaryStage.show();
    }

    private Parent createContent() {

        Parent root = null;

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("./Login.fxml"));
            root = loader.load();
        } catch (IOException ex) {
            Logger.getLogger(LogToWeb.class.getName()).log(Level.SEVERE, null, ex);
        }
        return root;
    }
    
    public static void main(String[] args){
        Application.launch(args);
    }
    
}








