
package ca.werewolfgame.beans;

public class Vote implements java.io.Serializable {
    
    private int gameId;
    private String votingId;
    private String votedId;
    private int round;
    private int voteIndex;

    public Vote() {
    }

    public Vote(int gameId, String votingId, String votedId, int round, int voteIndex) {
        this.gameId = gameId;
        this.votingId = votingId;
        this.votedId = votedId;
        this.round = round;
        this.voteIndex = voteIndex;
    }

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public String getVotingId() {
        return votingId;
    }

    public void setVotingId(String votingId) {
        this.votingId = votingId;
    }

    public String getVotedId() {
        return votedId;
    }

    public void setVotedId(String votedId) {
        this.votedId = votedId;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public int getVoteIndex() {
        return voteIndex;
    }

    public void setVoteIndex(int voteIndex) {
        this.voteIndex = voteIndex;
    }
}

