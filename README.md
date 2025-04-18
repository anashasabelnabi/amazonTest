 Test Automation Framework

[![Java](https://img.shields.io/badge/Java-17%2B-blue)](https://www.oracle.com/java/)
[![RestAssured](https://img.shields.io/badge/RestAssured-5.3.0-green)](https://rest-assured.io/)
[![TestNG](https://img.shields.io/badge/TestNG-7.8.0-red)](https://testng.org/)

A hybrid test automation framework supporting both **UI** (Selenium) and **API** (RestAssured) testing.

## 📦 Project Structure
src
├── main
│   ├── java
│   │   ├── config          # Configuration files (e.g., API endpoints, constants)
│   │   └── utils           # Utility classes (e.g., helper methods, drivers)
│   └── resources           # Main resources (e.g., property files, JSON schemas)
└── test
    ├── java
    │   ├── api             # API-related test classes (e.g., REST API tests)
    │   ├── base            # Base classes for tests (e.g., BaseTest, BaseApi)
    │   ├── pages           # Page Object Model classes for UI tests
    │   ├── helper          # Helper/utility classes specific to tests
    │   └── ui              # UI-related test classes (e.g., Selenium tests)
    └── resources           # Test-specific resources (e.g., test data, JSON files)


## 🚀 Quick Start

### Prerequisites
- Java 17+
- Maven 3.8+
- Chrome/Firefox (for UI tests)

### Installation
```bash
git clone https://github.com/anashasabelnabi/amazonTest.git
cd your-repo
mvn clean install
🧪 Running Tests
Before Running : On DriverFactory.class Add your chromeDriver version or remove this line ##WebDriverManager.chromedriver().driverVersion("135.0.7049.84").setup();
API Tests
mvn test -Dtest=UserApiTests
UI Tests
mvn test -Dtest=AmazonUITests
Full Suite
mvn test
🔧 Configuration
Edit src/main/resources/config.properties:

properties
# UI Config
base.url=https://www.amazon.eg
browser=chrome

# API Config
api.base.url=https://reqres.in
🛠 Key Features
Hybrid Testing: UI + API in one framework

Dynamic Test Data: JSON-based test cases

Parallel Execution: TestNG parallel suites

CI/CD Ready: GitHub Actions compatible

📂 Test Data Management
API requests/responses: src/test/resources/yourTestCase/

Example API test data:

// src/test/resources/UC01_createUser/request.json
{
  "name": "morpheus",
  "job": "leader"
}
🐛 Debugging
View detailed logs:

mvn test -Dtest=UserApiTests -Dlogging.level.io.restassured=DEBUG
🤝 Contribution
Fork the repository

Create your feature branch (git checkout -b feature/your-feature)

Commit your changes (git commit -m 'Add some feature')

Push to the branch (git push origin feature/your-feature)

Open a Pull Request

📜 License
MIT © [Anas Hasab]

