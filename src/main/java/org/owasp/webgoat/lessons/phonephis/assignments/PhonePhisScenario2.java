package org.owasp.webgoat.lessons.phonephis.assignments;

import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AssignmentHints;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AssignmentHints({"phonephis.hints.3", "phonephis.hints.4"})
public class PhonePhisScenario2 implements AssignmentEndpoint {

    @GetMapping("/phonephis/scenario2")
    public String scenario2() {
        return "phonephis-scenario2";
    }

    @PostMapping("/phonephis/scenario2")
    @ResponseBody
    public AttackResult completed(@RequestParam("answer") String answer) {
        boolean correct = false;
        String[] correctAnswers = {"card details", "account number", "password", "pin", "security code", "urgency"};
        
        // Check if the answer contains any of the keywords
        for (String keyword : correctAnswers) {
            if (answer.toLowerCase().contains(keyword.toLowerCase())) {
                correct = true;
                break;
            }
        }
        
        if (correct) {
            return success(this)
                    .feedback("phonephis.scenario2.success")
                    .build();
        }
        
        return failed(this)
                .feedback("phonephis.scenario2.failure")
                .build();
    }
} 