<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="/js/jquery.js"></script>
<link rel="stylesheet" type="text/css" href="/css/chatList.css">
</head>
<body>
<button>새로고침</button>
<form action="/createChat">
<button type="submit">채팅 방 만들기</button>
</form>

채팅 리스트
<table id="chatListTable" border="1" class="chatListTable">
	<colgroup>
		<col style="width:40%">
		<col style="width:20%">
		<col style="width:20%">
		<col style="width:20%">
	</colgroup>
<tr>
	<th>방 이름</th><th>방장</th><th>방 만든 시간</th><th>참석인원/최대 인원</th>
</tr>

</table>

<script>
window.onload= function () {
	 $.ajax({
         url: "/chatRoomList",
         type: "GET",  // HTTP 요청 방식 (GET)
         dataType: "json",  // 응답 데이터 형식
         success: function(result) {
             console.log("result :", result);
        	 if (result.status == "ok") {
        		 var chatRoomMap = result.data;
        		 Object.keys(chatRoomMap).forEach(key => {
        			var chatRoomNum = key;
        			var chatRoomInfo = chatRoomMap[key];
        			
        			var chatRoomTr = document.createElement('tr');
        			var chatRoomTd1 = document.createElement('td');
        			var chatRoomTd2 = document.createElement('td');
        			var chatRoomTd3 = document.createElement('td');
        			var chatRoomTd4 = document.createElement('td');
        			chatRoomTd1.textContent = chatRoomInfo.roomName;
        			chatRoomTd2.textContent = chatRoomInfo.createUserName;
        			chatRoomTd3.textContent = chatRoomInfo.createDate.substring(0, 16);
        			chatRoomTd4.textContent = Object.keys(chatRoomInfo.chatMemberMap).length + "(명)/" + chatRoomInfo.maxPersonCount + "(명)";
        			
        			chatRoomTr.appendChild(chatRoomTd1);
        			chatRoomTr.appendChild(chatRoomTd2);
        			chatRoomTr.appendChild(chatRoomTd3);
        			chatRoomTr.appendChild(chatRoomTd4);
        			chatRoomTr.setAttribute('roomNumber', chatRoomInfo.roomNumber)
        			chatRoomTr.addEventListener('click', function () {
        				joinChat(chatRoomInfo.roomNumber);
        			});
        			
        			var chatListTable = document.getElementById('chatListTable');
        			chatListTable.appendChild(chatRoomTr);
        		 });
        		 
        	 }
         },
         error: function(xhr, status, error) {
        	 console.error(error);
         }
     });
}

function joinChat(roomNumber) {
	var form = document.createElement('form');
	form.action = '/joinChat';
	var input = document.createElement('input');
	input.type = 'hidden';  // 사용자가 볼 수 없도록 hidden으로 설정
	input.name = 'roomNumber';  // 서버에서 받을 파라미터 이름 설정
	input.value = roomNumber;  // 전송할 값 설정

	// 4. 폼에 input 요소 추가
	form.appendChild(input);
	form.method = 'GET';
	document.body.appendChild(form);
	form.submit();
}

</script>

</html>