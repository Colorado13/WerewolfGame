<%-- 
    Document   : MyGames
    Created on : Dec 7, 2016, 2:58:25 PM
    Author     : jpedr
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="Header.jsp">
        <jsp:param name="navigation" value ="<li><a href=\"\WerewolfGame\MainPage.jsp\">Main Page</a></li>
                   <li class=\"active\"><a href=\"\WerewolfGame\MyGames\">MyGames</a></li>
                   <li><a href=\"#\">My Stats</a></li>
                   <li><a href=\"\WerewolfGame\/CreateGameServlet\">Create Game</a></li>
                   <li><a href=\"index.jsp\">Logout</a></li>" />
    </jsp:include>
    <div class="main-container">
        <h1>My Games Page</h1>
        <h2>Welcome ${user.username}</h2>
        <h2>Your status is ${user.privilege}</h2>
        <form method="post" action="GamePageServlet">
            <c:forEach items="${myGames}" var="game">

                <input type="submit" name="game" value="${game}"/><br>

            </c:forEach>
        </form>
    </div>



</body>
</html>
