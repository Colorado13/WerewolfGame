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
    <script language="JavaScript">
        function toggle(source) {
            checkboxes = document.getElementsByName('player');
            for (var i = 0, n = checkboxes.length; i < n; i++) {
                checkboxes[i].checked = source.checked;
            }
        }
    </script>
    <div class="MainDiv">
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

