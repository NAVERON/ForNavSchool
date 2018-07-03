/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.FloatProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleFloatProperty;
import javafx.beans.property.StringProperty;
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
import jdk.nashorn.internal.ir.BreakNode;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

public class SearchController implements Initializable {

    private Stage primaryStage;
    private List<Notice> lists;
    private LocalDate from, to;
    private String keywords, department;
    /*
    LocalDate localDate = datePicker.getValue();
Instant instant = Instant.from(localDate.atStartOfDay(ZoneId.systemDefault()));
Date date = Date.from(instant);
System.out.println(localDate + "\n" + instant + "\n" + date);
    
    
    Date date = new Date();
Instant instant = date.toInstant();
LocalDate localDate = instant.atZone(ZoneId.systemDefault()).toLocalDate();
System.out.println(date + "\n" + instant + "\n" + localDate);
     */
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
    private TextField keywords_textfield, department_textfield;
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
        to_datepicker.setValue(LocalDate.now());  //设置现在为搜索结束时间
        links_boxes.getChildren().add(clear_btn);  //添加清空搜索结果的按钮

    }

    public SearchController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.lists = new LinkedList<Notice>();
    }

    public void search() {
        //爬取信息的进程
        String url = "http://i.whut.edu.cn/";  //需要爬取的网页
        this.from = from_datepicker.getValue();
        this.to = to_datepicker.getValue();
        this.keywords = keywords_textfield.getText();
        this.department = department_textfield.getText();

        ProcessPage processpage = new ProcessPage(url);
        FutureTask<List<Notice>> futuretask = new FutureTask<>(processpage);
        search_progress.progressProperty().bind(processpage.process);  //绑定进度

        ExecutorService executer = Executors.newCachedThreadPool();
        executer.submit(futuretask);
        executer.shutdown();

        if (futuretask.isDone()) {
            return;
        }
        addResultHBox(lists);
    }

    public boolean verifyInput() {  //检测输入数据的正确性

        return true;
    }

    class ProcessPage implements Callable<List<Notice>> {  //多线程处理网页请求，异步处理，使用Jsoup库

        private String url;
        public DoubleProperty process = new SimpleDoubleProperty(0);

        public ProcessPage(String url) {
            this.url = url;
        }

        @Override
        public List<Notice> call() throws Exception {
            Document doc = Jsoup.connect(this.url).get();
            String result = doc.toString();
            System.out.println(result);
            while (true) {
                System.out.println(process.get());
                process.add(10);
                Thread.sleep(200);
                if (process.doubleValue() > 100) {
                    break;
                }
            }
            return null;
        }

    }

    public void addResultHBox(List<Notice> lists) {
        //首先清空以前的搜索结果
        if (links_boxes.getChildren().size() > 1) {
            links_boxes.getChildren().remove(1, links_boxes.getChildren().size());
        }
        //根据搜索结果添加到界面显示
        for (Notice notice : lists) {
            links_boxes.getChildren().add(new ResultHBox(content_webview, notice));
        }
    }

}
