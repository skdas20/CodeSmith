package com.codesmith.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Service
public class CodeGenerationService {

    @Autowired
    private GeminiService geminiService;

    public byte[] generateProject(String projectName) throws Exception {
        String packageName = projectName.toLowerCase().replaceAll("[^a-z0-9]", "");

        // Step 1: Generate backend schema first to ensure consistency
        Map<String, String> backendFiles = generateBackendCode(projectName, packageName);

        // Step 2: Extract entity fields from generated entity to ensure frontend matches
        String entityFields = extractEntityFields(backendFiles, packageName);

        // Step 3: Generate frontend with matching fields
        Map<String, String> frontendFiles = generateFrontendCode(projectName, entityFields);

        // Step 4: Generate documentation
        String srsDocument = generateSRS(projectName);
        String designDocument = generateDesignDocument(projectName);
        String feasibilityReport = generateFeasibilityReport(projectName);

        // Create ZIP file with proper structure
        return createZipFile(projectName, backendFiles, frontendFiles, srsDocument, designDocument, feasibilityReport);
    }

    private Map<String, String> generateBackendCode(String projectName, String packageName) throws Exception {
        String prompt = String.format(
            "Generate a COMPLETE Spring Boot backend for '%s'.\n" +
            "Package name: com.%s\n\n" +
            "CRITICAL FORMATTING RULES:\n" +
            "1. Start EACH file with EXACTLY: ### FILE: path/to/file.ext\n" +
            "2. NO markdown code fences (```), NO extra formatting\n" +
            "3. Pure code only after the ### FILE: marker\n" +
            "4. One blank line between files\n\n" +
            "REQUIRED FILES (in this exact order):\n\n" +
            "### FILE: pom.xml\n" +
            "Complete Maven POM with Spring Boot 3.2.0, H2, JPA, Web, NO test scope for H2\n\n" +
            "### FILE: src/main/java/com/%s/Application.java\n" +
            "@SpringBootApplication class with main method\n\n" +
            "### FILE: src/main/java/com/%s/entity/Employee.java\n" +
            "Complete entity with:\n" +
            "- @Entity, @Table annotations\n" +
            "- Long id with @Id and @GeneratedValue\n" +
            "- String firstName, lastName, email (all with @Column)\n" +
            "- String phoneNumber, department (with @Column)\n" +
            "- No-arg constructor\n" +
            "- All-args constructor\n" +
            "- All getters and setters\n\n" +
            "### FILE: src/main/java/com/%s/repository/EmployeeRepository.java\n" +
            "Interface extending JpaRepository<Employee, Long>\n\n" +
            "### FILE: src/main/java/com/%s/service/EmployeeService.java\n" +
            "Service with: getAllEmployees, getEmployeeById, createEmployee, updateEmployee, deleteEmployee\n\n" +
            "### FILE: src/main/java/com/%s/controller/EmployeeController.java\n" +
            "@RestController, @RequestMapping(\"/api/employees\"), @CrossOrigin(origins = \"*\")\n" +
            "All CRUD endpoints: GET, GET/{id}, POST, PUT/{id}, DELETE/{id}\n\n" +
            "### FILE: src/main/resources/application.properties\n" +
            "H2 config with console enabled, testdb, show-sql=true\n\n" +
            "IMPORTANT: Pure code ONLY, absolutely NO ``` markers anywhere!",
            projectName, packageName, packageName, packageName, packageName, packageName, packageName
        );

        String response = geminiService.generateContent(prompt);
        return parseFilesFromResponse(response);
    }

    private String extractEntityFields(Map<String, String> backendFiles, String packageName) {
        // Find the entity file
        String entityKey = backendFiles.keySet().stream()
            .filter(key -> key.contains("entity") && key.endsWith(".java"))
            .findFirst()
            .orElse(null);

        if (entityKey == null) {
            return "firstName, lastName, email, phoneNumber, department";
        }

        String entityContent = backendFiles.get(entityKey);

        // Extract field names from entity (look for private fields)
        Pattern fieldPattern = Pattern.compile("private\\s+\\w+\\s+(\\w+);");
        Matcher matcher = fieldPattern.matcher(entityContent);

        List<String> fields = new ArrayList<>();
        while (matcher.find()) {
            String fieldName = matcher.group(1);
            if (!fieldName.equals("id")) { // Skip ID field
                fields.add(fieldName);
            }
        }

        return String.join(", ", fields);
    }

