/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import javafx.scene.control.Hyperlink;
import javafx.scene.layout.HBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

/**
 *
 * @author ERON
 */
public class TitleHBox extends HBox{
    private Hyperlink title = new Hyperlink("http://www.baidu.com");
    
    public TitleHBox(WebView webview){
        
        this.getChildren().add(title);
        
        
        WebEngine engine = webview.getEngine();
        title.setOnAction(event -> {
            engine.load(title.getText());
        });
    }
    
}
