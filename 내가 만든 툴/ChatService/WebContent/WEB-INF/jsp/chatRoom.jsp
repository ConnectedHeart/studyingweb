<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/css/chatList.css">
</head>
<body>
<div id="chatBody" class="chatBody">

</div>
<textarea id="chatInput" class="chatInput"></textarea>
<div>
<button id="sendMsgBtn" class="sendMsgBtn btn" onclick="sendMsg()">전송</button>
<button id="chatCloseBtn" class="chatCloseBtn btn" onclick="disconnectChat()">채팅 종료</button>
</div>

</body>

<script>
	var socket;
	var roomNumber = ${roomNumber};
	var userName = "${userName}";
	window.onload = function () {
		socket = new WebSocket("ws://192.168.0.4:8081/chat?roomNumber=" + roomNumber + "&userName=" + userName);

	    // WebSocket이 연결되었을 때
	    socket.onopen = () => {
	        console.log('Connected to WebSocket server');
	    };
	    
	    socket.onmessage = function(event) {
	    	var messageData = JSON.parse(event.data);
	    	
	    	if (messageData.type == 'send') {
		    	var senderName = messageData.userName;
		    	var content = messageData.content;
		    	var chatContainerDiv = document.createElement('div');
		    	chatContainerDiv.classList.add('chatContainer');
		    	var contentDiv = document.createElement('div');
		    	contentDiv.classList.add('subContainer')
		    	var userProfileDiv = document.createElement('div');
		    	userProfileDiv.classList.add('userProfile');
		    	contentDiv.appendChild(userProfileDiv);
		    	
		    	var contentDiv2 = document.createElement('div');
		    	contentDiv2.classList.add('userNameAndContent');
		    	var userNameDiv = document.createElement('div');
		    	userNameDiv.textContent = senderName;
		    	userNameDiv.classList.add('userName');
		    	contentDiv2.appendChild(userNameDiv);
		    	var chatContentDiv = document.createElement('div');
		    	chatContentDiv.textContent = content;
		    	chatContentDiv.classList.add('chatContent');
		    	contentDiv2.appendChild(chatContentDiv);
		    	contentDiv.appendChild(contentDiv2);
		    	chatContainerDiv.appendChild(contentDiv);
		    	/*
		    	"<div class='chatContentDiv'><span class='userProfile'>" + userName + "</span><span class='chatContent'>" + message +"</span></div>"
		    	*/
		   		document.getElementById('chatBody').appendChild(chatContainerDiv);
	    	} else if (messageData.type == 'noti') {
	    		var chatContainerDiv = document.createElement('div');
	    		chatContainerDiv.classList.add('chatContainer');
		    	var chatNotiDiv = document.createElement('div');
		    	chatNotiDiv.classList.add('chatNoti');
		    	chatNotiDiv.textContent = messageData.content;
		    	chatContainerDiv.appendChild(chatNotiDiv);
		    	document.getElementById('chatBody').appendChild(chatContainerDiv);
	    	}
	    	
	    	
	    }

	    // WebSocket 연결이 닫혔을 때
	    socket.onclose = () => {
	    	var chatContainerDiv = document.createElement('div');
	    	chatContainerDiv.classList.add('chatContainer');
	    	var chatNotiDiv = document.createElement('div');
	    	chatNotiDiv.classList.add('chatNoti');
	    	chatNotiDiv.textContent = '채팅을 종료합니다.';
	    	chatContainerDiv.appendChild(chatNotiDiv);
	    	document.getElementById('chatBody').appendChild(chatContainerDiv);
	    	console.log('Disconnected from WebSocket server');
	    };
	    
	    var chatInput = document.getElementById('chatInput');
	    chatInput.addEventListener('keydown', function(event) {
	    	if(event.key === "Enter") {
	    		sendMsg();
	    	}
	    });
	}
	
	function sendMsg() {
		var sendMessage = document.getElementById("chatInput").value;
		var dataToSend = {
			type : 'send',
			message : sendMessage
		}; 
		socket.send(JSON.stringify(dataToSend));
		document.getElementById("chatInput").value = "";
		var chatContainer = document.createElement('div');
		chatContainer.classList.add('chatContainer');
		chatContainer.setAttribute('align', 'right');
		var myMsgSpan = document.createElement('span');
		myMsgSpan.classList.add('myMsg');
		myMsgSpan.textContent = sendMessage;
		chatContainer.appendChild(myMsgSpan);
		document.getElementById('chatBody').appendChild(chatContainer);
	}
	
	function disconnectChat() {
		socket.close();
	}
	
	
</script>
</html>