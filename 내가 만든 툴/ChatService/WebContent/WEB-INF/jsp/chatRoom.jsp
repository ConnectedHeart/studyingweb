<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/css/chatList.css">
<script src="/js/jquery.js"></script>
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
		var validRoomFlag = checkValidRoom(roomNumber);
		
		if (!validRoomFlag) {
			alert('삭제된 방입니다.');
			closeChat();
			document.getElementById('chatInput').setAttribute('disabled', 'disabled');
			document.getElementById('chatInput').setAttribute('disabled', 'disabled');
			document.getElementById('sendMsgBtn').setAttribute('disabled', 'disabled');
			document.getElementById('chatCloseBtn').style.display = 'none';
			return;
		}
		
		webSocketSetting();
		eventSettingForChatRoom();
	}
	
	function webSocketSetting() {
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
		   		scrollToBottom();
	    	} else if (messageData.type == 'noti') {
	    		var chatContainerDiv = document.createElement('div');
	    		chatContainerDiv.classList.add('chatContainer');
		    	var chatNotiDiv = document.createElement('div');
		    	chatNotiDiv.classList.add('chatNoti');
		    	chatNotiDiv.textContent = messageData.content;
		    	chatContainerDiv.appendChild(chatNotiDiv);
		    	document.getElementById('chatBody').appendChild(chatContainerDiv);
		    	scrollToBottom();
	    	}
	    	
	    	
	    }

	    // WebSocket 연결이 닫혔을 때
	    socket.onclose = () => {
	    	closeChat();
	    	console.log('Disconnected from WebSocket server');
	    };
	}
	
	function eventSettingForChatRoom() {
		var chatInput = document.getElementById('chatInput');
	    chatInput.addEventListener('keydown', function(event) {
	    	if(event.key === "Enter") {
	    		sendMsg();
	    	}
	    });
	} 
	
	function closeChat() {
		var chatContainerDiv = document.createElement('div');
    	chatContainerDiv.classList.add('chatContainer');
    	var chatNotiDiv = document.createElement('div');
    	chatNotiDiv.classList.add('chatNoti');
    	chatNotiDiv.textContent = '채팅을 종료합니다.';
    	chatContainerDiv.appendChild(chatNotiDiv);
    	document.getElementById('chatBody').appendChild(chatContainerDiv);
    	scrollToBottom();
	}
	
	function sendMsg() {
		var validRoomFlag = checkValidRoom(roomNumber);
		
		if (!validRoomFlag) {
			alert('삭제된 방입니다.');
			document.getElementById('chatInput').setAttirubte('disabled') = 'disabled';
			document.getElementById('chatInput').setAttirubte('disabled') = 'disabled';
			document.getElementById('sendMsgBtn').setAttirubte('disabled') = 'disabled';
			document.getElementById('chatCloseBtn').style.display = 'none';
			return;
		}
		
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
		scrollToBottom();
	}
	
	function disconnectChat() {
		socket.close();
	}
	
	function scrollToBottom() {
		document.getElementById('chatBody').scrollTop = document.getElementById('chatBody').scrollHeight;
	}
	
	function checkValidRoom(roomNumber) {
		$.ajax({
            url: '/checkValidRoom', // 서버의 URL
            data:{
				roomNumber : roomNumber
            },
            type: 'GET', // 요청 방식
            async: false,
            success: function(response) {
                if (response == 'exist') {
					return true;
				} else {
					return false;					
				}
            },
            error: function(xhr, status, error) {
                console.log('오류 발생:', error); // 오류 처리
            }
        });
	}
</script>
</html>