<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="chatBody">

</div>
<label>채팅 입력 :</label>
<input id="chatInput" type="text"/><button id="sendMsgBtn" onclick="sendMsg()">전송</button>
<br/>
<button id="chatCloseBtn" onclick="disconnectChat()">채팅 종료</button>
</body>

<script>
	var socket;
	var roomNumber = ${roomNumber};
	var userName = "${userName}";
	window.onload = function () {
		socket = new WebSocket("ws://192.168.0.4:8081/chat?roomNumber=" + roomNumber + "&userName=" + userName);
		socket.onmessage = (event) => {
	      var message = event.data;
	      var messagesDiv = document.getElementById('messages');
	      var newMessage = document.createElement('div');
	      newMessage.textContent = message;
	      messagesDiv.appendChild(newMessage);
	    };

	    // WebSocket이 연결되었을 때
	    socket.onopen = () => {
	        console.log('Connected to WebSocket server');
	    };
	    
	    socket.onmessage = function(event) {
	    	var message = event.data;
	   		document.getElementById('chatBody').innerHTML += "<p>" + message +"</p>";
	    }

	    // WebSocket 연결이 닫혔을 때
	    socket.onclose = () => {
	    	document.getElementById('chatBody').innerHTML += "<p>채팅을 종료합니다.</p>";
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
	}
	
	function disconnectChat() {
		socket.close();
	}
	
	
</script>
</html>