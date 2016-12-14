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
        <jsp:param name="navigation" value ="<li><a href=\"#\">Main Page</a></li>
                   <li class=\"active\"><a href=\"#\">MyGames</a></li>
                   <li><a href=\"#\">My Stats</a></li>
                   <li><a href=\"#\">Create Game</a></li>
                   <li><a href=\"#\">Logout</a></li>" />
    </jsp:include>
    <div class="MainDiv">
        <h1>My Games Page</h1>
        <h2>Welcome ${user.username}</h2>
        <h2>Your status is ${user.privilege}</h2>
        <form method="post" action="GamePageServlet">
            <c:forEach items="${myGames}" var="game">

                <input type="submit" name="game" value="${game}"/><br>

            </c:forEach>
        </form>
        <form method="get" action="MainPage.jsp">
            <input type="submit" value="Back">
        </form>
    </div>



</body>
</html>
