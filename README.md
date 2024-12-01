# Backend

User

회원가입

curl -X POST "https://connected-backend-yir6.onrender.com/api/register" -d "username=testuser&password=testpassword&userbirth=2001-01-15"

로그인

curl -X POST "https://connected-backend-yir6.onrender.com/api/login" -d "username=testuser&password=testpassword"

로그아웃

curl -X POST "https://connected-backend-yir6.onrender.com/api/logout" -b "JSESSIONID=<your_session_id>"

//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
Comment

댓글 생성

curl -X POST "https://connected-backend-yir6.onrender.com/api/comment/create" -d "videoId={videoId}&userId={userId}&content={content}"

댓글 좋아요

curl -X POST "http://localhost:8080/api/comment/like" -d "commentId={commentId}"

댓글 싫어요

curl -X POST "http://localhost:8080/api/comment/dislike" -d "commentId={commentId}"

특정 비디오의 댓글목록(pagination)

curl -X GET "http://localhost:8080/api/comment/{videoId}?page={page번호}&size={page당 댓글 수}"
