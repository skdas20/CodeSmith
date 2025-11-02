# How CodeSmith Works

## Overview

CodeSmith is an AI-powered code generator that creates complete project codebases using Google's Gemini AI. When you provide a project idea, it generates everything you need to start development immediately.

## The Generation Process

### Step 1: User Input
- User enters project name (e.g., "Hospital Management System")
- Frontend validates input and sends request to backend

### Step 2: Backend Processing
The `CodeGenerationService` orchestrates the generation of multiple components:

1. **Backend Code Generation**
   - Prompts Gemini AI to create Spring Boot application
   - Generates entities, repositories, services, controllers
   - Creates database configuration (H2)
   - Generates pom.xml with dependencies

2. **Frontend Code Generation**
   - Creates HTML structure
   - Generates CSS styling
   - Writes JavaScript with API integration
   - Ensures proper connection to backend

3. **Documentation Generation**
   - **SRS (Software Requirements Specification)**
     - Functional requirements
     - Non-functional requirements
     - User roles and features

   - **Design Document**
     - System architecture
     - Database schema
     - API endpoints
     - Technology stack

   - **Feasibility Report**
     - Technical feasibility
     - Economic analysis
     - Risk assessment
     - Recommendations

### Step 3: ZIP Creation
- All generated files are packaged into a ZIP
- Organized folder structure:
  ```
  ProjectName/
  ├── backend/generated-code.txt
  ├── frontend/generated-code.txt
  ├── docs/
  │   ├── SRS.txt
  │   ├── Design-Document.txt
  │   └── Feasibility-Report.txt
  └── README.md
  ```

### Step 4: Download
- ZIP file sent to browser
- User downloads complete project

## Technical Architecture

### Backend (Spring Boot)

```
┌─────────────────────────────────────┐
│     ProjectController               │
│  - POST /api/generate               │
│  - GET /api/health                  │
└───────────┬─────────────────────────┘
            │
            ↓
┌─────────────────────────────────────┐
│   CodeGenerationService             │
│  - generateProject()                │
│  - generateBackendCode()            │
│  - generateFrontendCode()           │
│  - generateSRS()                    │
│  - generateDesignDocument()         │
│  - generateFeasibilityReport()      │
│  - createZipFile()                  │
└───────────┬─────────────────────────┘
            │
            ↓
┌─────────────────────────────────────┐
│      GeminiService                  │
│  - generateContent(prompt)          │
│  - callGeminiAPI()                  │
│  - Auto API key switching           │
└─────────────────────────────────────┘
```

### Frontend (HTML/CSS/JS)

```
┌─────────────────────────────────────┐
│         index.html                  │
│  - Beautiful gradient UI            │
│  - Tailwind CSS styling             │
│  - Glass morphism effects           │
│  - Responsive design                │
└───────────┬─────────────────────────┘
            │
            ↓
┌─────────────────────────────────────┐
│          app.js                     │
│  - AJAX calls to backend            │
│  - Real-time status updates         │
│  - File download handling           │
│  - Error management                 │
└─────────────────────────────────────┘
```

## AI Prompt Engineering

Each component uses carefully crafted prompts:

### Backend Prompt Example:
```
Generate a complete Spring Boot backend code for a [PROJECT_NAME] project.
Include:
1. Main Application class
2. Entity classes for H2 database
3. Repository interfaces
4. Service layer
5. REST Controllers
6. application.properties with H2 config
7. pom.xml with dependencies
Make it simple, concise, and production-ready.
```

The prompts are designed to:
- Be specific and detailed
- Request complete, working code
- Specify format (file paths as comments)
- Emphasize simplicity and best practices

## Key Features

### 1. Dual API Key System
```java
private boolean usePrimaryKey = true;

public String generateContent(String prompt) throws Exception {
    try {
        return callGeminiAPI(prompt, primaryKey);
    } catch (Exception e) {
        if (e.getMessage().contains("429")) {
            usePrimaryKey = !usePrimaryKey;
            return callGeminiAPI(prompt, secondaryKey);
        }
        throw e;
    }
}
```

### 2. Real-time Status Updates
The frontend displays progress:
- "Analyzing your project requirements..."
- "Generating Spring Boot backend..."
- "Creating frontend components..."
- "Generating SRS document..."
- etc.

### 3. Error Handling
- API rate limit detection
- Automatic key switching
- User-friendly error messages
- Graceful degradation

## What Makes It Special

1. **Complete Solution**: Not just code snippets, but entire working projects
2. **Documentation Included**: Professional SRS, Design, and Feasibility docs
3. **Zero Configuration**: Generated projects use H2 (no DB setup needed)
4. **Modern Stack**: Latest Spring Boot, clean frontend
5. **Production Ready**: Best practices, proper structure
6. **Beautiful UI**: Modern, responsive design with animations

## Example Use Cases

1. **Rapid Prototyping**: Quick proof-of-concept for client demos
2. **Learning**: Study well-structured project templates
3. **Project Proposals**: Complete documentation for pitches
4. **Hackathons**: Jump-start development with working base
5. **Teaching**: Show students complete project structures

## Limitations & Future Enhancements

### Current Limitations:
- AI-generated code may need customization
- Limited to Spring Boot + HTML/CSS/JS stack
- Documentation is template-based

### Future Enhancements:
- Support for React/Angular/Vue frontends
- Multiple backend frameworks (Node.js, Django, etc.)
- Database choice (MySQL, PostgreSQL, MongoDB)
- Custom configuration options
- Project customization wizard
- Real-time code preview
