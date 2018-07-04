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
import javafx.scene.control.Alert;
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
    /*  //时间格式的转换问题，显示使用local格式就不用转换了，一切正常
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
    private Button search_btn, cancle_btn;
    @FXML
    private ProgressBar search_progress;
    @FXML
    private HBox mannual_hbox;
    @FXML
    private DatePicker from_datepicker, to_datepicker;
    @FXML
    private TextField keywords_textfield, department_textfield;
    @FXML
    private VBox links_boxes;   //左边添加链接
    @FXML
    private WebView content_webview;  //右边显示的超链接网页界面

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //设置默认的搜索时间范围
        from_datepicker.setValue(LocalDate.now().minusMonths(1));
        to_datepicker.setValue(LocalDate.now());

    }

    public SearchController(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.lists = new LinkedList<Notice>();
    }

    //搜索模式
    //根据学院搜索，department输入学院名称
    //根据部门通知公告搜索，department输入部门名称
    //不输入department，搜索页面中的所有超链接            相关关键字       内容
    public void search() {
        //截面数据输入
        this.from = from_datepicker.getValue();
        this.to = to_datepicker.getValue();
        this.keywords = keywords_textfield.getText();
        this.department = department_textfield.getText();
        System.out.println(from + "\n" + to);
        if (keywords.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("请输入关键字");
            alert.showAndWait();
            return;
        }
        //取消了验证，如果没有关键字则显示所有的链接
        ExecutorService executer = Executors.newCachedThreadPool();
        ProcessPage processpage = new ProcessPage(this.from, this.to, this.keywords, this.department);  //链接内置
        FutureTask<List<Notice>> futuretask = new FutureTask<>(processpage);
        search_progress.progressProperty().bind(processpage.process);  //绑定进度
        executer.submit(futuretask);

    }

    class ProcessPage implements Callable<List<Notice>> {  //多线程处理网页请求，异步处理，使用Jsoup库

        private String[] urls = {"http://i.whut.edu.cn/xxtg/", "http://i.whut.edu.cn/xyxw/"};
        private DoubleProperty process = new SimpleDoubleProperty(0);  //进度表示
        private List<Notice> notices = new LinkedList<Notice>();  //用来返回结果

        private LocalDate from, to;
        private String keywords, department;

        public ProcessPage(LocalDate from, LocalDate to, String keywords, String department) {
            this.from = from;
            this.to = to;
            this.keywords = keywords;
            if( !department.isEmpty() ){
                this.department = department;
            }
            
        }
        //关于搜索中需要使用的变量存储

        @Override
        public List<Notice> call() throws Exception {

            for (String search_url : urls) {
                Document doc = Jsoup.connect(search_url).get();
                String result = doc.toString();
                while (true) {
                    process.set(process.get() + 0.2);
                    System.out.println(process.getValue());
                    Thread.sleep(500);
                    if (process.getValue() > 1) {
                        process.set(0);
                        break;
                    }
                }
            }

            return notices;
        }

    }

    public void addResultHBox(List<Notice> lists) {
        //首先清空以前的搜索结果
        if (!links_boxes.getChildren().isEmpty()) {
            links_boxes.getChildren().clear();
        }
        //根据搜索结果添加到界面显示
        for (Notice notice : lists) {
            links_boxes.getChildren().add(new ResultHBox(content_webview, notice));
        }
    }

}
