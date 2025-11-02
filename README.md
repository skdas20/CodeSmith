# CodeSmith - AI Project Generator

CodeSmith is an AI-powered code generation tool that creates complete project codebases with documentation using Gemini AI.
link - https://codesmith-uj5w.onrender.com/
## Features

- **Full Stack Code Generation**: Generates Spring Boot backend with H2 database and HTML/CSS/JS frontend
- **Complete Documentation**: Automatically creates SRS, Design Document, and Feasibility Report
- **ZIP Download**: Packages everything into a downloadable ZIP file
- **Beautiful UI**: Modern, responsive interface with Tailwind CSS
- **Dual API Key Support**: Automatic fallback between two Gemini API keys

## Tech Stack

### Backend
- Java 17
- Spring Boot 3.2.0
- Google Gemini AI API
- Maven

### Frontend
- HTML5
- Tailwind CSS
- Vanilla JavaScript
- Font Awesome Icons

## Setup Instructions

### Prerequisites
- Java 17 or higher
- Maven 3.6+

### Installation

1. Clone the repository
```bash
cd CodeSmith
```

2. The API keys are already configured in `src/main/resources/application.properties`

3. Build the project
```bash
mvn clean install
```

4. Run the application
```bash
mvn spring-boot:run
```

5. Open your browser and navigate to:
```
http://localhost:8080
```

## Usage

1. Enter your project idea (e.g., "Hospital Management System", "ERP", "Smart AI Assistant")
2. Click "Generate Project"
3. Wait for the AI to generate your complete codebase
4. Download the ZIP file containing:
   - Spring Boot backend code
   - HTML/CSS/JS frontend code
   - SRS Document
   - Design Document
   - Feasibility Report

## API Endpoints

### Generate Project
- **POST** `/api/generate`
- **Body**:
```json
{
  "projectName": "Your Project Name"
}
```
- **Response**: ZIP file download

### Health Check
- **GET** `/api/health`
- **Response**: `"CodeSmith API is running!"`

## Project Structure

```
CodeSmith/
├── src/
│   ├── main/
│   │   ├── java/com/codesmith/
│   │   │   ├── CodeSmithApplication.java
│   │   │   ├── config/
│   │   │   │   └── CorsConfig.java
│   │   │   ├── controller/
│   │   │   │   └── ProjectController.java
│   │   │   ├── dto/
│   │   │   │   └── ProjectRequest.java
│   │   │   └── service/
│   │   │       ├── GeminiService.java
│   │   │       └── CodeGenerationService.java
│   │   └── resources/
│   │       ├── static/
│   │       │   ├── index.html
│   │       │   └── app.js
│   │       └── application.properties
└── pom.xml
```

## Configuration

API keys and configuration can be modified in `src/main/resources/application.properties`:

```properties
server.port=8080
gemini.api.key.primary=YOUR_PRIMARY_KEY
gemini.api.key.secondary=YOUR_SECONDARY_KEY
```

## Features in Generated Projects

Each generated project includes:
- Complete Spring Boot backend with REST APIs
- Entity classes and JPA repositories
- Service layer implementation
- H2 in-memory database configuration
- Responsive frontend with API integration
- Professional documentation (SRS, Design Doc, Feasibility Report)

## License

MIT License

## Support

For issues and questions, please open an issue on GitHub.
