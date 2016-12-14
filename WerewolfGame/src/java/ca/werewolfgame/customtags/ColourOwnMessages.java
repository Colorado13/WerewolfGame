package ca.werewolfgame.customtags;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;

/**
 *
 * @author lemon
 */
public class ColourOwnMessages extends SimpleTagSupport
{
    private String playername;
    private String sender;
    private String message;

    public void setPlayername(String playername) {
        this.playername = playername;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public ColourOwnMessages() {
    }

    public ColourOwnMessages(String playername, String sender, String message) {
        this.playername = playername;
        this.sender = sender;
        this.message = message;
    }
    
    @Override
    public void doTag() throws JspException, IOException
    {
        
        JspWriter out = getJspContext().getOut();
        
        System.out.println("Custom Tag Code Running...");
        if (playername.equals(sender))
        {
            out.println("<tr style=\"color:blue\">");
            out.println("<td>" + sender + "</td>");
            out.println("<td>>></td>");
            out.println("<td>" + message + "</td>");
            out.println("<tr>");
        }
        else
        {
            out.println("<tr>");
            out.println("<td>" + sender + "</td>");
            out.println("<td>>></td>");
            out.println("<td>" + message + "</td>");
            out.println("<tr>");
        }
    }
}