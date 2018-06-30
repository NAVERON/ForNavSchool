/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

public class SearchController implements Initializable {

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Button clear_btn = new Button("ClearAll");
        clear_btn.setOnAction((event) -> {
            links_boxes.getChildren().remove(1, links_boxes.getChildren().size());
        });
        links_boxes.getChildren().add(clear_btn);
    }

    public SearchController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.lists = new LinkedList<ResultHBox>();
    }

    public void search() {
        links_boxes.getChildren().remove(1, links_boxes.getChildren().size());
        //爬取信息的进程
        String page;
        
        
        ResultHBox resulthbox = new ResultHBox(content_webview, notice);
        
        lists.clear();
        lists.add(resulthbox);
        addResults(lists);
    }
    
    class GetNotice {
        
        public GetNotice(Date from, Date to, String keywords){
            ExecutorService executor = Executors.newFixedThreadPool(2);
            executor.shutdown();
        }
        
    }
    class ProcessPage implements  Callable<Notice>{
        
        public ProcessPage(URL url){
            
        }

        @Override
        public Notice call() throws Exception {
            Notice notice = new Notice("这里一共有20个汉字，确认是否能够完没显示", "content string and article", new Date(), "http://www.baidu.com");
            return notice;
        }
        
    }
    
    public void addResults(List<ResultHBox> lists) {
        for (ResultHBox titlehbox : lists) {
            links_boxes.getChildren().add(titlehbox);
        }
    }
    
}



