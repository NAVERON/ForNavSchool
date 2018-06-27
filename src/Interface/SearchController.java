/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class SearchController {
    @FXML
    Button logout;
    
    private Stage primaryStage = null;
    public SearchController(Stage primaryStage){
        this.primaryStage = primaryStage;
    }
    
}
