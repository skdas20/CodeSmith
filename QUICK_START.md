# Quick Start Guide

## Run the Application

### Option 1: Using the batch script (Windows)
```bash
run.bat
```

### Option 2: Using Maven directly
```bash
mvn spring-boot:run
```

### Option 3: Package and run JAR
```bash
mvn clean package
java -jar target/codesmith-1.0.0.jar
```

## Access the Application

Once the application starts, open your browser and go to:
```
http://localhost:8080
```

## How to Use

1. **Enter Project Name**: Type what you want to build
   - Examples: "Hospital Management System", "ERP Application", "Smart AI Assistant"

2. **Click Generate**: The AI will start creating your project
   - This may take 1-3 minutes depending on complexity

3. **Download ZIP**: Your complete project will be downloaded as a ZIP file

## What You Get

The generated ZIP contains:

### `/backend/`
- Complete Spring Boot application
- Entity classes
- Repositories
- Services
- REST Controllers
- Database configuration (H2)
- pom.xml with all dependencies

### `/frontend/`
- index.html
- styles.css
- app.js
- Fully integrated with backend APIs

### `/docs/`
- **SRS.txt** - Software Requirements Specification
- **Design-Document.txt** - Technical design and architecture
- **Feasibility-Report.txt** - Project feasibility analysis

### `README.md`
- Setup instructions
- Project structure
- How to run the generated project

## Troubleshooting

### API Limit Reached
The application automatically switches between two API keys if one reaches its limit.

### Port 8080 Already in Use
Change the port in `src/main/resources/application.properties`:
```properties
server.port=8081
```

### Build Errors
Make sure you have:
- Java 17 or higher: `java -version`
- Maven 3.6+: `mvn -version`

## Next Steps

After downloading your generated project:
1. Extract the ZIP file
2. Follow the README.md inside the generated project
3. Set up the Spring Boot backend
4. Open the frontend in a browser
5. Start developing!

## Tips

- Be specific with project names for better results
- The AI generates production-ready starter code
- You can customize the generated code as needed
- All generated projects use H2 in-memory database (no setup required)
