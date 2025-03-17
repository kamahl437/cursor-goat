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
@AssignmentHints({"phonephis.hints.5", "phonephis.hints.6"})
public class PhonePhisScenario3 implements AssignmentEndpoint {

    @GetMapping("/phonephis/scenario3")
    public String scenario3() {
        return "phonephis-scenario3";
    }

    @PostMapping("/phonephis/scenario3")
    @ResponseBody
    public AttackResult completed(@RequestParam("answer") String answer) {
        boolean correct = false;
        String[] correctAnswers = {"personal information", "identity theft", "social engineering", "gift card", "payment"};
        
        // Check if the answer contains any of the keywords
        for (String keyword : correctAnswers) {
            if (answer.toLowerCase().contains(keyword.toLowerCase())) {
                correct = true;
                break;
            }
        }
        
        if (correct) {
            return success(this)
                    .feedback("phonephis.scenario3.success")
                    .build();
        }
        
        return failed(this)
                .feedback("phonephis.scenario3.failure")
                .build();
    }
} 