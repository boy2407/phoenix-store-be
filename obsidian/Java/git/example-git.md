# Ví dụ Git

## 1. Setup & Initial Commit

### Clone Repository
```bash
git clone https://github.com/user/project.git
cd project
```

### Initialize New Repository
```bash
git init
git add .
git commit -m "Initial commit"
git remote add origin https://github.com/user/project.git
git push -u origin main
```

## 2. Branching

### Create & Switch Branch
```bash
# Tạo branch mới
git branch feature/add-login

# Chuyển sang branch
git checkout feature/add-login

# Hoặc tạo & chuyển cùng lúc
git checkout -b feature/add-login

# Liệt kê branch
git branch
git branch -r # Xem remote branch
```

### Push Branch lên Remote
```bash
# Đẩy branch lên GitHub
git push origin feature/add-login

# Thiết lập tracking branch
git push -u origin feature/add-login

# Lần sau: git push sẽ tự động push lên origin/feature/add-login
```

## 3. Commit

### Make Commits
```bash
# Sửa file, kiểm tra trạng thái
git status

# Thêm file vào staging area
git add src/main/java/User.java
git add . # Thêm tất cả

# Commit
git commit -m "Add user authentication"

# Commit + add cùng lúc
git commit -am "Fix user login bug"

# Xem commit history
git log
git log --oneline # Ngắn gọn
git log --graph --all --decorate # Hiển thị graph
```

## 4. Merge

### Merge Branch
```bash
# Checkout sang main
git checkout main

# Pull latest từ remote
git pull origin main

# Merge feature branch vào main
git merge feature/add-login

# Đẩy lên remote
git push origin main

# Xoá branch sau khi merge
git branch -d feature/add-login
git push origin --delete feature/add-login
```

### Merge Conflict
```bash
# Khi conflict xảy ra, git sẽ báo
# Sửa file conflict, chọn phần cần giữ

# Sau khi sửa
git add src/main/java/User.java
git commit -m "Resolve merge conflict"

# Hoặc hủy merge
git merge --abort
```

## 5. Rebase

### Rebase Local Branch
```bash
# Checkout feature branch
git checkout feature/add-login

# Rebase trên main
git rebase main

# Nếu có conflict, giải quyết rồi
git add .
git rebase --continue

# Hoặc hủy
git rebase --abort
```

### Interactive Rebase
```bash
# Squash, reorder các commit gần nhất
git rebase -i HEAD~3

# Thay "pick" thành "squash" (s) để merge commit

# VD:
# pick abc123 Add user model
# s def456 Add user repository
# s ghi789 Add user service

# Kết quả: 3 commit → 1 commit
```

## 6. Pull Request (GitHub)

### Workflow
```bash
# 1. Tạo feature branch
git checkout -b feature/add-category

# 2. Commit & push
git commit -m "Add category model"
git push origin feature/add-category

# 3. Trên GitHub: tạo Pull Request
# - Base: main, Compare: feature/add-category
# - Mô tả PR, request review

# 4. Review, approve, merge
# - Đội review code, comment
# - Fix feedback nếu cần: git push (tự động update PR)
# - Merge PR

# 5. Delete branch
git branch -d feature/add-category
```

## 7. Useful Commands

### Stash
```bash
# Lưu thay đổi tạm thời
git stash

# Áp dụng thay đổi lại
git stash pop

# Liệt kê stash
git stash list
```

### Tag (Release)
```bash
# Tạo tag
git tag v1.0.0

# Push tag lên remote
git push origin v1.0.0

# Liệt kê tag
git tag
```

### Cherry-pick
```bash
# Áp dụng commit từ branch khác
git cherry-pick abc123

# Hữu ích khi chỉ cần một commit cụ thể
```

### Undo
```bash
# Undo commit cuối cùng (giữ changes)
git reset --soft HEAD~1

# Undo commit + changes
git reset --hard HEAD~1

# Undo push (cảnh báo: rewrite history)
git push origin --force-with-lease

# Revert commit (tạo commit mới undo thay đổi)
git revert abc123
```

## Liên kết kiến thức
- [[git/git.md]]
- [[git/interview-questions-git.md]]
- [[spring-boot/spring-boot.md]] (Project workflow)

