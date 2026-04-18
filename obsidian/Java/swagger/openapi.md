# OpenAPI là gì? Tại sao phải dùng?

## Câu trả lời ngắn gọn
- OpenAPI là một chuẩn mô tả API RESTful dưới dạng file (JSON/YAML), giúp tự động sinh tài liệu, kiểm thử, và tích hợp giữa các hệ thống.

## Giải thích chi tiết
- OpenAPI Specification (OAS, trước đây là Swagger Specification) là chuẩn định nghĩa API phổ biến nhất hiện nay.
- Cho phép mô tả endpoint, method, request, response, model, security, ...
- Dùng OpenAPI, bạn có thể tự động sinh ra tài liệu API (Swagger UI), client SDK, server stub, kiểm thử tự động, ...
- Hỗ trợ nhiều ngôn ngữ và framework (Java, Node.js, Python, ...).
- Được hỗ trợ bởi nhiều công cụ: Swagger UI, Swagger Editor, Postman, ...

## Mục đích sử dụng
- **Tự động hóa tài liệu API**: Không cần viết tay, luôn đồng bộ với code.
- **Dễ dàng kiểm thử**: Swagger UI cho phép thử API trực tiếp trên trình duyệt.
- **Tích hợp hệ thống**: Các team frontend, mobile, đối tác có thể generate client SDK từ file OpenAPI.
- **Chuẩn hóa giao tiếp**: Đảm bảo API rõ ràng, nhất quán, dễ bảo trì.
- **Tạo server stub**: Sinh code server mẫu từ file OpenAPI.

## Ví dụ cụ thể
```yaml
openapi: 3.0.0
info:
  title: Phoenix Store API
  version: 1.0.0
paths:
  /products:
    get:
      summary: Lấy danh sách sản phẩm
      responses:
        '200':
          description: Thành công
          content:
            application/json:
              schema:
                type: array
                items:
                  $ref: '#/components/schemas/Product'
components:
  schemas:
    Product:
      type: object
      properties:
        id:
          type: integer
        name:
          type: string
```

## Liên kết kiến thức
- [[swagger/swagger.md]]
- [[spring-boot/spring-boot.md]] (Cấu hình Swagger)

## Mẹo/Lưu ý
- Spring Boot dùng springdoc-openapi để tự động sinh file OpenAPI từ code.
- Để xem tài liệu: truy cập /swagger-ui.html hoặc /swagger-ui/index.html.
- Có thể export file openapi.json để chia sẻ cho các team khác.

