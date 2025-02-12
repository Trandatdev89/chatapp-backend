Cách sử dụng dự án local như sau:

Backend
Yêu cầu: Postman,IDE (intel,eclipse...),MySQL,JDK
Trong file application.property hãy đổi url front-end thành http://localhost:3000 tại biến "FRONT_END.URI"

Frontend
Yêu cầu: Vscode,npm...
Chạy lệnh npm install ,chạy lệnh npm start để chạy dư án front-end
Link URL backend đuược để trong file RequestAPI và RequestAPIToken ở thu mục utils hãy bên phía front end,hãy đối đường lại đường dẫn thành http://localhost:8080/

Mô tả cơ bản về dự án:

+) Khi login thành công mà header chưa kịp cập nhập hãy reload lại trang web,bạn có thể đăng nhập với google hoặc đăng ký tài khoản

+) Có 2 chức năng chính đó là nhăn tin trong nhóm và nhắn với cá nhân(User)

+) Khi đăng ký thì tất cả sẽ được gắn role là MEMBER

+)Nhóm có thể thêm thành viên ,sửa xóa nhóm và mỗi member thì đều có thể rời nhóm

+) có thể xem lại info và chỉnh sưa upload ảnh lại cập nhập