    private Map<String, String> generateFrontendCode(String projectName, String entityFields) throws Exception {
        String prompt = String.format(
            "Generate a COMPLETE frontend for '%s' using HTML, CSS, and JavaScript.\n\n" +
            "The backend entity has these fields: %s\n\n" +
            "CRITICAL FORMATTING RULES:\n" +
            "1. Start EACH file with EXACTLY: ### FILE: filename.ext\n" +
            "2. NO markdown code fences (```), NO extra formatting\n" +
            "3. Pure code only after the ### FILE: marker\n\n" +
            "REQUIRED FILES:\n\n" +
            "### FILE: index.html\n" +
            "- Complete HTML5 structure\n" +
            "- Form with ALL entity fields: %s\n" +
            "- Table to display employees\n" +
            "- Edit modal with ALL fields\n" +
            "- Link style.css and app.js\n\n" +
            "### FILE: style.css\n" +
            "- Modern, responsive design\n" +
            "- Clean table styling\n" +
            "- Form styling\n" +
            "- Modal styling\n" +
            "- Button hover effects\n\n" +
            "### FILE: app.js\n" +
            "- const API_URL = 'http://localhost:8080/api/employees'\n" +
            "- loadEmployees() function with fetch\n" +
            "- displayEmployees() to populate table\n" +
            "- handleAddEmployee() with ALL fields: %s\n" +
            "- handleUpdateEmployee() with ALL fields\n" +
            "- deleteEmployee() function\n" +
            "- Edit modal logic\n\n" +
            "Make it professional and fully functional.\n" +
            "IMPORTANT: Pure code ONLY, NO ``` anywhere!",
            projectName, entityFields, entityFields, entityFields
        );

        String response = geminiService.generateContent(prompt);
        return parseFilesFromResponse(response);
    }

    private String generateSRS(String projectName) throws Exception {
        String prompt = String.format(
            "Create a professional Software Requirements Specification (SRS) for '%s'.\n\n" +
            "Include:\n" +
            "1. Introduction and Purpose\n" +
            "2. Functional Requirements (detailed list)\n" +
            "3. Non-Functional Requirements (performance, security, usability)\n" +
            "4. User Roles and Permissions\n" +
            "5. System Features\n" +
            "6. Database Requirements\n\n" +
            "Format as proper markdown. Be professional and concise (2-3 pages).\n" +
            "NO code fences needed, just plain markdown text.",
            projectName
        );
        return cleanContent(geminiService.generateContent(prompt));
    }

    private String generateDesignDocument(String projectName) throws Exception {
        String prompt = String.format(
            "Create a professional Design Document for '%s'.\n\n" +
            "Include:\n" +
            "1. System Architecture Overview\n" +
            "2. Technology Stack Details\n" +
            "3. Database Schema and Tables\n" +
            "4. API Endpoints (complete REST API documentation)\n" +
            "5. Component Design\n" +
            "6. Data Flow Diagrams (in text/markdown)\n\n" +
            "Format as proper markdown. Be clear and professional (2-3 pages).\n" +
            "NO code fences needed, just plain markdown text.",
            projectName
        );
        return cleanContent(geminiService.generateContent(prompt));
    }

    private String generateFeasibilityReport(String projectName) throws Exception {
        String prompt = String.format(
            "Create a professional Feasibility Report for '%s'.\n\n" +
            "Include:\n" +
            "1. Executive Summary\n" +
            "2. Technical Feasibility (tech stack viability)\n" +
            "3. Economic Feasibility (cost-benefit analysis)\n" +
            "4. Operational Feasibility (user adoption, maintenance)\n" +
            "5. Schedule Feasibility (timeline estimates)\n" +
            "6. Risk Analysis (potential risks and mitigation)\n" +
            "7. Recommendations and Conclusion\n\n" +
            "Format as proper markdown. Be actionable and professional (2-3 pages).\n" +
            "NO code fences needed, just plain markdown text.",
            projectName
        );
        return cleanContent(geminiService.generateContent(prompt));
    }

