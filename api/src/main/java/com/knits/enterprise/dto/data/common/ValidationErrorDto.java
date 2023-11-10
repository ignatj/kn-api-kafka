package com.knits.enterprise.dto.data.common;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ValidationErrorDto {

    private List<Violation> violations;

    public void addViolation(String field, String error) {
        if (violations == null) {
            violations = new ArrayList<>();
        }
        violations.add(new Violation(field, error));
    }

    public record Violation(String fieldName, String error) {
    }
}
