# Prompt 紀錄｜AI Chat History

> 以下是可以放進繳交資料的 Prompt 紀錄範例。正式比賽時，可以把你實際跟 ChatGPT / Cursor / Gemini 的對話補在下面。

---

## Prompt 1：需求分析
我有一個 3 小時 AI Vibe Coding Hackathon，要求做出 Java 後端、Database、前端網頁、DM/ML 功能整合的全端系統。請幫我設計一個範圍不要太大，但可以完整展示資料流與 AI 分析功能的專題。

## AI 回覆摘要
建議製作 DeadlineRadar AI：期末任務風險預測系統。使用者輸入任務、截止日期、完成度、難度與心情，系統自動計算風險分數、分類 High / Medium / Low，並給出行動建議。

---

## Prompt 2：系統架構
請幫我規劃這個專題的系統架構，要符合 Frontend → Java API → Database → DM/ML 模組 → Dashboard 的資料流。

## AI 回覆摘要
使用 HTML/CSS/JS 做前端，透過 fetch 呼叫 Spring Boot REST API。後端用 SQLite 儲存 assignments，並透過 RiskEngine 進行風險分析，最後回傳 JSON 給 Dashboard 顯示。

---

## Prompt 3：後端開發
請產生 Spring Boot 專案的後端程式碼，需要 Assignment model、Repository、Controller、RiskEngine，並提供 GET、POST、DELETE、Dashboard API。

## AI 回覆摘要
建立 Spring Boot + JdbcTemplate + SQLite 專案，包含 Assignment.java、AssignmentRepository.java、AssignmentController.java、RiskEngine.java。

---

## Prompt 4：前端開發
請幫我寫一個簡潔好看的前端頁面，能新增任務、顯示風險分析結果、Dashboard 統計資料，並串接後端 API。

## AI 回覆摘要
建立 index.html、style.css、app.js。前端使用 fetch 呼叫 /api/assignments 與 /api/dashboard，並以卡片方式顯示分析結果。

---

## Prompt 5：除錯與部署
請幫我確認這個專案如何部署到 Render，並寫出 README 的本機執行方式與部署方式。

## AI 回覆摘要
Render 設定 Build Command 為 mvn clean package，Start Command 為 java -jar target/deadline-radar-ai-0.0.1-SNAPSHOT.jar。README 補上 API、資料流、部署說明與繳交資料對照。