    private Map<String, String> parseFilesFromResponse(String response) {
        Map<String, String> files = new LinkedHashMap<>();

        // Clean response first
        response = cleanContent(response);

        // Pattern to match ### FILE: path/to/file.ext (case insensitive)
        Pattern filePattern = Pattern.compile("###\\s*FILE:\\s*([^\\n]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = filePattern.matcher(response);

        List<Integer> fileMarkerPositions = new ArrayList<>();
        List<String> filePaths = new ArrayList<>();

        // Find all file markers
        while (matcher.find()) {
            fileMarkerPositions.add(matcher.end());
            filePaths.add(matcher.group(1).trim());
        }

        // Extract content between markers
        for (int i = 0; i < fileMarkerPositions.size(); i++) {
            int contentStart = fileMarkerPositions.get(i);
            int contentEnd = (i < fileMarkerPositions.size() - 1)
                ? response.indexOf("### FILE:", contentStart)
                : response.length();

            String content = response.substring(contentStart, contentEnd).trim();

            // Deep clean the content
            content = deepCleanContent(content);

            if (!content.isEmpty()) {
                files.put(filePaths.get(i), content);
            }
        }

        return files;
    }

    private String cleanContent(String content) {
        if (content == null) return "";

        // Remove markdown code fences at start
        content = content.replaceAll("^```[a-zA-Z]*\\n?", "");

        // Remove markdown code fences at end
        content = content.replaceAll("\\n?```\\s*$", "");

        // Remove inline code fences
        content = content.replaceAll("```[a-zA-Z]*\\n?", "");
        content = content.replaceAll("```", "");

        return content.trim();
    }

    private String deepCleanContent(String content) {
        if (content == null || content.isEmpty()) return "";

        // Step 1: Remove all markdown code fence variations
        content = content.replaceAll("```[a-zA-Z]*\\n?", ""); // ```java, ```xml, etc
        content = content.replaceAll("```\\s*\\n?", "");       // ``` alone
        content = content.replaceAll("```", "");               // Any remaining ```

        // Step 2: Remove common AI response artifacts
        content = content.replaceAll("^\\s*\\n+", "");         // Leading newlines
        content = content.replaceAll("\\n+\\s*$", "");         // Trailing newlines

        // Step 3: Trim each line to remove trailing spaces
        String[] lines = content.split("\\n");
        StringBuilder cleaned = new StringBuilder();
        for (String line : lines) {
            cleaned.append(line.stripTrailing()).append("\n");
        }

        // Step 4: Final trim
        return cleaned.toString().trim();
    }

    private byte[] createZipFile(String projectName, Map<String, String> backendFiles,
                                  Map<String, String> frontendFiles,
                                  String srs, String design, String feasibility) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ZipOutputStream zos = new ZipOutputStream(baos);

        String sanitizedName = projectName.replaceAll("[^a-zA-Z0-9-]", "-");

        // Add backend files with proper structure
        for (Map.Entry<String, String> entry : backendFiles.entrySet()) {
            String path = sanitizedName + "/backend/" + entry.getKey();
            addToZip(zos, path, entry.getValue());
        }

        // Add frontend files
        for (Map.Entry<String, String> entry : frontendFiles.entrySet()) {
            String path = sanitizedName + "/frontend/" + entry.getKey();
            addToZip(zos, path, entry.getValue());
        }

        // Add documents
        addToZip(zos, sanitizedName + "/docs/SRS.md", srs);
        addToZip(zos, sanitizedName + "/docs/Design-Document.md", design);
        addToZip(zos, sanitizedName + "/docs/Feasibility-Report.md", feasibility);

        // Add comprehensive README
        String readme = generateReadme(projectName, sanitizedName);
        addToZip(zos, sanitizedName + "/README.md", readme);

        // Add .gitignore for backend
        String gitignore = generateGitignore();
        addToZip(zos, sanitizedName + "/backend/.gitignore", gitignore);

        zos.close();
        return baos.toByteArray();
    }

