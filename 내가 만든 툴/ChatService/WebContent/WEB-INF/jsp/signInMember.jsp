<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원가입</title>
</head>
<body>
	<form action="/signIn" method="post">
		<label for="userName">이름 : </label>
		<input type="text" name="userName"/>
		<label for="gender">성별 : </label>
		<select name="gender">
			<option value="M">남자</option>
			<option value="F">여자</option>
		</select>
		<button type="submit">전송</button>
	</form>
</body>
</html>