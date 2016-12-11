<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ca.werewolfgame.beans.*"%>
<!DOCTYPE html>
<html>
    <jsp:include page="Header.jsp">
            <jsp:param name="navigation" value ="<li><a href=\"#\">Main Page</a></li>
                    <li><a href=\"#\">MyGames</a></li>
                    <li><a href=\"#\">My Stats</a></li>
                    <li class=\"active\"><a href=\"#\">Create Game</a></li>
                    <li><a href=\"#\">Logout</a></li>" />
    </jsp:include>
        <div id="MainDiv">
            <h1>Create Game!</h1>
            <form method="post" action="CreateGameServlet" onsubmit="selectAll">
                <div>
                    Name: <input type="text" name="GameName" /><br>
                    Number of Players: <input type="number" id="playerCount" name="playerCount" /><br>
                    <input type="button" value="Next" onclick="SelectPlayers()"/><br>
                </div>
                <div id="PlayerSelection" style="display: none;">
                    <h2>Select the Players</h2>
                    <c:forEach items="${playerRoster}" var="playerName">
                        <input type="checkbox" name="player" value="${playerName}">${playerName}<br>
                    </c:forEach>
                </div>
                <button type="submit">Create Game!</button><br>
            </form>
        </div>
    </body>
</html>


<script type="text/javascript">

    var players = document.getElementById("PlayerSelection");
    var playerCount = 5;

    function SelectPlayers()
    {
        players.style.display = "block";
    }



</script>