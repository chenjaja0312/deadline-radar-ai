package com.example.deadlineradar;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import jakarta.annotation.PostConstruct;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import java.util.List;

@Repository
public class AssignmentRepository {
    private final JdbcTemplate jdbc;
    private final RiskEngine riskEngine;

    public AssignmentRepository(JdbcTemplate jdbc, RiskEngine riskEngine) {
        this.jdbc = jdbc;
        this.riskEngine = riskEngine;
    }

    @PostConstruct
    public void init() {
        jdbc.execute("""
            CREATE TABLE IF NOT EXISTS assignments (
              id INTEGER PRIMARY KEY AUTOINCREMENT,
              course TEXT NOT NULL,
              title TEXT NOT NULL,
              deadline TEXT NOT NULL,
              difficulty INTEGER NOT NULL,
              progress INTEGER NOT NULL,
              estimated_hours INTEGER NOT NULL,
              mood INTEGER NOT NULL
            )
        """);
        Integer count = jdbc.queryForObject("SELECT COUNT(*) FROM assignments", Integer.class);
        if (count != null && count == 0) {
            jdbc.update("INSERT INTO assignments(course,title,deadline,difficulty,progress,estimated_hours,mood) VALUES(?,?,?,?,?,?,?)",
                    "計算機概論", "Hackathon 全端作品部署", java.time.LocalDate.now().plusDays(2).toString(), 5, 25, 8, 3);
            jdbc.update("INSERT INTO assignments(course,title,deadline,difficulty,progress,estimated_hours,mood) VALUES(?,?,?,?,?,?,?)",
                    "普通物理", "近代物理觀念整理", java.time.LocalDate.now().plusDays(5).toString(), 3, 55, 4, 4);
            jdbc.update("INSERT INTO assignments(course,title,deadline,difficulty,progress,estimated_hours,mood) VALUES(?,?,?,?,?,?,?)",
                    "英文", "單字練習與複習", java.time.LocalDate.now().plusDays(8).toString(), 2, 80, 2, 5);
        }
    }

    private final RowMapper<Assignment> mapper = new RowMapper<>() {
        @Override
        public Assignment mapRow(ResultSet rs, int rowNum) throws SQLException {
            Assignment a = new Assignment();
            a.setId(rs.getLong("id"));
            a.setCourse(rs.getString("course"));
            a.setTitle(rs.getString("title"));
            a.setDeadline(java.time.LocalDate.parse(rs.getString("deadline")));
            a.setDifficulty(rs.getInt("difficulty"));
            a.setProgress(rs.getInt("progress"));
            a.setEstimatedHours(rs.getInt("estimated_hours"));
            a.setMood(rs.getInt("mood"));
            return riskEngine.enrich(a);
        }
    };

    public List<Assignment> findAll() {
        return jdbc.query("SELECT * FROM assignments ORDER BY deadline ASC", mapper);
    }

    public Assignment create(Assignment a) {
        jdbc.update("INSERT INTO assignments(course,title,deadline,difficulty,progress,estimated_hours,mood) VALUES(?,?,?,?,?,?,?)",
                a.getCourse(), a.getTitle(), a.getDeadline().toString(), a.getDifficulty(), a.getProgress(), a.getEstimatedHours(), a.getMood());
        Long id = jdbc.queryForObject("SELECT last_insert_rowid()", Long.class);
        a.setId(id);
        return riskEngine.enrich(a);
    }

    public void delete(Long id) {
        jdbc.update("DELETE FROM assignments WHERE id=?", id);
    }
}
