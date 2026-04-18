# Tổng hợp kiến thức Git

## 1. Git Basics

### Git là gì?
- Hệ thống quản lý phiên bản (Version Control System).
- Theo dõi thay đổi file, cho phép quay lại phiên bản trước.
- Phân tán (Distributed): mỗi developer có bản sao toàn bộ repository.

### Workflow cơ bản
1. git clone: tải repository.
2. git add: đưa file vào staging area.
3. git commit: lưu thay đổi vào local.
4. git push: đẩy thay đổi lên remote (GitHub).
5. git pull: kéo thay đổi từ remote.

## 2. Branching

### Branch là gì?
- Nhánh phát triển độc lập, không ảnh hưởng đến branch khác.
- main/master: branch chính, production-ready.
- develop: branch phát triển chung.
- feature branch: phát triển feature cụ thể.

### Branch Naming Convention
- feature/: tính năng mới (ví dụ: feature/add-login).
- bugfix/: sửa lỗi (ví dụ: bugfix/fix-button).
- hotfix/: sửa cấp tốc (ví dụ: hotfix/fix-crash).
- refactor/: cải tiến code (ví dụ: refactor/optimize-query).

## 3. Merge & Rebase

### Merge
- Tạo commit mới kết hợp hai branch.
- Giữ toàn bộ lịch sử (non-linear).
- An toàn, dễ hiểu lịch sử.

### Rebase
- Chuyển commit từ branch A lên branch B.
- Tạo lịch sử tuyến tính (linear), sạch sẽ hơn.
- Riskier nếu branch đã push.
- Thường dùng locally trước khi merge.

## 4. Commit

### Commit Message
- Ngắn gọn, rõ ràng, mô tả thay đổi.
- Ví dụ: "Add user authentication" thay vì "Update code".
- Quy tắc: "Verb + Object" (ví dụ: Add, Fix, Update, Remove).

### Commit Best Practices
- Commit thường xuyên, mỗi commit = một feature/bugfix.
- Không commit code chưa test.
- Viết message chi tiết nếu cần.

## 5. Remote Repository

### GitHub
- Platform lưu trữ Git repository trên cloud.
- Cho phép collaboration, pull request, issue tracking.
- Miễn phí cho public repository.

### push & pull
- git push: đẩy commit từ local lên remote.
- git pull: kéo commit từ remote về local.

## 6. Conflict & Resolution

### Merge Conflict
- Xảy ra khi hai branch sửa cùng một phần file.
- Git yêu cầu developer chọn phần nào giữ lại.
- Dùng git merge --abort để hủy merge nếu cần.

### Resolve Conflict
- Sửa file, giữ lại phần cần thiết.
- git add file đã fix.
- git commit để hoàn thành merge.

## Liên kết kiến thức
- [[git/interview-questions-git.md]]
- [[git/example-git.md]]
- [[spring-boot/spring-boot.md]] (Workflow project)

