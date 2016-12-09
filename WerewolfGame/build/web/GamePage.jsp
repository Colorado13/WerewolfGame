<%-- 
    Document   : GamePage
    Created on : Dec 6, 2016, 7:43:17 PM
    Author     : jpedr
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script>
        <script>
            /*var auto_refresh = setInterval(
            function()
            {
                $.ajaxSetup({ cache: false });
                $('#GameChat').fadeOut('slow').load('GetGameChatServlet').fadeIn("slow");
            }, 5000); */
            $(document).ready(function(){
                window.setInterval(function() {// set time limit for AJAX call
                    $(function() {
                        $('#GameChatMessages').load('./GetGameChatServlet');
                        $('#DeadChatMessages').load('./GetDeadChatServlet');
                        $('#WwChatMessages').load('./GetWwChatServlet');
                    });
                }, 1000);
            });
        </script>
    </head>
    <body>
        <h1>Game Page</h1>
        <h2>Welcome ${user.username}</h2>
        <h3>This is Game ${playerInstance.gameId}</h3>
        <h3>Current round is ${playerInstance.currentRound}<h3>
        <h3>In this game you are a ${playerInstance.role}</h3>
        <h3>You are currently ${playerInstance.status}</h3>
        <div class="GameChat">
            <h2>Game Chat</h2>
            <div id="GameChatMessages">
            </div>
            <c:if test="${playerInstance.status == 'ALIVE'}">
                <form method="post" action="NewMessage">
                    Message: <input type="text" name="message" /><br>
                    <!-- maybe our custom tags...? -->
                    <input type="hidden" name="gameId" value="${playerInstance.gameId}"/>
                    <input type="hidden" name="role" value="${playerInstance.role}" />
                    <input type="hidden" name="status" value="${playerInstance.status}" />
                    <input type="hidden" name="currentRound" value="${playerInstance.currentRound}"/>
                    <button type="submit" name="sendMessage" value="gameMessage">Send</button><br>
                </form>
            </c:if>
            <c:choose>
                <c:when test="${playerInstance.status == 'DEAD'}">
                    <div class="Dead Chat">
                        <h2>Side Chat (Dead Chat)</h2>
                        <div id="DeadChatMessages">
                        </div>
                        <form method="post" action="NewMessage">
                            Message: <input type="text" name="message" /><br>
                            <input type="hidden" name="gameId" value="${playerInstance.gameId}"/>
                            <input type="hidden" name="role" value="${playerInstance.role}" />
                            <input type="hidden" name="status" value="${playerInstance.status}" />
                            <input type="hidden" name="currentRound" value="${playerInstance.currentRound}"/>
                            <button type="submit" name="sendMessage" value="deadMessage">Send</button><br>
                        </form>
                    </div>
                </c:when>
                <c:otherwise>
                    <c:if test="${playerInstance.role == 'werewolf'}">
                        <div class="Werewolf Chat">
                            <h2>Side Chat (Werewolf Chat)</h2>
                            <div id="WwChatMessages">
                            </div>
                            <form method="post" action="NewMessage">
                                Message: <input type="text" name="message" /><br>
                                <input type="hidden" name="gameId" value="${playerInstance.gameId}"/>
                                <input type="hidden" name="role" value="${playerInstance.role}" />
                                <input type="hidden" name="status" value="${playerInstance.status}" />
                                <input type="hidden" name="currentRound" value="${playerInstance.currentRound}"/>
                                <button type="submit" name="sendMessage" value="wwMessage">Send</button><br>
                            </form>
                        </div>
                    </c:if>
                </c:otherwise>
            </c:choose>
            <div>

            </div>
            <form method="get" action="MainPage.jsp">
                <input type="submit" value="Back">
            </form>
        </div>
    </body>
</html>
