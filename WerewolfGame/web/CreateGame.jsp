<%-- 
    Document   : CreateGame
    Created on : Dec 6, 2016, 8:39:08 PM
    Author     : jpedr
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="ca.werewolfgame.beans.*"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <style type="text/css">

        </style>
    </head>
    <body>
        <h1>Create Game!</h1>
        <div>
            <form method="post" action="CreateGameServlet">
                <div>
                    Name: <input type="text" name="GameName" /><br>
                    Number of Players: <input type="number" id="playerCount" name="playerCount" /><br>
                    <input type="button" value="Next" onclick="SelectPlayers()"/><br>
                </div>
                <div id="PlayerSelection">
                    <h2>Select the Players</h2>
                    

                </div>
            </form>
        </div>
    </body>
</html>


<script type="text/javascript">

    var players = document.getElementById("PlayerSelection");

    players.style.display = 'none';

    function SelectPlayers()
    {
        players.style.display = "block";
        

        var a = parseInt(document.getElementById("playerCount").value);
        var ch = document.getElementById("PlayerSelection");

        for (i = 0; i < a; i++) {
            var input = document.createElement("input");
            ch.appendChild(input);
        }

    }

</script>