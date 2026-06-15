package com.example.deadlineradar;

import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Service
public class RiskEngine {
    public Assignment enrich(Assignment a) {
        long daysLeft = ChronoUnit.DAYS.between(LocalDate.now(), a.getDeadline());
        double timePressure = daysLeft < 0 ? 40 : Math.max(0, 35 - daysLeft * 4.0);
        double workPressure = a.getDifficulty() * 8 + a.getEstimatedHours() * 2.5;
        double progressPenalty = (100 - a.getProgress()) * 0.35;
        double moodPenalty = (6 - a.getMood()) * 4.5;
        double score = Math.min(100, Math.max(0, timePressure + workPressure + progressPenalty + moodPenalty - 35));

        a.setRiskScore(Math.round(score * 10.0) / 10.0);
        if (score >= 70) {
            a.setRiskLevel("High");
            a.setSuggestion("今天先完成最小可交付版本：列出 3 個子任務，先做第一個 25 分鐘，晚上再補進度。");
        } else if (score >= 40) {
            a.setRiskLevel("Medium");
            a.setSuggestion("把任務拆成兩段：先完成核心功能，再做美化與補充資料，避免最後一天爆掉。");
        } else {
            a.setRiskLevel("Low");
            a.setSuggestion("目前狀態穩定。建議保留 30 分鐘做檢查、截圖與繳交整理。");
        }
        return a;
    }
}
