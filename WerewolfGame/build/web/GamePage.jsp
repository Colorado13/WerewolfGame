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
            <table>
                <c:forEach items="${gameChatHistory}" var="prevMessage">
                    <tr>
                        <td>${prevMessage.username}</td>
                        <td>>></td>
                        <td>${prevMessage.message}</td>
                    </tr>
                </c:forEach>
            </table>
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
                        <table>
                            <h2>Side Chat (Dead Chat)</h2>
                            <c:forEach items="${deadChatHistory}" var="prevDeadMessage">
                                <tr>
                                    <td>${prevDeadMessage.username}</td>
                                    <td>>></td>
                                    <td>${prevDeadMessage.message}</td>
                                </tr>
                            </c:forEach>
                        </table>
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
                            <table>
                                <h2>Side Chat (Werewolf Chat)</h2>
                                <c:forEach items="${wwChatHistory}" var="prevWwMessage">
                                    <tr>
                                        <td>${prevWwMessage.username}</td>
                                        <td>>></td>
                                        <td>${prevWwMessage.message}</td>
                                    </tr>
                                </c:forEach>
                            </table>
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
            <c:choose>
                <c:when test="${playerInstance.status == 'ALIVE'}">
                    <!-- Vote selection -->
                    <div>
                        <form method="post" action="ActionServlet">
                            Choose player to vote:
                            <select name="selectedPlayer">
                                <c:forEach items="${alivePlayers}" var="player">
                                    <option value="${player}">${player}</option>
                                </c:forEach>
                            </select>
                            <input type="hidden" name="gameId" value="${playerInstance.gameId}"/>
                            <input type="hidden" name="role" value="${playerInstance.role}" />
                            <input type="hidden" name="status" value="${playerInstance.status}" />
                            <input type="hidden" name="currentRound" value="${playerInstance.currentRound}"/>
                            <input type="submit" name="action" value="Vote!"/>
                        </form>
                    </div>
                    <c:if test="${playerInstance.role == 'werewolf'}">
                        <!-- Werewolf kill selection -->
                        <div>
                            <form method="post" action="ActionServlet">
                                Choose who dies:
                                <select name="selectedPlayer">
                                    <c:forEach items="${aliveVillagers}" var="player">
                                        <option value="${player}">${player}</option>
                                    </c:forEach>
                                </select>
                                <input type="hidden" name="gameId" value="${playerInstance.gameId}"/>
                                <input type="hidden" name="role" value="${playerInstance.role}" />
                                <input type="hidden" name="status" value="${playerInstance.status}" />
                                <input type="hidden" name="currentRound" value="${playerInstance.currentRound}"/>
                                <input type="submit" name="action" value="Kill!"/>
                            </form>
                        </div>
                    </c:if>
                </c:when>
            </c:choose>
            <form method="get" action="MainPage.jsp">
                <input type="submit" value="Back">
            </form>
        </div>
    </body>
</html>
