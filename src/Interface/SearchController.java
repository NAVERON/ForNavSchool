/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class SearchController {

    private Stage primaryStage = null;
    private Date from, to;
    private String keywords;

    @FXML
    private Button search_btn;
    @FXML
    private ProgressBar search_progress;
    @FXML
    private HBox mannual_hbox;
    @FXML
    private DatePicker from_date;
    @FXML
    private DatePicker to_date;
    @FXML
    private TextField keywords_textfield;
    @FXML
    private VBox titles_boxs;   //左边添加链接
    @FXML
    private WebView content_webview;  //右边显示的超链接网页界面

    public SearchController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    public void search(){
        titles_boxs.getChildren().add(new TitleHBox(content_webview));
    }
}
