# Quy tắc đặt tên, cấu trúc project và tuân thủ code

## 1. Quy tắc đặt tên branch
- Tính năng mới: feature/<ten-chuc-nang>
- Sửa lỗi: fix/<ten-loi>
- Refactor: refactor/<ten-chuc-nang>

## 2. Quy tắc đặt tên repository, package, class
- repository: <Entity>TênRepository (vd: CategoryRepository)
- service: <Entity>TênService (vd: CategoryService)
- controller: <Entity>TênController (vd: CategoryController)
- payload: phân chia rõ ràng request/response cho từng entity

## 3. Quy tắc commit
- Viết ngắn gọn, rõ ràng, tiếng Anh
- Ví dụ: Add CategoryRepository, Implement create category API

## 4. Quy tắc tạo payload
- Mỗi entity có các payload request/response riêng
- Không dùng chung payload giữa các entity
- Đặt tên rõ ràng: <Entity>CreateRequest, <Entity>UpdateRequest, <Entity>Response

## 5. Quy tắc controller/service
- Controller chỉ nhận request, trả response, không xử lý logic
- Service xử lý logic nghiệp vụ

## 6. Quy tắc exception
- Trả về ResponseEntity với mã lỗi và message rõ ràng

## 7. Quy tắc phân chia package
- controller, service, repository, entity, payload, config, ...

## 8. Quy tắc khác
- Viết code rõ ràng, dễ đọc, dễ bảo trì
- Comment khi cần thiết