    private String generateReadme(String projectName, String sanitizedName) {
        return "# " + projectName + "\n\n" +
                "🚀 Generated by **CodeSmith** - AI Project Generator\n\n" +
                "## 📁 Project Structure\n\n" +
                "```\n" +
                sanitizedName + "/\n" +
                "├── backend/               # Spring Boot Backend\n" +
                "│   ├── pom.xml\n" +
                "│   ├── .gitignore\n" +
                "│   └── src/\n" +
                "│       └── main/\n" +
                "│           ├── java/com/.../\n" +
                "│           │   ├── Application.java\n" +
                "│           │   ├── entity/Employee.java\n" +
                "│           │   ├── repository/EmployeeRepository.java\n" +
                "│           │   ├── service/EmployeeService.java\n" +
                "│           │   └── controller/EmployeeController.java\n" +
                "│           └── resources/\n" +
                "│               └── application.properties\n" +
                "│\n" +
                "├── frontend/              # HTML/CSS/JS Frontend\n" +
                "│   ├── index.html\n" +
                "│   ├── style.css\n" +
                "│   └── app.js\n" +
                "│\n" +
                "└── docs/                  # Documentation\n" +
                "    ├── SRS.md\n" +
                "    ├── Design-Document.md\n" +
                "    └── Feasibility-Report.md\n" +
                "```\n\n" +
                "## ⚡ Quick Start\n\n" +
                "### Backend (Spring Boot)\n\n" +
                "```bash\n" +
                "cd backend\n" +
                "mvn clean install\n" +
                "mvn spring-boot:run\n" +
                "```\n\n" +
                "✅ Backend starts on: **http://localhost:8080**\n\n" +
                "### Frontend\n\n" +
                "**Option 1**: Simply open `frontend/index.html` in your browser\n\n" +
                "**Option 2**: Use a local server:\n" +
                "```bash\n" +
                "cd frontend\n" +
                "python -m http.server 3000\n" +
                "# OR\n" +
                "npx serve .\n" +
                "```\n\n" +
                "✅ Frontend accessible at: **http://localhost:3000** (or directly via file)\n\n" +
                "## 🎯 Features\n\n" +
                "- ✅ **Full CRUD Operations** - Create, Read, Update, Delete\n" +
                "- ✅ **Spring Boot 3.2.0** - Latest stable version\n" +
                "- ✅ **H2 Database** - In-memory database (no setup needed)\n" +
                "- ✅ **JPA/Hibernate** - Modern ORM\n" +
                "- ✅ **REST API** - Standard RESTful endpoints\n" +
                "- ✅ **CORS Enabled** - Frontend-backend integration ready\n" +
                "- ✅ **Responsive UI** - Clean, modern design\n" +
                "- ✅ **Complete Documentation** - SRS, Design, Feasibility\n\n" +
                "## 🔌 API Endpoints\n\n" +
                "| Method | Endpoint | Description |\n" +
                "|--------|----------|-------------|\n" +
                "| GET | /api/employees | Get all employees |\n" +
                "| GET | /api/employees/{id} | Get employee by ID |\n" +
                "| POST | /api/employees | Create new employee |\n" +
                "| PUT | /api/employees/{id} | Update employee |\n" +
                "| DELETE | /api/employees/{id} | Delete employee |\n\n" +
                "## 💾 Database\n\n" +
                "**H2 Console**: http://localhost:8080/h2-console\n\n" +
                "Connection settings:\n" +
                "- **JDBC URL**: `jdbc:h2:mem:testdb`\n" +
                "- **Username**: `sa`\n" +
                "- **Password**: (leave empty)\n\n" +
                "## 📚 Documentation\n\n" +
                "Check the `/docs` folder for:\n" +
                "- **SRS.md** - Complete requirements specification\n" +
                "- **Design-Document.md** - System architecture and design\n" +
                "- **Feasibility-Report.md** - Project feasibility analysis\n\n" +
                "## 🛠️ Tech Stack\n\n" +
                "**Backend**:\n" +
                "- Java 17\n" +
                "- Spring Boot 3.2.0\n" +
                "- Spring Data JPA\n" +
                "- H2 Database\n" +
                "- Maven\n\n" +
                "**Frontend**:\n" +
                "- HTML5\n" +
                "- CSS3\n" +
                "- Vanilla JavaScript\n" +
                "- Fetch API\n\n" +
                "## ✨ What's Next?\n\n" +
                "1. **Customize** the entity fields as needed\n" +
                "2. **Add validation** (Bean Validation annotations)\n" +
                "3. **Add security** (Spring Security)\n" +
                "4. **Switch database** (MySQL, PostgreSQL)\n" +
                "5. **Deploy** to cloud (Heroku, AWS, Azure)\n\n" +
                "---\n\n" +
                "**Generated with ❤️ by CodeSmith**\n";
    }

    private String generateGitignore() {
        return "target/\n" +
               "!.mvn/wrapper/maven-wrapper.jar\n" +
               "!**/src/main/**/target/\n" +
               "!**/src/test/**/target/\n\n" +
               "### STS ###\n" +
               ".apt_generated\n" +
               ".classpath\n" +
               ".factorypath\n" +
               ".project\n" +
               ".settings\n" +
               ".springBeans\n" +
               ".sts4-cache\n\n" +
               "### IntelliJ IDEA ###\n" +
               ".idea\n" +
               "*.iws\n" +
               "*.iml\n" +
               "*.ipr\n\n" +
               "### NetBeans ###\n" +
               "/nbproject/private/\n" +
               "/nbbuild/\n" +
               "/dist/\n" +
               "/nbdist/\n" +
               "/.nb-gradle/\n" +
               "build/\n\n" +
               "### VS Code ###\n" +
               ".vscode/\n";
    }

    private void addToZip(ZipOutputStream zos, String fileName, String content) throws IOException {
        ZipEntry entry = new ZipEntry(fileName);
        zos.putNextEntry(entry);
        zos.write(content.getBytes());
        zos.closeEntry();
    }
}
