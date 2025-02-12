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
<div id="chatBody">

</div>

</body>

<script>
	var socket;
	var roomNumber = ${roomNumber};
	window.onload = function () {
		socket = new WebSocket("ws://chat");
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
				message : roomNumber
			};
	    	socket.send(JSON.stringify(dataTosend));
	        console.log('Connected to WebSocket server');
	    };

	    // WebSocket 연결이 닫혔을 때
	    socket.onclose = () => {
	      console.log('Disconnected from WebSocket server');
	    };
	}
	
	function sendMsg() {
		
	}
	
	
</script>
</html>