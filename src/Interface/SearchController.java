/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.net.URL;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.*;

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
    ExecutorService executer = Executors.newSingleThreadExecutor();
    ProcessPage processpage;
    FutureTask<List<Notice>> futuretask;
    public void search() {
        lists.clear();
        links_boxes.getChildren().clear();
        //截面数据输入
        this.from = from_datepicker.getValue();
        this.to = to_datepicker.getValue();
        this.keywords = keywords_textfield.getText();
        this.department = department_textfield.getText();
        if (keywords.trim().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("请输入关键字");
            alert.showAndWait();
            return;
        }
        //取消了验证，如果没有关键字则显示所有的链接
        processpage = new ProcessPage(this.from, this.to, this.keywords, this.department);  //链接内置
        futuretask = new FutureTask<>(processpage);
        search_progress.progressProperty().bind(processpage.process);  //绑定进度
        
        Task updateUI = new Task<Void>() {
            @Override
            public Void call() {
                try {
                    while (true) {                        
                        if (futuretask.isDone()) {
                            addResultHBox();
                            break;
                        } else {
                            Thread.sleep(500);
                        }
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(SearchController.class.getName()).log(Level.SEVERE, null, ex);
                }
                return null;
            }

        };
        Thread next = new Thread(updateUI);
        next.setDaemon(true);
        next.start();
        
        executer.submit(futuretask);
        executer.shutdown();
    }
    public void cancle_search(){
        futuretask.cancel(true);
        processpage.process.set(0);
        System.out.println("取消任务");
        addResultHBox();
    }

    class ProcessPage implements Callable<List<Notice>> {  //多线程处理网页请求，异步处理，使用Jsoup库

        private String[] urls = {"http://i.whut.edu.cn/xxtg/", "http://i.whut.edu.cn/xyxw/"};
        private DoubleProperty process = new SimpleDoubleProperty(0);  //进度表示

        private LocalDate from, to;
        private String keywords, department;

        public ProcessPage(LocalDate from, LocalDate to, String keywords, String department) {
            this.from = from;
            this.to = to;
            this.keywords = keywords;
            this.department = department;
        }
        //  关于搜索中需要使用的变量存储
        //  process.set(process.get() + 0.2);
        @Override
        public List<Notice> call() throws Exception {
            
            Map<String, String> depart_name = new HashMap<String, String>();  //部门号对应的超链接，大类
            for (String search_url : urls) {       //这里获取所有大标题，两大块搜索，部门和学院
                Document doc = Jsoup.connect(search_url).get();
                Elements by_class = doc.getElementsByClass("text_list_menu2");
                for(Element class_list : by_class){
                    Elements by_tag = class_list.getElementsByTag("a");
                    for(Element a_tag : by_tag){  //各个school的名称和对应的超链接
                        depart_name.put(a_tag.text(), a_tag.attr("href"));
                    }
                }
            }
            //根据部门号查找，超链接，添加到
            List<String> need_search = new LinkedList<String>();  //先筛选部门和学院
            if(this.department.isEmpty()){
                for(String key : depart_name.keySet()){
                    need_search.add(depart_name.get(key));  //添加所有链接
                }
            }else{
                for(String key : depart_name.keySet()){
                    if( key.contains(this.department) ){
                        need_search.add(depart_name.get(key));
                    }
                }
            }
            //现在need_search是所有需要搜索关键字的链接了
            for(String url : need_search){   //点开大部门的链接
                Document doc = Jsoup.connect(url).timeout(10*1000).get();
                //先判断总体情况
                //Elements number_by_class = doc.getElementsByClass("num_nav");
                //Elements by_class = doc.getElementsByClass("normal_list2");
                //需要使用动态解析网页
                
                //先做后面的内容，这个问题解决不了
                Element by_class = doc.getElementsByClass("normal_list2").first();
                Elements get_li = by_class.getElementsByTag("li");
                int size = get_li.size();  //总数
                int index = 0;
                for(Element li : get_li){
                    index++;  //界面进度条显示进度
                    process.set(index/size);
                    
                    Element a = li.select("a").first();
                    Element strong = li.select("strong").first();
                    //提取信息进行筛选
                    String title = a.attr("title");  //名称
                    String link = a.attr("abs:href");  //超链接
                    String content = Jsoup.connect(link).get().getElementById("divToPrint").toString();  //连接内容，方便以后增加分析层
                    LocalDate date = LocalDate.parse(strong.text());
                    if(date.isAfter(from) && date.isBefore(to)){
                        if (title.contains(keywords)) {
                            Notice temp = new Notice(title, Jsoup.connect(link).get().getElementById("divToPrint").toString(), LocalDate.parse(strong.text()), link);
                            lists.add(temp);
                        }
                    }
                }
            }
            //搜索完毕
            process.set(0);
            return lists;
        }

    }

    public void addResultHBox() {
        //根据搜索结果添加到界面显示
        Platform.runLater(() -> {
            for (Notice notice : lists) {
                links_boxes.getChildren().add(new ResultHBox(content_webview, notice));
            }
        });
    }

}
