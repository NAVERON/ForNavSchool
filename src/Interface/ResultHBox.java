/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.text.SimpleDateFormat;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author ERON
 */
public class ResultHBox extends HBox{
    
    private Hyperlink box_title = new Hyperlink();
    private Label box_content = new Label();
    private Label box_date = new Label();
    private Hyperlink box_superlink = new Hyperlink();
    //表示当前状态
    private Label state = new Label();
    //////////////////////////////////外界传进来的引用
    private WebView webview;
    private Notice notice = null;
    
    public ResultHBox(){}
    public ResultHBox(WebView webview, Notice notice){  //显示以当前组件为主，数据以notice为主
        this.webview = webview;
        this.notice = notice;
        setBody();
        
        WebEngine engine = this.webview.getEngine();
        box_title.setOnMouseClicked((event) -> {
            engine.load(notice.superlink);
        });
    }
    
    private void setBody(){
        box_title.setText(notice.title);
        box_title.setTooltip(new Tooltip(notice.superlink));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        box_date.setText(sdf.format(notice.date));
        
        //界面更新
        getChildren().addAll(box_title, box_date);
    }
    
}
