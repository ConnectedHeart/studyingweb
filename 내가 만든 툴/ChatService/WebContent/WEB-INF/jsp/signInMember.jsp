<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="/css/chatList.css">
    <script src="/js/jquery.js"></script>
    <title>이름과 성별 선택 폼</title>
</head>
<body>
	<div class="signInArea">
	    <div class="form-container">
	        <h2>회원가입</h2>
	        <form id="signInForm" action="/signIn" method="post">
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
	                <input type="button" id="submitBtn" value="제출" onclick="checkValidName();">
	            </div>
	        </form>
	    </div>
	</div>
</body>
<script>
function checkValidName() {
	var inputName = document.getElementById('name').value;
	$.ajax({
        url: '/checkValidName', // 서버의 URL
        data:{
			inputName : inputName
        },
        type: 'GET', // 요청 방식
        success: function(response) {
            if (response == 'exist') {
				alert('이미 존재하는 이름입니다. 다시 입력해주세요.');
			} else {
				document.getElementById('signInForm').submit();
			}
        },
        error: function(xhr, status, error) {
            console.log('오류 발생:', error); // 오류 처리
        }
    });
}
</script>

</html>