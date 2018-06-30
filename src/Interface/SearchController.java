/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.FutureTask;
import javafx.concurrent.Task;
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

    private Stage primaryStage;
    List<ResultHBox> lists;
    private Date from, to;
    private String keywords;

    @FXML
    private Button search_btn;
    @FXML
    private ProgressBar search_progress;
    @FXML
    private HBox mannual_hbox;
    @FXML
    private DatePicker from_datepicker;
    @FXML
    private DatePicker to_datepicker;
    @FXML
    private TextField keywords_textfield;
    @FXML
    private VBox links_boxes;   //左边添加链接
    @FXML
    private WebView content_webview;  //右边显示的超链接网页界面

    public SearchController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.lists = new LinkedList<ResultHBox>();
    }

    public void search() {
        //爬取信息的进程
        String page;

        Notice notice = new Notice("wang", "content string and article", new Date(), "http://www.baidu.com");
        ResultHBox resulthbox = new ResultHBox(content_webview, notice);
        lists.add(resulthbox);
        
        lists.clear();
        addResults(lists);
    }

    class GetPageContent extends FutureTask<ResultHBox>{
        
        public GetPageContent(Runnable runnable, ResultHBox result) {
            super(runnable, result);
        }
        
    }

    public void addResults(List<ResultHBox> lists) {
        for (ResultHBox titlehbox : lists) {
            links_boxes.getChildren().add(titlehbox);
        }
    }
}
