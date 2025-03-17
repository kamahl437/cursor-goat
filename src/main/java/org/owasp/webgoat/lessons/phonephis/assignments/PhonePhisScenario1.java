package org.owasp.webgoat.lessons.phonephis.assignments;

import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AssignmentHints;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.owasp.webgoat.lessons.phonephis.PhonePhishing;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"phonephis.hints.1", "phonephis.hints.2"})
public class PhonePhisScenario1 implements AssignmentEndpoint {

    @GetMapping("/phonephis/scenario1")
    public String scenario1() {
        return "phonephis-scenario1";
    }

    @PostMapping("/phonephis/scenario1")
    @ResponseBody
    public AttackResult completed(@RequestParam("answer") String answer) {
        boolean correct = false;
        String[] correctAnswers = {"credentials", "remote access", "software install", "urgency"};
        
        // Check if the answer contains any of the keywords
        for (String keyword : correctAnswers) {
            if (answer.toLowerCase().contains(keyword.toLowerCase())) {
                correct = true;
                break;
            }
        }
        
        if (correct) {
            return success(this)
                    .feedback("phonephis.scenario1.success")
                    .build();
        }
        
        return failed(this)
                .feedback("phonephis.scenario1.failure")
                .build();
    }
} 