<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ca.werewolfgame.beans.*"%>
<!DOCTYPE html>
<html>

    <jsp:include page="Header.jsp">
        <jsp:param name="navigation" value ="<li><a href=\"\WerewolfGame\MainPage.jsp\">Main Page</a></li>
                   <li><a href=\"\WerewolfGame\MyGames\">MyGames</a></li>
                   <li><a href=\"#\">My Stats</a></li>
                   <li class=\"active\"><a href=\"\WerewolfGame\PreCreateGame\">Create Game</a></li>
                   <li><a href=\"index.jsp\">Logout</a></li>" />
    </jsp:include>
   <script src="script/script.js"></script>
    <div class="main-container">
        <h1>Create Game!</h1>
        <form method="post" action="CreateGameServlet" onsubmit="selectAll">
            <div>
                <h2>Select the Players</h2>
                <input type="checkbox" onClick="toggle(this)" /> Select All<br/>
                <c:forEach items="${playerRoster}" var="playerName">
                    <input type="checkbox" name="player" value="${playerName}">${playerName}<br>
                </c:forEach>
            </div>
            <button type="submit">Create Game!</button><br>
        </form>
    </div>
</body>
</html>

