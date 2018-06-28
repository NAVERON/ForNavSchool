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
public class TitleHBox extends HBox{
    
    private Label name = new Label();
    private Hyperlink title = new Hyperlink();
    private Label state = new Label();
    
    private WebView webview;
    private Notice notice = null;
    
    public TitleHBox(WebView webview, Notice notice){
        this.webview = webview;
        this.notice = notice;
        
        this.name.setText(notice.title);
        this.title.setText(notice.superlink);
        this.getChildren().addAll(name, title);
        
        
        WebEngine engine = this.webview.getEngine();
        title.setOnAction(event -> {
            engine.load(title.getText());
        });
    }
    
    private void setBody(){
        this.name.setText(this.notice.title);
        
    }
    
}
