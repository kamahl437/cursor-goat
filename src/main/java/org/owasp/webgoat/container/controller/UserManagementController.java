/*
 * SPDX-FileCopyrightText: Copyright © 2023 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.container.controller;

import jakarta.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.owasp.webgoat.container.users.UserService;
import org.owasp.webgoat.container.users.WebGoatUser;
import org.owasp.webgoat.container.users.UserRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Controller for handling user management functionality.
 */
@Controller
@RequestMapping("/user-management")
@RequiredArgsConstructor
@Slf4j
public class UserManagementController {

    private final UserService userService;
    private final UserRepository userRepository;

    /**
     * Handles the user management page request.
     *
     * @param request the HTTP request
     * @return the user management view
     */
    @GetMapping
    public ModelAndView userManagement(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();
        model.setViewName("user-management");
        model.addObject("message", "Manage WebGoat Users");
        
        // Get all users from the database
        List<WebGoatUser> dbUsers = userService.getAllUsers();
        
        // Convert to format expected by the view
        List<Object[]> userDataList = new ArrayList<>();
        for (WebGoatUser user : dbUsers) {
            // For each user, create an array with [username, role, status]
            // Since we don't have a status field, we'll mark all as "Active"
            userDataList.add(new Object[] {
                user.getUsername(),
                user.getRole(),
                "Active"
            });
        }
        
        model.addObject("users", userDataList);
        return model;
    }
    
    /**
     * Displays the form for creating a new user.
     */
    @GetMapping("/create")
    public String createUserForm(Model model) {
        model.addAttribute("user", new WebGoatUser("", ""));
        model.addAttribute("isNew", true);
        model.addAttribute("roles", new String[] {WebGoatUser.ROLE_USER, WebGoatUser.ROLE_ADMIN});
        return "user-form";
    }
    
    /**
     * Handles the submission of the create user form.
     */
    @PostMapping("/create")
    public String createUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role,
            RedirectAttributes redirectAttributes) {
        
        try {
            // Check if user already exists
            List<WebGoatUser> users = userService.getAllUsers();
            boolean userExists = users.stream()
                    .anyMatch(user -> user.getUsername().equals(username));
            
            if (userExists) {
                redirectAttributes.addFlashAttribute("error", "User with username '" + username + "' already exists");
                return "redirect:/user-management/create";
            }
            
            // Add the new user with the specified role
            if (role.equals(WebGoatUser.ROLE_ADMIN)) {
                // For admin users, we need to create and save the user directly
                WebGoatUser newUser = new WebGoatUser(username, password, WebGoatUser.ROLE_ADMIN);
                userRepository.save(newUser);
            } else {
                // For regular users, we can use the standard addUser method
                userService.addUser(username, password);
            }
            
            redirectAttributes.addFlashAttribute("success", "User created successfully");
            return "redirect:/user-management";
        } catch (Exception e) {
            log.error("Error creating user", e);
            redirectAttributes.addFlashAttribute("error", "Error creating user: " + e.getMessage());
            return "redirect:/user-management/create";
        }
    }
    
    /**
     * Displays the form for editing an existing user.
     */
    @GetMapping("/edit")
    public String editUserForm(@RequestParam String username, Model model, RedirectAttributes redirectAttributes) {
        try {
            WebGoatUser user = userService.loadUserByUsername(username);
            model.addAttribute("user", user);
            model.addAttribute("isNew", false);
            model.addAttribute("roles", new String[] {WebGoatUser.ROLE_USER, WebGoatUser.ROLE_ADMIN});
            return "user-form";
        } catch (Exception e) {
            log.error("Error loading user for edit", e);
            redirectAttributes.addFlashAttribute("error", "User not found: " + username);
            return "redirect:/user-management";
        }
    }
    
    /**
     * Handles the submission of the edit user form.
     */
    @PostMapping("/edit")
    public String editUser(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String role,
            RedirectAttributes redirectAttributes) {
        
        try {
            // First delete the existing user (if it exists)
            WebGoatUser existingUser = userService.loadUserByUsername(username);
            if (existingUser != null) {
                // We don't have a delete method, so we'll just overwrite the user
            }
            
            // Add the user with updated information
            if (role.equals(WebGoatUser.ROLE_ADMIN)) {
                // For admin users, we need to create and save the user directly
                WebGoatUser updatedUser = new WebGoatUser(username, password, WebGoatUser.ROLE_ADMIN);
                userRepository.save(updatedUser);
            } else {
                // For regular users, we can use the standard addUser method
                userService.addUser(username, password);
            }
            
            redirectAttributes.addFlashAttribute("success", "User updated successfully");
            return "redirect:/user-management";
        } catch (Exception e) {
            log.error("Error updating user", e);
            redirectAttributes.addFlashAttribute("error", "Error updating user: " + e.getMessage());
            return "redirect:/user-management/edit?username=" + username;
        }
    }
    
    /**
     * Handles the deletion of a user.
     */
    @GetMapping("/delete")
    public String deleteUser(@RequestParam String username, RedirectAttributes redirectAttributes) {
        try {
            // Note: UserService doesn't have a delete method, so we'd need to implement one
            // For now, we'll just show a message
            redirectAttributes.addFlashAttribute("warning", 
                "Delete functionality is not implemented yet. User '" + username + "' was not deleted.");
            return "redirect:/user-management";
        } catch (Exception e) {
            log.error("Error deleting user", e);
            redirectAttributes.addFlashAttribute("error", "Error deleting user: " + e.getMessage());
            return "redirect:/user-management";
        }
    }
} 