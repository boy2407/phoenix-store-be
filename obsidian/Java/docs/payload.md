# Tổng hợp kiến thức về payload, request/response

File này tổng hợp các quy tắc và ví dụ về việc tạo payload cho các entity, đặc biệt là Category.

## 1. Quy tắc tạo payload
- Mỗi entity có các payload request/response riêng
- Không dùng chung payload giữa các entity
- Đặt tên rõ ràng: <Entity>CreateRequest, <Entity>UpdateRequest, <Entity>Response

## 2. Ví dụ cho Category
- CategoryCreateRequest
- CategoryUpdateRequest
- CategoryResponse

## 3. Quy tắc áp dụng vào controller/service
- Controller nhận payload request, trả về response
- Service xử lý logic nghiệp vụ

## 4. Các câu hỏi đã hỏi
- Có cần tuân thủ tạo payload cho các request của category không?
- Tạo các payload cho category tuân thủ nguyên tắc, tạo package phân chia rõ ràng
- Áp dụng vào controller
- Sửa lại service
- ... (bổ sung các câu hỏi khác khi có)

