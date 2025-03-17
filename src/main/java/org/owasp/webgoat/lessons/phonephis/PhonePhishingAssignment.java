/*
 * SPDX-FileCopyrightText: Copyright © 2023 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.phonephis;

import static org.owasp.webgoat.container.assignments.AttackResultBuilder.failed;
import static org.owasp.webgoat.container.assignments.AttackResultBuilder.success;

import org.owasp.webgoat.container.assignments.AssignmentEndpoint;
import org.owasp.webgoat.container.assignments.AttackResult;
import org.owasp.webgoat.container.assignments.AssignmentHints;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Assignment for the phone phishing lesson focusing on vishing attacks.
 */
@RestController
@AssignmentHints({
    "phonephis.hints.1", 
    "phonephis.hints.2", 
    "phonephis.hints.3"
})
public class PhonePhishingAssignment implements AssignmentEndpoint {

    /**
     * Endpoint to check the answer to the phone phishing scenario.
     */
    @PostMapping("/phonephis/scenario")
    @ResponseBody
    public AttackResult completed(@RequestParam("scenario") int scenario, @RequestParam("action") String action) {
        switch (scenario) {
            case 1: // IT support call scenario
                return checkITSupportAction(action.toLowerCase());
            case 2: // Bank call scenario
                return checkBankCallAction(action.toLowerCase());
            case 3: // Survey call scenario
                return checkSurveyCallAction(action.toLowerCase());
            default:
                return failed(this).feedback("phonephis.error.invalidscenario").build();
        }
    }

    private AttackResult checkITSupportAction(String action) {
        if (action.contains("verify") || action.contains("call back") || action.contains("hang up")) {
            return success(this).feedback("phonephis.scenario1.success").build();
        }
        return failed(this).feedback("phonephis.scenario1.failure").build();
    }

    private AttackResult checkBankCallAction(String action) {
        if (action.contains("official number") || action.contains("hang up") || action.contains("no information")) {
            return success(this).feedback("phonephis.scenario2.success").build();
        }
        return failed(this).feedback("phonephis.scenario2.failure").build();
    }

    private AttackResult checkSurveyCallAction(String action) {
        if (action.contains("no personal") || action.contains("hang up") || action.contains("decline")) {
            return success(this).feedback("phonephis.scenario3.success").build();
        }
        return failed(this).feedback("phonephis.scenario3.failure").build();
    }
} 