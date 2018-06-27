/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class SearchController {

    private Stage primaryStage = null;

    @FXML
    private Button search_btn;
    @FXML
    private ProgressBar search_progress;
    @FXML
    private HBox mannual_hbox;

    public SearchController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

}
