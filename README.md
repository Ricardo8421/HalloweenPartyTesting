# CandyMapper Automation Framework

An automated end-to-end (E2E) testing suite built with **Java**, **Selenium WebDriver**, **TestNG**, and **Maven**. This project implements the **Page Object Model (POM)** design pattern to automate test scenarios for the [CandyMapper Party Location](https://candymapper.com/party-location) application.

---

## Project Overview

This repository provides robust UI automation for key user flows on CandyMapper, including account creation, user authentication, pop-up handling, multi-factor authentication, and contact form submissions.

### Key Features
* **Page Object Model (POM)** structure for maintainability and clean code practices.
* **TestNG** integration for test suite execution, assertions, and reporting.
* **Maven** build management for seamless dependency resolution and execution.
* **Base Classes** (`BaseTest`, `BasePage`) for reusable setup, teardown, and common web element interactions.

---

## Tech Stack & Prerequisites

* **Java Development Kit (JDK)**: 11 or higher
* **Build Tool**: Apache Maven 3.6+
* **Automation Library**: Selenium WebDriver 4.x
* **Test Framework**: TestNG
* **IDE**: IntelliJ IDEA or Eclipse (recommended)

---

## Project Structure

```text
.
├── logs/                         # Execution log files (Log4j)
├── reports/                      # Here are the task evidence and the screenshots failures
└── src/
    ├── main/                     # Application source code
    └── test/
        ├── java/
        │   ├── models/           # Data models / POJO classes
        │   │   └── UserAccount.java
        │   ├── pages/            # Page Object Model mappings
        │   │   ├── common/       # Reusable base page classes
        │   │   │   └── BasePage.java
        │   │   └── party/        # WebElements and actions for the Party module
        │   │       ├── config/
        │   │       ├── ContactUsPage.java
        │   │       ├── CreateAccountPage.java
        │   │       ├── HalloweenPartyPage.java
        │   │       ├── PopUpPage.java
        │   │       ├── SignInPage.java
        │   │       └── TwoFactorAuthenticationPage.java
        │   └── testcases/        # Automated test suites (TestNG)
        │       ├── common/       # Global test setup and teardown
        │       │   └── BaseTest.java
        │       └── party/        # Specific test cases for the Party module
        │           ├── ContactUsTest.java
        │           ├── CreateAccountTest.java
        │           ├── HalloweenPartyTest.java
        │           ├── PopUpTest.java
        │           ├── SignInTest.java
        │           └── TwoFactorAuthenticationTest.java
        └── resources/            # Environment configurations and TestNG suite files
            ├── env.dev.properties
            ├── env.qa.properties
            ├── log4j2.xml
            ├── regression-suite.xml
            ├── smoke-suite.xml
            └── testng.xml
```

## Getting Started

### 1. Clone the Repository
```bash
git clone <REPOSITORY_URL>
cd <REPOSITORY_FOLDER>

### 2. Install Dependencies
Ensure Maven is installed and run:
```bash
mvn clean install -DskipTests
```
---
## Executing Tests

### Option 1: Execute via Maven Command Line
Run the entire test suite using `testng.xml`:
```bash
mvn clean test
```

Run a specific test class:
```bash
mvn test -Dtest=SignInTest
```

### Option 2: Execute via IDE (IntelliJ / Eclipse)
1. Right-click on `src/test/resources/testng.xml` and select **Run 'testng.xml'**.
2. Or right-click any specific Test class (e.g., `CreateAccountTest.java`) and click **Run**.