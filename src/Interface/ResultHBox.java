/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author ERON
 */
public class ResultHBox extends HBox{
    
    private Label box_title = new Label();
    private Label box_content = new Label();
    private Label box_date = new Label();
    private Hyperlink box_superlink = new Hyperlink();
    //表示当前状态
    private Label state = new Label();
    //////////////////////////////////外界传进来的引用
    private WebView webview;
    private Notice notice = null;
    
    public ResultHBox(WebView webview, Notice notice){  //显示以当前组件为主，数据以notice为主
        this.webview = webview;
        this.notice = notice;
        
        setBody();
        this.getChildren().addAll(box_title, box_superlink, box_date);
        
        WebEngine engine = this.webview.getEngine();
        box_superlink.setOnAction(event -> {
            engine.load(notice.superlink);
        });
    }
    
    private void setBody(){
        box_title.setText(notice.title);
        box_content.setText(notice.content.substring(20));
        box_date.setText(notice.date.toString());
        box_superlink.setText(notice.superlink);
        
    }
    
}
