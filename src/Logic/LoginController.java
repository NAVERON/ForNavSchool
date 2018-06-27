/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Logic;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

/**
 *
 * @author ERON
 */
public class LoginController {
    
    @FXML TextField student_id_textfield; TextField student_pw_textfield; Hyperlink log_visitor; Button log_honor;
    
    
    private Stage primaryStage = null;
    public LoginController(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
    
    @FXML
    public void changeToSearch() {  //这里需要根据是否登陆来判断载入哪个页面
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../Interface/Search.fxml"));
            SearchController searchcontroller = new SearchController(this.primaryStage);
            loader.setController(searchcontroller);
            
            this.primaryStage.setScene(new Scene( loader.load() ));
        } catch (IOException ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String WEB_PAGE = "http://zhlgd.whut.edu.cn/tpass/login?service=http%3A%2F%2Fzhlgd.whut.edu.cn%2Ftp_up%2F";
    @FXML
    public void changeToBrowser(){
        this.primaryStage.close();
        
        WebView webview = new WebView();
        WebEngine webengine = webview.getEngine();
        webengine.load(LoginController.WEB_PAGE);

        Stage webAccess = new Stage();
        Scene scene = new Scene(webview);
        webAccess.show();
    }
}
