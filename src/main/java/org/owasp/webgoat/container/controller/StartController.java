/*
 * SPDX-FileCopyrightText: Copyright © 2023 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.container.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 * Controller for handling the start.mvc URL.
 */
@Controller
public class StartController {

    /**
     * Handles the start.mvc URL with optional admin parameter.
     *
     * @param request the HTTP request
     * @param admin the admin parameter
     * @return the appropriate view
     */
    @GetMapping(path = "start.mvc")
    public ModelAndView start(HttpServletRequest request, 
                             @RequestParam(required = false) Boolean admin) {
        ModelAndView model = new ModelAndView();
        
        if (Boolean.TRUE.equals(admin)) {
            // If admin=true, add an admin message to the model
            model.addObject("adminMessage", "Welcome to the Admin Panel");
        }
        
        // Always use the main_new view
        model.setViewName("main_new");
        return model;
    }
} 