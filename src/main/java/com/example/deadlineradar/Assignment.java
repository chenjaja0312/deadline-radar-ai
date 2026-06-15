package com.example.deadlineradar;

import jakarta.validation.constraints.*;
import java.time.LocalDate;

public class Assignment {
    private Long id;
    @NotBlank private String course;
    @NotBlank private String title;
    @NotNull private LocalDate deadline;
    @Min(1) @Max(5) private int difficulty;
    @Min(0) @Max(100) private int progress;
    @Min(1) @Max(10) private int estimatedHours;
    @Min(1) @Max(5) private int mood;
    private double riskScore;
    private String riskLevel;
    private String suggestion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public int getDifficulty() { return difficulty; }
    public void setDifficulty(int difficulty) { this.difficulty = difficulty; }
    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }
    public int getEstimatedHours() { return estimatedHours; }
    public void setEstimatedHours(int estimatedHours) { this.estimatedHours = estimatedHours; }
    public int getMood() { return mood; }
    public void setMood(int mood) { this.mood = mood; }
    public double getRiskScore() { return riskScore; }
    public void setRiskScore(double riskScore) { this.riskScore = riskScore; }
    public String getRiskLevel() { return riskLevel; }
    public void setRiskLevel(String riskLevel) { this.riskLevel = riskLevel; }
    public String getSuggestion() { return suggestion; }
    public void setSuggestion(String suggestion) { this.suggestion = suggestion; }
}
