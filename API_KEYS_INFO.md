# API Keys Configuration

## Current Configuration

The Gemini API keys are already configured in the application:

**Primary Key**: `AIzaSyA4_FZTjsN1B7dbriB1Q3f_FR6cfCO7GIs`
**Secondary Key**: `AIzaSyCP_2h2ozoYvLJQSRbx6T5vzsq19MuAmDM`

## How It Works

1. The application first tries the **primary key**
2. If it hits rate limits (429 error), it automatically switches to the **secondary key**
3. This ensures uninterrupted service even when one key reaches its quota

## Changing API Keys

If you need to use different API keys:

1. Open `src/main/resources/application.properties`
2. Update the following lines:
   ```properties
   gemini.api.key.primary=YOUR_NEW_PRIMARY_KEY
   gemini.api.key.secondary=YOUR_NEW_SECONDARY_KEY
   ```
3. Restart the application

## Getting Your Own API Keys

1. Visit: https://makersuite.google.com/app/apikey
2. Sign in with your Google account
3. Click "Create API Key"
4. Copy the generated key
5. Update the `application.properties` file

## Rate Limits

- Free tier: 60 requests per minute
- With two keys: Effectively doubles your capacity
- The app handles switching automatically

## Security Note

⚠️ **Important**: For production deployment:
- Never commit API keys to public repositories
- Use environment variables instead
- Consider using a secrets management service
- Rotate keys regularly
