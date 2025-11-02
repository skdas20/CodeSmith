package com.codesmith.controller;

import com.codesmith.dto.ProjectRequest;
import com.codesmith.service.CodeGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class ProjectController {

    @Autowired
    private CodeGenerationService codeGenerationService;

    @PostMapping("/generate")
    public ResponseEntity<?> generateProject(@RequestBody ProjectRequest request) {
        try {
            String projectName = request.getProjectName();
            if (projectName == null || projectName.trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Project name is required");
            }

            byte[] zipFile = codeGenerationService.generateProject(projectName.trim());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment",
                projectName.replaceAll("\\s+", "-") + "-codebase.zip");

            return new ResponseEntity<>(zipFile, headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error generating project: " + e.getMessage());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("CodeSmith API is running!");
    }
}
