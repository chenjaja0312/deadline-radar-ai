# DeadlineRadar AI｜期末任務風險預測系統

## 1. 專題簡介
DeadlineRadar AI 是一個符合「AI Vibe Coding 期末個人實戰挑戰」要求的全端小型系統。使用者可以輸入課程任務、截止日期、難度、完成度、預估時數與目前心情，系統會自動計算風險分數、分類為 High / Medium / Low，並產生具體行動建議。

這個作品適合 3 小時 Hackathon，因為功能完整但範圍不會太大，包含：

- Java 後端：Spring Boot RESTful API
- Database：SQLite 資料庫儲存任務資料
- 前端網頁：HTML / CSS / JavaScript 呼叫 API
- DM / ML：RiskEngine 風險分析模組，做分類與建議生成
- 可部署：Render / Railway / Fly.io 皆可部署

---

## 2. 系統資料流

```text
使用者輸入任務資料
        ↓
HTML / CSS / JavaScript 前端表單
        ↓ fetch()
Spring Boot REST API
        ↓
SQLite Database 儲存任務
        ↓
RiskEngine DM/ML 風險分類模組
        ↓
回傳 JSON 給前端 Dashboard 顯示
```

---

## 3. 功能列表

### 基本 CRUD
- 新增任務
- 查看任務列表
- 刪除任務
- 自動儲存到 SQLite

### Dashboard 分析
- 總任務數
- 平均風險分數
- 最高風險任務
- High / Medium / Low 風險分布

### DM / ML 模組
RiskEngine 會根據以下特徵做風險預測：

| 特徵 | 意義 |
|---|---|
| daysLeft | 距離截止日期剩幾天 |
| difficulty | 任務難度 |
| progress | 目前完成度 |
| estimatedHours | 預估剩餘工作時數 |
| mood | 目前狀態 / 壓力感 |

輸出：
- riskScore：0 到 100 分
- riskLevel：High / Medium / Low
- suggestion：具體行動建議

---

## 4. API 設計

### GET `/api/assignments`
取得所有任務。

### POST `/api/assignments`
新增任務。

範例 JSON：

```json
{
  "course": "計算機概論",
  "title": "Hackathon 全端專題",
  "deadline": "2026-06-20",
  "difficulty": 5,
  "progress": 25,
  "estimatedHours": 8,
  "mood": 3
}
```

### DELETE `/api/assignments/{id}`
刪除指定任務。

### GET `/api/dashboard`
取得統計分析資料。

---

## 5. 本機執行方式

### 需求
- JDK 17+
- Maven

### 指令

```bash
mvn clean package
java -jar target/deadline-radar-ai-0.0.1-SNAPSHOT.jar
```

打開：

```text
http://localhost:8080
```

---

## 6. Render 部署方式

1. 將此專案 push 到 GitHub Public Repository。
2. 到 Render 建立 New Web Service。
3. 連接 GitHub Repository。
4. 設定：

```text
Build Command: mvn clean package
Start Command: java -jar target/deadline-radar-ai-0.0.1-SNAPSHOT.jar
```

5. 等部署完成後，Render 會產生 Live Demo URL。
6. 將該網址貼到 Moodle 的「作品部署連結」。

---

## 7. 繳交資料對照

| 課程要求 | 本專案對應內容 |
|---|---|
| Live Demo URL | 部署到 Render / Railway 後產生 |
| Project README | 本 README.md |
| Prompt 紀錄 | PROMPT_LOG.md |
| GitHub 專案庫 | 上傳此完整專案到 GitHub |
| Java 後端 | Spring Boot |
| Database | SQLite |
| Frontend | static/index.html, style.css, app.js |
| DM / ML | RiskEngine.java |

---

## 8. 專題亮點

這個作品不是單純的待辦清單，而是把「任務資料」轉成「可行動的風險分析」。它符合 Hackathon 評分重點：能清楚說明資料如何從前端流到 API、DB，再回到 AI 分析與商務邏輯。
