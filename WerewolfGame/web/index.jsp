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

    <div id="login">
        <h2>Login Page</h2>
        <h3>${loginMessage}
        <form name='form-login' method="post" action="LoginServlet">
            <span class="fontawesome-user"></span></span>
            <input type="text" id="user"  name="name" placeholder="Username"/><br>
            <span class="fontawesome-lock"></span>
            <input type="password" id="pass" name="password" placeholder="Password"/><br>
            <input type="submit" name="option" value="login">
            <input type="submit" name="option" value="createUser">
        </form>
    </div>
</body>
</html>
