# Backend

회원가입

curl -X POST "https://connected-backend-yir6.onrender.com/api/register" -d "username=testuser&password=testpassword&userbirth=2001-01-15"


로그인

curl -X POST "https://connected-backend-yir6.onrender.com/api/login" -d "username=testuser&password=testpassword"


로그아웃

curl -X POST "https://connected-backend-yir6.onrender.com/api/logout" -b "JSESSIONID=<your_session_id>"


댓글 생성

curl -X POST "https://connected-backend-yir6.onrender.com/api/comment/create" -d "videoId=1&userId=1&content=hello"

댓글 좋아요

curl -X POST "http://localhost:8080/api/comment/like" -d "commentId=2"

특정 비디오의 댓글목록

curl -X GET "http://localhost:8080/api/comment/1"
