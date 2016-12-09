<%-- 
    Document   : MyGames
    Created on : Dec 7, 2016, 2:58:25 PM
    Author     : jpedr
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>My Games Page</h1>
        <h2>Welcome ${user.username}</h2>
        <h2>Your status is ${user.privilege}</h2>
        <form method="post" action="GamePageServlet">
            <c:forEach items="${myGames}" var="game">

                <input type="submit" name="game" value="${game}"/><br>

            </c:forEach>
        </form>
    </body>
</html>
