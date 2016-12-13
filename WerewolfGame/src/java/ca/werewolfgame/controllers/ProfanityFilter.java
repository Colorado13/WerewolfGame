package ca.werewolfgame.controllers;

import ca.werewolfgame.beans.User;
import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Scanner;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ProfanityFilter implements Filter 
{
    public ProfanityFilter() 
    {
        
    }    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException 
    {
        HttpServletRequest httpReq = (HttpServletRequest)request;
        HttpServletResponse httpResp = (HttpServletResponse)response;
        
        HttpSession session = httpReq.getSession();
        String message = httpReq.getParameter("message");
        User user = (User) session.getAttribute("user");
        String chat = httpReq.getParameter("sendMessage");
        if (httpReq.getParameter("gameId") != null)
        {
            int gameId = Integer.parseInt(httpReq.getParameter("gameId"));
            httpReq.setAttribute("gameId", gameId);
        }
        if (httpReq.getParameter("role") != null)
        {
            String role = httpReq.getParameter("role");
            httpReq.setAttribute("role", role);
        }
        if (httpReq.getParameter("status") != null)
        {
            String status = httpReq.getParameter("status");
            httpReq.setAttribute("status", status);
        }
        if (httpReq.getParameter("currentRound") != null)
        {
            int currentRound = Integer.parseInt(httpReq.getParameter("currentRound"));
            httpReq.setAttribute("currentRound", currentRound);
        }
        
        System.out.println(message);
        Scanner readBadWords = new Scanner(new File("badwords.txt"));
        ArrayList<String> badWords = new ArrayList<>();
        ArrayList<String> messageWords = new ArrayList<>();
        String [] messageWordsArray = message.split(" ");
        for (int j = 0; j < messageWordsArray.length; j++)
        {
            String word = messageWordsArray[j];
            messageWords.add(word);
        }
        while (readBadWords.hasNextLine())
        {
            String badWord = readBadWords.nextLine();
            badWords.add(badWord);
        }
        messageWords.retainAll(badWords);
        if (!messageWords.isEmpty())
        {
            message = "********(Profanity Detected!)*********";
        } 
        WrappedResponse wrappedResp = new WrappedResponse(httpResp);
        
        httpReq.setAttribute("user", user);
        httpReq.setAttribute("message", message);
        httpReq.setAttribute("sendMessage", chat);
        
        chain.doFilter(httpReq, wrappedResp);
    }   
    
    public void destroy() {        
    }

    public void init(FilterConfig filterConfig) {        
        
    }
    
}
