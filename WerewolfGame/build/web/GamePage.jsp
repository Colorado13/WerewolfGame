<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="WEB-INF/tlds/WwTagLib.tld" prefix="ww"%>
<!DOCTYPE html>
<html>
    <jsp:include page="Header.jsp">
            <jsp:param name="navigation" value ="<li><a href=\"#\">Main Page</a></li>
                    <li><a href=\"#\">MyGames</a></li>
                    <li><a href=\"#\">My Stats</a></li>
                    <li><a href=\"#\">Create Game</a></li>
                    <li><a href=\"#\">Logout</a></li>" />
        </jsp:include>
        <script src="http://ajax.googleapis.com/ajax/libs/jquery/1.3.0/jquery.min.js"></script>
        <script>
            $(document).ready(function () {
                window.setInterval(function () {// set time limit for AJAX call
                    $(function () {
                        $('#GameChatMessages').load('./GetGameChatServlet');
                        $('#DeadChatMessages').load('./GetDeadChatServlet');
                        $('#WwChatMessages').load('./GetWwChatServlet');
                        $('#ShowVotes').load('./ShowVotesServlet');
                        $('#ShowVotesAgainst').load('./ShowVotesAgainstServlet');
                    });
                }, 1000);
            });
        </script>
        
        <

                <h1>Game Page</h1>
                <h2>Welcome ${user.username}</h2>
                <h3>This is Game ${playerInstance.gameId}</h3>
                <h3>Current round is ${playerInstance.currentRound}</h3>
                <h3>In this game you are a ${playerInstance.role}</h3>
                <h3>You are currently ${playerInstance.status}</h3>
            <div class="Container">

            <div class="GameChat">
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
            <div id="SideWindow">

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
                <div id="GameInfo">
                    <div id="Votes">
                        <h2>Votes</h2>
                        <div id="ShowVotesAgainst">
                        </div>
                        <div id="ShowVotes">
                        </div>
                    </div>
                    <div id="Actions">
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
                    <div id="BackButton">
                        <form method="get" action="MainPage.jsp">
                            <input type="submit" value="Back">
                        </form>
                    </div>

                </div>



            </div>
        </div>

    </body>
</html>