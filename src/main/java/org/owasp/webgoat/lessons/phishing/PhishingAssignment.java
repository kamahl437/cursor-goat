/*
 * SPDX-FileCopyrightText: Copyright © 2023 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.phishing;

import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

import java.util.Arrays;
import java.util.List;
import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.owasp.webgoat.container.assignments.AssignmentHints;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Assignment for the phishing lesson with three specific goals.
 */
@RestController
@AssignmentHints({
    "phishing.hints.1", 
    "phishing.hints.2", 
    "phishing.hints.3"
})
public class PhishingAssignment implements AssignmentEndpoint {

    private static final List<String> ACCEPTED_ANSWERS = Arrays.asList(
            "facebook",
            "banking",
            "josh"
    );

    /**
     * Endpoint to check answers for the phishing lesson goals.
     */
    @PostMapping("/phishing/goal")
    @ResponseBody
    public AttackResult completed(@RequestParam String goal, @RequestParam String answer) {
        switch (goal) {
            case "1":
                return checkAnswerForGoal1(answer.toLowerCase());
            case "2":
                return checkAnswerForGoal2(answer.toLowerCase());
            case "3":
                return checkAnswerForGoal3(answer.toLowerCase());
            default:
                return failed(this).feedback("phishing.error.invalidgoal").build();
        }
    }

    private AttackResult checkAnswerForGoal1(String answer) {
        if (answer.contains("facebook") || answer.contains("social media")) {
            return success(this).feedback("phishing.goal1.success").build();
        }
        return failed(this).feedback("phishing.goal1.failure").build();
    }

    private AttackResult checkAnswerForGoal2(String answer) {
        if (answer.contains("bank") || answer.contains("banking") || answer.contains("online banking")) {
            return success(this).feedback("phishing.goal2.success").build();
        }
        return failed(this).feedback("phishing.goal2.failure").build();
    }

    private AttackResult checkAnswerForGoal3(String answer) {
        if (answer.contains("josh")) {
            return success(this).feedback("phishing.goal3.success").build();
        }
        return failed(this).feedback("phishing.goal3.failure").build();
    }
} 