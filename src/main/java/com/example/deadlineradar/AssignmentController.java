package com.example.deadlineradar;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class AssignmentController {
    private final AssignmentRepository repo;

    public AssignmentController(AssignmentRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/assignments")
    public List<Assignment> all() {
        return repo.findAll();
    }

    @PostMapping("/assignments")
    public Assignment create(@Valid @RequestBody Assignment a) {
        return repo.create(a);
    }

    @DeleteMapping("/assignments/{id}")
    public Map<String, Object> delete(@PathVariable Long id) {
        repo.delete(id);
        return Map.of("ok", true, "deletedId", id);
    }

    @GetMapping("/dashboard")
    public Map<String, Object> dashboard() {
        List<Assignment> data = repo.findAll();
        Map<String, Long> riskCounts = data.stream().collect(Collectors.groupingBy(Assignment::getRiskLevel, LinkedHashMap::new, Collectors.counting()));
        Map<String, Double> avgRiskByCourse = data.stream().collect(Collectors.groupingBy(Assignment::getCourse, LinkedHashMap::new, Collectors.averagingDouble(Assignment::getRiskScore)));
        double avgRisk = data.stream().mapToDouble(Assignment::getRiskScore).average().orElse(0);
        Optional<Assignment> topRisk = data.stream().max(Comparator.comparingDouble(Assignment::getRiskScore));
        return Map.of(
                "total", data.size(),
                "averageRisk", Math.round(avgRisk * 10.0) / 10.0,
                "riskCounts", riskCounts,
                "avgRiskByCourse", avgRiskByCourse,
                "topRisk", topRisk.orElse(null)
        );
    }
}
