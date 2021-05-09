<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Insert title here</title>
    <link href="/css/bootstrap.min.css" rel="stylesheet">
    <link href="/css/style.css" rel="stylesheet">
    <script src="/js/jquery.min.js"></script>
    <script src="/js/sockjs.js"></script>
    <script src="/js/stomp.js"></script>
    <script src="/js/script.js"></script>
</head>
<body>
<h1>Simple chat</h1>
<div class="chat_window">
    <div class="top_menu">
        <div class="buttons">
            <div class="button close"></div>
            <div class="button minimize"></div>
            <div class="button maximize"></div>
        </div>
        <div class="title">Chat</div>
    </div>
    <ul class="messages">
        <#list messages as message>
            <li class="message left appeared">
                <div class="avatar"></div>
                <div class="text_wrapper">
                    <span class="from">${message.username} ${message.createdDate}</span>
                    <div class="text">${message.text}</div>
                </div>
            </li>
        </#list>
    </ul>
    <div class="bottom_wrapper clearfix">
        <input id="user_id" hidden/>
        <div class="message_input_wrapper">
            <input id="message_input_value" disabled class="message_input" placeholder="Type your message here..."/>
        </div>
        <div class="send_message">
            <input id="from_input_value" class="message_input" placeholder="Your name"/>
        </div>

        <button id="connect_btn" onclick="connect()">Connect to chat</button>
        <button id="send_btn" onclick="sendMessage()" class="text" disabled>Send</button>
        <button id="disconnect_btn" onclick="disconnect()" disabled>Disconnect from chat</button>
    </div>
</div>
<div id="message_template" class="message_template">
    <li class="message">
        <div class="avatar"></div>
        <div class="text_wrapper">
            <span class="from"></span>
            <div class="text"></div>
        </div>
    </li>
</div>
</body>
</html>