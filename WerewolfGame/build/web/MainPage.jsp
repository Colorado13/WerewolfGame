<%-- 
    Document   : MainPage
    Created on : Dec 6, 2016, 2:40:49 PM
    Author     : jpedr
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ca.werewolfgame.beans.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Main Page</h1>
        <h2>Welcome ${user.username}</h2>
        <h2>Your status is ${user.privilege}</h2>
        <div>
            <table>
                <c:forEach items="${chatHistory}" var="prevMessage">
                    <tr>
                        <td>${prevMessage.username}</td>
                        <td>>></td>
                        <td>${prevMessage.message}</td>
                    </tr>
                </c:forEach>
            </table>
            <form method="post" action="NewMessage">
                Message: <input type="text" name="message" /><br>
                <button type="submit" name="sendMessage" value="mainChat">Send</button><br>
            </form>
            
            <c:if test="${user.privilege eq 'ADMIN'}">
                <a href="CreateGame.jsp">Create Game</a>
            </c:if>




        </div>
    </body>
</html>
