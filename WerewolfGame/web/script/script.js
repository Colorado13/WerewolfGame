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
			
function toggle(source) {
	checkboxes = document.getElementsByName('player');
	for (var i = 0, n = checkboxes.length; i < n; i++) {
		checkboxes[i].checked = source.checked;
	}
}