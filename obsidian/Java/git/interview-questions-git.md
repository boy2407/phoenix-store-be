# Câu hỏi phỏng vấn Git

## 1. Git merge vs rebase?

**Câu trả lời ngắn gọn:**
- **merge**: Tạo commit mới kết hợp hai branch, giữ lịch sử của cả hai (non-linear history).
- **rebase**: Chuyển các commit từ branch hiện tại lên branch gốc, tạo lịch sử tuyến tính (linear history).

**Giải thích chi tiết:**
- **Merge**:
  - Tạo một "merge commit" kết hợp hai branch.
  - Giữ toàn bộ lịch sử commit của cả hai branch.
  - Git history không tuyến tính (non-linear).
  - An toàn hơn, không thay đổi lịch sử.
  - Tốt cho collaboration (nhiều người làm việc).

- **Rebase**:
  - Chuyển các commit từ branch hiện tại (ví dụ: feature) lên trên branch gốc (ví dụ: main).
  - Tạo lịch sử tuyến tính, sạch sẽ hơn.
  - Thay đổi commit hash (vì rebase tạo commit mới).
  - Riskier nếu branch đã được push (không nên rebase shared branch).
  - Tốt cho local development, làm sạch lịch sử trước merge.

**Ví dụ cụ thể:**
```bash
# Tình huống: có main branch và feature branch

# Trước merge/rebase
main:    A --- B --- C
                      \
feature:               D --- E

# Sau merge (git merge feature)
main:    A --- B --- C --- M (merge commit)
                      \   /
feature:               D-E

# Sau rebase (git rebase main, sau đó merge)
main:    A --- B --- C --- D' --- E' (linear history)
feature:                   D' --- E'
```

**Lệnh sử dụng:**
```bash
# 1. Merge
git checkout main
git merge feature
# Kết quả: main chứa toàn bộ commit từ feature, bao gồm merge commit

# 2. Rebase (trước khi merge để làm sạch lịch sử)
git checkout feature
git rebase main
# Kết quả: commit D, E được chuyển lên trên C (tạo D', E')

# 3. Rebase interactive (chỉnh sửa lịch sử)
git rebase -i HEAD~3
# Cho phép squash, reorder, edit các commit gần nhất

# 4. Merge sau rebase
git checkout main
git merge feature
# Kết quả: main có D', E', fast-forward merge (không cần merge commit)
```

**Liên kết kiến thức:**
- [[git/git.md]]
- [[git/example-git.md]]

**Mẹo/Lưu ý:**
- Dùng rebase cho local branch, merge cho shared branch (để không làm xáo trộn lịch sử người khác).
- Không nên rebase trên commit đã push (trừ khi đó là feature branch và bạn chắc chắn).
- `git rebase -i` để squash nhiều commit thành một (hữu ích trước merge).
- Sau rebase, có thể dùng fast-forward merge để giữ lịch sử sạch sẽ.
- Khi conflict, giải quyết, sau đó `git rebase --continue` (khác merge là `git merge --continue`).

---

## 2. Cách tạo branch mới?

**Câu trả lời ngắn gọn:**
- Dùng `git branch <tên-branch>` để tạo, hoặc `git checkout -b <tên-branch>` để tạo và chuyển sang branch mới ngay.

**Giải thích chi tiết:**
- **git branch**: Liệt kê, tạo, xoá, đổi tên branch.
  - `git branch`: Liệt kê tất cả local branch.
  - `git branch <tên>`: Tạo branch mới từ branch hiện tại.
  - `git branch -d <tên>`: Xoá branch (an toàn, yêu cầu đã merged).
  - `git branch -D <tên>`: Xoá branch (bắt buộc, không cần merged).
  - `git branch -m <tên-cũ> <tên-mới>`: Đổi tên branch.

- **git checkout**: Chuyển đổi branch.
  - `git checkout <tên-branch>`: Chuyển sang branch.
  - `git checkout -b <tên>`: Tạo và chuyển sang branch mới (tương đương: git branch + git checkout).

- **git switch** (Git 2.23+): Phiên bản mới hơn của checkout.
  - `git switch <tên-branch>`: Chuyển sang branch.
  - `git switch -c <tên>`: Tạo và chuyển sang branch mới.

**Ví dụ cụ thể:**
```bash
# 1. Tạo branch từ main
git checkout main
git branch feature/add-category
git checkout feature/add-category
# hoặc gộp thành một lệnh
git checkout -b feature/add-category

# 2. Kiểm tra branch hiện tại
git branch
# Kết quả:
# * feature/add-category
#   main
#   develop

# 3. Tạo branch từ commit cụ thể
git branch hotfix/fix-bug abc123def456

# 4. Tạo tracking branch (liên kết với remote)
git checkout -b feature/from-remote origin/feature/remote-branch

# 5. Xoá branch
git branch -d feature/add-category # An toàn (yêu cầu merged)
git branch -D feature/add-category # Bắt buộc (không kiểm tra merged)

# 6. Đổi tên branch
git branch -m feature/add-category feature/create-category

# 7. Đẩy branch lên remote
git push origin feature/add-category

# 8. Xoá branch trên remote
git push origin --delete feature/add-category
# hoặc
git push origin :feature/add-category

# 9. Lấy branch từ remote
git checkout --track origin/feature/remote-branch
# hoặc (Git 2.23+)
git switch --create feature/local-name origin/feature/remote-name
```

**Quy tắc đặt tên branch:**
- `feature/<tên>`: Tính năng mới (ví dụ: feature/add-category, feature/user-auth).
- `bugfix/<tên>`: Sửa lỗi (ví dụ: bugfix/fix-login-error).
- `hotfix/<tên>`: Sửa cấp tốc production (ví dụ: hotfix/payment-bug).
- `refactor/<tên>`: Cải tiến code (ví dụ: refactor/optimize-query).
- `docs/<tên>`: Tài liệu (ví dụ: docs/api-guide).

**Liên kết kiến thức:**
- [[git/git.md]]
- [[git/example-git.md]]

**Mẹo/Lưu ý:**
- Luôn tạo branch từ main/develop, không từ branch khác (trừ hotfix từ main).
- Xoá branch local sau khi merge xong để tránh nhầm lẫn.
- Dùng `git branch -vv` để xem tracking branch.
- Naming convention rõ ràng giúp dễ hiểu intent của branch.
- Trước khi xoá, chắc chắn rằng đã merge vào branch main hoặc đã backup.

