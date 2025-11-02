# Testing Guide

## Quick Test

### 1. Start the Application

```bash
mvn spring-boot:run
```

Wait for the message:
```
Started CodeSmithApplication in X.XXX seconds
```

### 2. Test the Backend API

Open a new terminal and test the health endpoint:

```bash
curl http://localhost:8080/api/health
```

Expected response: `"CodeSmith API is running!"`

### 3. Test the Frontend

Open your browser and go to:
```
http://localhost:8080
```

You should see:
- Purple gradient background
- "CodeSmith" title with code icon
- Glass-effect input card
- Text input field
- Generate button

### 4. Test Project Generation

1. In the text field, enter: `Hospital Management System`
2. Click "Generate Project"
3. Watch the status messages update
4. Wait for the ZIP file to download (1-3 minutes)
5. Extract the ZIP and examine the contents

## Expected Output Structure

```
Hospital-Management-System/
├── backend/
│   └── generated-code.txt        (Spring Boot code)
├── frontend/
│   └── generated-code.txt        (HTML/CSS/JS code)
├── docs/
│   ├── SRS.txt                   (Requirements doc)
│   ├── Design-Document.txt       (Design doc)
│   └── Feasibility-Report.txt    (Feasibility report)
└── README.md                     (Setup instructions)
```

## Test Cases

### Test Case 1: Basic Generation
- **Input**: "Hospital Management System"
- **Expected**: ZIP with all components
- **Status**: ✅ Should work

### Test Case 2: Simple Project
- **Input**: "Todo App"
- **Expected**: ZIP with simpler codebase
- **Status**: ✅ Should work

### Test Case 3: Complex Project
- **Input**: "Enterprise Resource Planning System with Inventory, HR, and Finance modules"
- **Expected**: Comprehensive ZIP with detailed code
- **Status**: ✅ Should work

### Test Case 4: Empty Input
- **Input**: "" (empty)
- **Expected**: Error message "Project name is required"
- **Status**: ✅ Should be handled

### Test Case 5: API Key Switching
- **Input**: Make 10+ requests quickly
- **Expected**: Automatic switch to secondary key if primary hits limit
- **Status**: ✅ Auto-handled

## Manual API Testing

### Using curl (Windows Git Bash or Linux/Mac)

```bash
curl -X POST http://localhost:8080/api/generate \
  -H "Content-Type: application/json" \
  -d "{\"projectName\":\"Test Project\"}" \
  --output test-project.zip
```

### Using PowerShell (Windows)

```powershell
Invoke-WebRequest -Uri "http://localhost:8080/api/generate" `
  -Method POST `
  -Headers @{"Content-Type"="application/json"} `
  -Body '{"projectName":"Test Project"}' `
  -OutFile "test-project.zip"
```

### Using Postman

1. Create new POST request
2. URL: `http://localhost:8080/api/generate`
3. Headers: `Content-Type: application/json`
4. Body (raw JSON):
   ```json
   {
     "projectName": "Test Project"
   }
   ```
5. Send and save response as ZIP

## Browser Console Testing

Open browser console (F12) and run:

```javascript
fetch('http://localhost:8080/api/generate', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json'
    },
    body: JSON.stringify({
        projectName: 'Console Test Project'
    })
})
.then(response => response.blob())
.then(blob => {
    const url = window.URL.createObjectURL(blob);
    const a = document.createElement('a');
    a.href = url;
    a.download = 'console-test-project.zip';
    a.click();
});
```

## Performance Testing

### Expected Times:
- Health check: < 100ms
- Project generation: 30-180 seconds
- ZIP download: < 5 seconds

### Monitor Logs:
Watch the console output for:
- API calls to Gemini
- Generation progress
- Any errors or warnings

## Troubleshooting

### Issue: Port 8080 already in use
**Solution**:
```properties
# In application.properties
server.port=8081
```

### Issue: API rate limit reached
**Solution**:
- Wait 1 minute
- The app will auto-switch keys
- Or update API keys in `application.properties`

### Issue: Generation takes too long
**Cause**:
- Gemini API might be slow
- Complex project requires more processing

**Solution**:
- Wait patiently (up to 3 minutes)
- Try a simpler project name first

### Issue: Download doesn't start
**Solution**:
- Check browser console for errors
- Ensure backend is running
- Check CORS configuration

### Issue: ZIP file is corrupted
**Cause**:
- Network interruption
- Server error during generation

**Solution**:
- Try generating again
- Check server logs for errors

## Validation Checklist

After generation, validate the ZIP contains:

- ✅ backend/generated-code.txt exists and has Java code
- ✅ frontend/generated-code.txt exists and has HTML/CSS/JS
- ✅ docs/SRS.txt exists and has requirements
- ✅ docs/Design-Document.txt exists and has design info
- ✅ docs/Feasibility-Report.txt exists and has analysis
- ✅ README.md exists and has setup instructions
- ✅ All files have actual content (not empty)
- ✅ Code appears to be project-specific (not generic)

## Success Criteria

Your CodeSmith installation is working correctly if:

1. ✅ Application starts without errors
2. ✅ Health endpoint responds
3. ✅ Frontend loads with correct styling
4. ✅ Can submit a project name
5. ✅ Status updates appear during generation
6. ✅ ZIP file downloads successfully
7. ✅ ZIP contains all expected files
8. ✅ Generated code is relevant to project name

## Next Steps After Testing

1. Try different project types
2. Examine generated code quality
3. Extract and run a generated project
4. Provide feedback for improvements
5. Share with team members
