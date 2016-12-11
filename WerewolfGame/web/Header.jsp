
    <head>
        <meta charset="utf-8" />
        <title>Werewolf The Game</title>
        <link rel="icon" type="image/png" href="Images/favicon.jpg">
        <!-- <link rel="stylesheet" href="css/main.css" type="text/css" /> -->
        <style>
            @import url("reset.css");
            @import url("global-forms.css");
            body {
                background: black ;
                color: #000305;
                font-size: 87.5%;
                font-family: 'Trebuchet MS', Trebuchet, 'Lucida Sans Unicode', 'Lucida Grande', 'Lucida Sans', Arial, sans-serif;
                line-height: 1.429;
                margin: 0;
                padding: 0;
                text-align: left;
            }
            h2 {font-size: 1.571em} 
            h3 {font-size: 1.429em} 
            h4 {font-size: 1.286em} 
            h5 {font-size: 1.143em} 
            h6 {font-size: 1em}

            h2, h3, h4, h5, h6 {
                font-weight: 400;
                line-height: 1.1;
                margin-bottom: .8em;
            }
            a:hover, a:active {
                background-color: #C74350;
                color: #fff;
                text-decoration: none;
                text-shadow: 1px 1px 1px #333;
            }
            strong, b {
                font-weight: bold;
            }
            ul {
                list-style: outside disc;
                margin: 1em 0 1.5em 1.5em;
            }
            header h1 {
                font-size: 3.571em;
                line-height: .6;
                color:red;
                display: flex;
                justify-content: center;
            }
            header image {
                float:left;
            }
            nav h1 a:link, nav h1 a:visited {
                color: #000305;
                display: block;
                font-weight: bold;
                margin: 0 0 .6em .2em;
                text-decoration: none;
                width: 427px;
                float: right;
                color: red;
            }
            nav h1 a:hover, h1 a:active {
                background: none;
                color: #C74350;
                text-shadow: none;
            }
            h1 strong {
                font-size: 0.36em; 
                font-weight: normal;
            }

            nav {
                background: #000305;
                font-size: 1.143em;
                height: 40px;
                line-height: 30px;
                margin: 0 auto 2em auto;
                padding: 0;
                text-align: center;
                width: 800px;

                border-radius: 5px;
                -moz-border-radius: 5px;
                -webkit-border-radius: 5px;
            }
            nav ul {list-style: none; margin: 0 auto; width: 800px;}
            nav li {float: left; display: inline; margin: 0;}

            nav a:link, nav a:visited {
                color: #fff;
                display: inline-block;
                height: 30px;
                padding: 5px 1.5em;
                text-decoration: none;
            }
            nav a:hover, nav a:active,
            nav .active a:link, #banner nav .active a:visited {
                background: #C74451;
                color: #fff;
                text-shadow: none !important;
            }

            nav li:first-child a {
                border-top-left-radius: 5px;
                -moz-border-radius-topleft: 5px;
                -webkit-border-top-left-radius: 5px;

                border-bottom-left-radius: 5px;
                -moz-border-radius-bottomleft: 5px;
                -webkit-border-bottom-left-radius: 5px;
            }
            nav li:last-child a {
                border-top-right-radius: 5px;
                -moz-border-radius-topright: 5px;
                -webkit-border-top-right-radius: 5px;

                border-bottom-right-radius: 5px;
                -moz-border-radius-bottomright: 5px;
                -webkit-border-bottom-right-radius: 5px;
            }
            
            #MainDiv {
                color:white;
            }
            div.Container {
                width: 100%;
                border: 1px solid gray;
            }
            
            .GameChat {
                display: inline-block;
                float:left;
                width: 50%;
            }
            #GameChatMessages {
                background-color:yellow;
            }
            #GameChatInput {
                background-color:green;
            }
            #SideWindow {
                display: inline-block;
                float: right;
                width: 50%;
            }
            #SideChat {
                display: inline-block;
                float: left;
                width: 50%;
            }
            #DeadChatMessages {
                background-color:gray;
            }
            #WwChatMessages {
                background-color:pink;
            }
            #SideChatInput {
                background-color: #66ff66;
            }
            #GameInfo {
                display:block;
                float: right;
                width: 50%;
            }
            #Votes {
                background-color:red;
                display: block;
            }
            #ShowVotesAgainst {
                background-color:blue;
                display: block;
                width: 100%;
            }
            #ShowVotes {
                background-color:orange;
                display: block;
                width: 100%;
            }            
            #Actions {
                position: relative;
                bottom: 0;
                left: 0;
            }
            #VoteForm {
                background-color:brown;
            }
            #KillOrderForm {
                background-color:purple;
            }
        </style>

    </head>

    <body>
        <header>
            <img src="Images/Werewolf.jpg" alt="Werewolf Image" class="photo" />
            <h1>Werewolf The Game</h1><br>
            <h1><strong> Java 3 Project by Jamie and Joao</strong></h1>

            <nav><ul>
                    ${param.navigation}
                </ul>
            </nav>
        </header>