/*
 * SPDX-FileCopyrightText: Copyright © 2023 WebGoat authors
 * SPDX-License-Identifier: GPL-2.0-or-later
 */
package org.owasp.webgoat.lessons.phishing;

import org.owasp.webgoat.container.lessons.Category;
import org.owasp.webgoat.container.lessons.Lesson;
import org.springframework.stereotype.Component;

/**
 * Lesson on phishing attacks and how to protect yourself.
 */
@Component
public class Phishing extends Lesson {

  @Override
  public Category getDefaultCategory() {
    return Category.PHISHING;
  }

  @Override
  public String getTitle() {
    return "phishing.title";
  }
} 