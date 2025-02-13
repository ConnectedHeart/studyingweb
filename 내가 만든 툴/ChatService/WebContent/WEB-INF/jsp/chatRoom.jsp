<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<label>채팅 입력 :</label>
<input id="chatInput" type="text"/> <br/>
<button id="sendMsg" onclick="sendMsg()">전송</button>
<button id="sendMsg" onclick="disconnectChat()">채팅 종료</button>
<div id="chatBody">

</div>

</body>

<script>
	var socket;
	var roomNumber = ${roomNumber};
	var userName = "${userName}";
	window.onload = function () {
		socket = new WebSocket("ws://localhost:8081/chat?userName=" + userName + "&roomNumber=" + roomNumber);
		socket.onmessage = (event) => {
	      var message = event.data;
	      var messagesDiv = document.getElementById('messages');
	      var newMessage = document.createElement('div');
	      newMessage.textContent = message;
	      messagesDiv.appendChild(newMessage);
	    };

	    // WebSocket이 연결되었을 때
	    socket.onopen = () => {
			var dataToSend = {
				type : 'join',
				message : roomNumber + "," + userName
			};
	    	socket.send(JSON.stringify(dataToSend));
	        console.log('Connected to WebSocket server');
	    };
	    
	    socket.onmessage = function(event) {
	    	var message = event.data;
	   		document.getElementById('chatBody').innerHTML += "<p>" + message +"</p>";
	    }

	    // WebSocket 연결이 닫혔을 때
	    socket.onclose = () => {
	      console.log('Disconnected from WebSocket server');
	    };
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