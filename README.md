# Backend

회원가입

curl -X POST "https://connected-backend-yir6.onrender.com/api/register" -d "username=testuser&password=testpassword"


로그인

curl -X POST "https://connected-backend-yir6.onrender.com/api/login" -d "username=testuser&password=testpassword"


로그아웃

curl -X POST "https://connected-backend-yir6.onrender.com/api/logout" -b "JSESSIONID=<your_session_id>"
