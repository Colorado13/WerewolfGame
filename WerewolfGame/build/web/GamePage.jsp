<%-- 
    Document   : GamePage
    Created on : Dec 6, 2016, 7:43:17 PM
    Author     : jpedr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Game Page</h1>
        <h2>Welcome ${user.username}</h2>
        <div class="GameChat">
            <h2>Game Chat</h2>
            <table>
                <c:forEach items="${gameChatHistory}" var="prevMessage">
                    <tr>
                        <td>${prevMessage.username}</td>
                        <td>>></td>
                        <td>${prevMessage.message}</td>
                    </tr>
                </c:forEach>
            </table>
            <form method="post" action="NewMessage">
                Message: <input type="text" name="message" /><br>
                <button type="submit" name="sendMessage" value="gameMessage">Send</button><br>
            </form>
                    
            <div class="Werewolf Chat">
                <table>
                    <h2>Side Chat (Werewolf)</h2>
                    <c:forEach items="${wwChatHistory}" var="prevMessage">
                        <tr>
                            <td>${prevMessage.username}</td>
                            <td>>></td>
                            <td>${prevMessage.message}</td>
                        </tr>
                    </c:forEach>
                </table>
                <form method="post" action="NewMessage">
                    Message: <input type="text" name="message" /><br>
                    <button type="submit" name="sendMessage" value="wwMessage">Send</button><br>
                </form>


            </div>
    </body>
</html>
