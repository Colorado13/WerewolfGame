<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="WEB-INF/tlds/WwTagLib.tld" prefix="ww"%>
<!DOCTYPE html>
<html>
    <head>
        <link href="https://fonts.googleapis.com/css?family=Shadows+Into+Light" rel="stylesheet">
        <link rel="stylesheet" type="text/css" href="css/style.css">
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script>
        <script src="script/script.js"></script>
        <jsp:include page="Header.jsp">
            <jsp:param name="navigation" value ="<a href=\"\WerewolfGame\MainPage.jsp\">Main Page</a></li>
                       <li><a href=\"\WerewolfGame\MyGames\">MyGames</a></li>
                       <li><a href=\"#\">My Stats</a></li>
                       <li><a href=\"\WerewolfGame\PreCreateGame\">Create Game</a></li>
                      <li><a href=\"index.jsp\">Logout</a></li>" />
        </jsp:include>
    <div class="main-container">
        <div class="mainchat">
            <h2>Game Chat</h2>
            <div id="GameChatMessages">
            </div>
            <div id="GameChatInput">
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
            </div>
        </div>
        <div class="right-screen">
            <div class="sidechat">
                <c:choose>
                    <c:when test="${playerInstance.status == 'DEAD'}">
                        <div class="Dead Chat" id="SideChat">
                            <h2>Side Chat (Dead Chat)</h2>
                            <div id="DeadChatMessages">
                            </div>
                            <div id="SideChatInput"
                                 <form method="post" action="NewMessage">
                                    Message: <input type="text" name="message" /><br>
                                    <input type="hidden" name="gameId" value="${playerInstance.gameId}"/>
                                    <input type="hidden" name="role" value="${playerInstance.role}" />
                                    <input type="hidden" name="status" value="${playerInstance.status}" />
                                    <input type="hidden" name="currentRound" value="${playerInstance.currentRound}"/>
                                    <button type="submit" name="sendMessage" value="deadMessage">Send</button><br>
                                </form>
                            </div>
                        </div>

                    </c:when>
                    <c:otherwise>
                        <c:if test="${playerInstance.role == 'werewolf'}">
                            <div class="Werewolf Chat" id="SideChat">
                                <h2>Side Chat (Werewolf Chat)</h2>
                                <div id="WwChatMessages">
                                </div>

                                <div id="SideChatInput">
                                    <form method="post" action="NewMessage">
                                        Message: <input type="text" name="message" /><br>
                                        <input type="hidden" name="gameId" value="${playerInstance.gameId}"/>
                                        <input type="hidden" name="role" value="${playerInstance.role}" />
                                        <input type="hidden" name="status" value="${playerInstance.status}" />
                                        <input type="hidden" name="currentRound" value="${playerInstance.currentRound}"/>
                                        <button type="submit" name="sendMessage" value="wwMessage">Send</button><br>
                                    </form>
                                </div>
                            </div>

                        </c:if>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="gameactions">
                <div class="gameinfo">
                    <h2>Welcome ${user.username}</h2>
                    <h3>This is Game ${playerInstance.gameId}</h3>
                    <h3>Current round is ${playerInstance.currentRound}</h3>
                    <h3>In this game you are a ${playerInstance.role}</h3>
                    <h3>You are currently ${playerInstance.status}</h3>
                </div>
                <h2>Votes</h2>
                <div class="tally">
                    <div id="ShowVotesAgainst">
                    </div>
                </div>
                <div class="inverted-tally">
                    <div id="ShowVotes">
                    </div>      
                </div>
                <div class="actions">
                    <h2>Actions</h2>
                    <c:choose>
                        <c:when test="${playerInstance.status == 'ALIVE'}">
                            <!-- Vote selection -->
                            <div id="VoteForm">
                                <form method="post" action="ActionServlet" >
                                    Choose player to vote:
                                    <select name="selectedPlayer">
                                        <option value="Players" disabled selected>
                                            <c:forEach items="${alivePlayers}" var="player">
                                            <option value="${player}">${player}</option>
                                        </c:forEach>
                                    </select>
                                    <input type="hidden" name="gameId" value="${playerInstance.gameId}"/>
                                    <input type="hidden" name="role" value="${playerInstance.role}" />
                                    <input type="hidden" name="status" value="${playerInstance.status}" />
                                    <input type="hidden" name="currentRound" value="${playerInstance.currentRound}"/>
                                    <input type="submit" name="action" value="Vote!"/>
                                    <input type="submit" name="action" value="Nightfall!"/>
                                </form>
                            </div>


                            <c:if test="${playerInstance.role == 'werewolf'}">
                                <!-- Werewolf kill selection -->
                                <div id="KillOrderForm">
                                    <form method="post" action="ActionServlet">
                                        Choose who dies:
                                        <select name="selectedPlayer">
                                            <option value="Villagers" disabled selected>
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
                </div>
            </div>
        </div>
    </div>
</body>
</html>
