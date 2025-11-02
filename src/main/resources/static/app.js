const API_URL = 'https://codesmith-uj5w.onrender.com/api';

const projectNameInput = document.getElementById('projectName');
const generateBtn = document.getElementById('generateBtn');
const btnText = document.getElementById('btnText');
const statusMessage = document.getElementById('statusMessage');
const statusText = document.getElementById('statusText');
const errorMessage = document.getElementById('errorMessage');
const errorText = document.getElementById('errorText');

// Generate project on button click
generateBtn.addEventListener('click', generateProject);

// Generate project on Enter key
projectNameInput.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        generateProject();
    }
});

async function generateProject() {
    const projectName = projectNameInput.value.trim();

    if (!projectName) {
        showError('Please enter a project name');
        return;
    }

    // Update UI to loading state
    generateBtn.disabled = true;
    generateBtn.classList.add('opacity-75', 'cursor-not-allowed');
    btnText.textContent = 'Generating...';
    statusMessage.classList.remove('hidden');
    errorMessage.classList.add('hidden');

    const stages = [
        'Analyzing your project requirements...',
        'Generating Spring Boot backend...',
        'Creating frontend components...',
        'Generating SRS document...',
        'Creating design documentation...',
        'Preparing feasibility report...',
        'Compiling everything into a ZIP file...'
    ];

    let stageIndex = 0;
    const stageInterval = setInterval(() => {
        if (stageIndex < stages.length) {
            statusText.textContent = stages[stageIndex];
            stageIndex++;
        }
    }, 3000);

    try {
        const response = await fetch(`${API_URL}/generate`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ projectName })
        });

        clearInterval(stageInterval);

        if (!response.ok) {
            const errorData = await response.text();
            throw new Error(errorData || 'Failed to generate project');
        }

        // Download the ZIP file
        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = `${projectName.replace(/\s+/g, '-')}-codebase.zip`;
        document.body.appendChild(a);
        a.click();
        window.URL.revokeObjectURL(url);
        document.body.removeChild(a);

        // Show success
        statusText.textContent = '✓ Project generated successfully! Download started.';
        setTimeout(() => {
            resetUI();
        }, 3000);

    } catch (error) {
        clearInterval(stageInterval);
        showError(error.message);
        resetUI();
    }
}

function showError(message) {
    errorMessage.classList.remove('hidden');
    errorText.textContent = message;
    statusMessage.classList.add('hidden');
}

function resetUI() {
    generateBtn.disabled = false;
    generateBtn.classList.remove('opacity-75', 'cursor-not-allowed');
    btnText.textContent = 'Generate Project';
    setTimeout(() => {
        statusMessage.classList.add('hidden');
        errorMessage.classList.add('hidden');
    }, 3000);
}
