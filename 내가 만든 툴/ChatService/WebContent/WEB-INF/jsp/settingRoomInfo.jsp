<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>채팅방 생성</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f7f7f7;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            margin: 0;
        }
        .form-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
        }
        .form-container h2 {
            text-align: center;
            margin-bottom: 20px;
            color: #333;
        }
        .form-group {
            margin-bottom: 20px;
        }
        .form-group label {
            display: block;
            font-size: 14px;
            color: #333;
            margin-bottom: 5px;
        }
        .form-group input, .form-group select {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            border: 1px solid #ccc;
            border-radius: 4px;
        }
        .form-group select {
            cursor: pointer;
        }
        .form-group input:focus, .form-group select:focus {
            border-color: #007BFF;
            outline: none;
        }
        .form-group button {
            width: 100%;
            padding: 12px;
            background-color: #007BFF;
            color: white;
            font-size: 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .form-group button:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>

    <div class="form-container">
        <h2>채팅방 만들기</h2>
        <form action="/createChat" method="post">
            <!-- 방 이름 입력 -->
            <div class="form-group">
                <label for="roomName">방 이름</label>
                <input type="text" id="roomName" name="roomName" placeholder="방 이름을 입력하세요" required>
            </div>
            
            <!-- 최대 참여 인원 선택 -->
            <div class="form-group">
                <label for="maxPersonCount">최대 참여 인원</label>
                <select id="maxPersonCount" name="maxPersonCount" required>
                    <option value="">인원 수를 선택하세요</option>
                    <option value="1">1명</option>
                    <option value="2">2명</option>
                    <option value="3">3명</option>
                    <option value="4">4명</option>
                    <option value="5">5명</option>
                    <option value="6">6명</option>
                </select>
            </div>
            
            <!-- 제출 버튼 -->
            <div class="form-group">
                <button type="submit">채팅방 생성</button>
            </div>
        </form>
    </div>

</body>
</html>