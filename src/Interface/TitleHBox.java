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
    
    private Label name = null;
    private Hyperlink title = new Hyperlink("http://www.baidu.com");
    private Label state = null;
    
    private Notice notice = null;
    
    public TitleHBox(WebView webview, Notice notice){
        
        this.notice = notice;
        
        this.getChildren().addAll(name, title);
        
        
        WebEngine engine = webview.getEngine();
        title.setOnAction(event -> {
            engine.load(title.getText());
        });
    }
    
    private void setBody(){
        this.name.setText(this.notice.title);
        
    }
    
}
