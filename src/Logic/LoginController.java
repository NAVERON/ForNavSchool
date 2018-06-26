/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author ERON
 */
public class LoginController {

    @FXML
    Button login;
    
    Stage primaryStage = null;

    public LoginController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    public void changeToScene() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interface/Search.fxml"));
            SearchController searchcontroller = new SearchController();
            loader.setController(searchcontroller);
            
            this.primaryStage.setScene(new Scene( loader.load() ));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
