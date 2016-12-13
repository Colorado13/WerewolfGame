<%-- 
    Document   : index
    Created on : Dec 6, 2016, 2:24:50 PM
    Author     : jpedr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <jsp:include page="Header.jsp">
            <jsp:param name="navigation" value ="<li><a href=\"#\">Main Page</a></li>
                    <li><a href=\"#\">MyGames</a></li>
                    <li><a href=\"#\">My Stats</a></li>
                    <li><a href=\"#\">Create Game</a></li>
                    <li><a href=\"#\">Logout</a></li>" />
        </jsp:include>
        <h1>Werewolf Game</h1>
        <h1>Login Page</h1>
        <div class="MainDiv">
            <h2>Login</h2>
            <form method="post" action="LoginServlet">
                Name: <input type="text" name="name" /><br>
                Password: <input type="password" name="password" /><br>
                <button type="submit" name="option" value="login">Login</button><br>
                <button type="submit" name="option" value="createUser">New User</button><br>
            </form>
        </div>
    </body>
</html>
