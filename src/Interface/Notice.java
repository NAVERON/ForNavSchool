/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interface;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author ERON
 */
public class Notice {
    
    public String title;
    public String content;
    public LocalDate date;
    public String superlink;
    
    public Notice(){}
    public Notice(String title, String content, LocalDate date, String superlink){
        this.title = title;
        this.content = content;
        this.date = date;
        this.superlink = superlink;
        
    }
}
