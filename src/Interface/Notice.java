/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.util.Date;

/**
 *
 * @author ERON
 */
public class Notice {
    
    public String title = null;
    public String content = null;
    public Date date = null;
    public String superlink = "http://www.baidu.com";
    
    public Notice(String title, String content, Date date, String superlink){
        this.title = title;
        this.content = content;
        this.date = date;
        this.superlink = superlink;
    }
}
