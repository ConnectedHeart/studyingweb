<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/chatList.css">
    <script src="/js/test.js"></script>
    <title>이름과 성별 선택 폼</title>
</head>
<body>
	<div class="signInArea">
	    <div class="form-container">
	        <h2>회원가입</h2>
	        <form action="/signIn" method="post">
	            <div class="form-group">
	                <label for="name">이름</label>
	                <input type="text" id="name" name="userName" placeholder="이름을 입력하세요" required>
	            </div>
	            <div class="form-group">
	                <label for="gender">성별</label>
	                <select id="gender" name="gender" required>
	                    <option value="">성별을 선택하세요</option>
	                    <option value="M">남자</option>
	                    <option value="F">여자</option>
	                </select>
	            </div>
	            <div class="form-group">
	                <button type="submit">제출</button>
	            </div>
	        </form>
	    </div>
	</div>
</body>
</html>