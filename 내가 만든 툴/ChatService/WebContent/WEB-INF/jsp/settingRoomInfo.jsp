<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>채팅 방 생성</title>
</head>
<body>
	<form action="/createChat" method="post">
		<label for="roomName">방 이름 : </label>
		<input type="text" name="roomName"/>
		<label for="maxPersonCount">성별 : </label>
		<select name="maxPersonCount">
			<option value="1">1명</option>
			<option value="2">2명</option>
			<option value="3">3명</option>
			<option value="4">4명</option>
			<option value="5">5명</option>
			<option value="6">6명</option>
		</select>
		<button type="submit">전송</button>
	</form>
</body>
</html>