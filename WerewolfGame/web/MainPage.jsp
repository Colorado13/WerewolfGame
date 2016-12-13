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
        <jsp:param name="navigation" value ="<li class=\"active\"><a href=\"#\">Main Page</a></li>
                   <li><a href=\"#\">MyGames</a></li>
                   <li><a href=\"#\">My Stats</a></li>
                   <li><a href=\"#\">Create Game</a></li>
                   <li><a href=\"#\">Logout</a></li>" />
    </jsp:include>

    <div class="MainDiv">
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
        <form method="post" action="MyGames">
            <input type="submit" value="My Games"/><br>
        </form>
        <c:if test="${user.privilege eq 'ADMIN'}">
            <form method="post" action="PreCreateGame">
                <input type="submit" value="Create Game"/>
            </form>
        </c:if>

    </div>
</body>
</html>
