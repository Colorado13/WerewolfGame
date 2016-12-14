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
    <jsp:include page="Header.jsp">
        <jsp:param name="navigation" value ="<li class=\"active\"><a href=\"\WerewolfGame\MainPage.jsp\">Main Page</a></li>
                   <li><a href=\"\WerewolfGame\MyGames\">MyGames</a></li>
                   <li><a href=\"#\">My Stats</a></li>
                   <li><a href=\"\WerewolfGame\PreCreateGame\">Create Game</a></li>
                   <li><a href=\"index.jsp\">Logout</a></li>" />
    </jsp:include>

    <div class="main-container">
        <h1>Main Page</h1>
        <h2>Welcome ${user.username}</h2>
        <h2>Your status is ${user.privilege}</h2>
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
            <button type="submit" name="sendMessage" value="mainChat">Send<br></button>
        </form>
    </div>
</body>
</html>
