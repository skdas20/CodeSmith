# CodeSmith Deployment Guide

## 🚀 Deploy to Render (Recommended)

### Prerequisites
- GitHub account (✅ Already done!)
- Render account (free): https://render.com

---

## 📋 Step-by-Step Deployment

### 1. Create Render Account
1. Go to https://render.com
2. Click "Get Started"
3. Sign up with GitHub (easiest)
4. Authorize Render to access your repositories

### 2. Deploy Backend (Spring Boot)

#### A. From Render Dashboard:
1. Click "New +" → "Web Service"
2. Connect your GitHub repository: `skdas20/CodeSmith`
3. Configure:
   ```
   Name: codesmith-backend
   Environment: Java
   Branch: main
   Root Directory: (leave empty)
   Build Command: mvn clean install
   Start Command: java -jar target/codesmith-1.0.0.jar
   ```

#### B. Environment Variables:
Add these in Render dashboard:
```
JAVA_TOOL_OPTIONS = -Xmx512m
PORT = 8080
```

#### C. Plan:
- Select **Free** plan
- Click "Create Web Service"

#### D. Wait for deployment (5-10 minutes)
- Render will build and deploy automatically
- You'll get a URL like: `https://codesmith-backend.onrender.com`

### 3. Update Frontend API URL

After backend is deployed, update the frontend:

1. Note your Render backend URL
2. Update `src/main/resources/static/app.js`:
   ```javascript
   const API_URL = 'https://codesmith-backend.onrender.com/api';
   ```

3. Commit and push:
   ```bash
   git add .
   git commit -m "Update API URL for production"
   git push origin main
   ```

4. Render will auto-redeploy!

---

## 🌐 Access Your App

### Backend API:
```
https://codesmith-backend.onrender.com/api/health
```

### Frontend:
```
https://codesmith-backend.onrender.com/
```

(Spring Boot serves both!)

---

## ⚡ Important Notes

### Free Tier Limitations:
- ⚠️ **Spins down after 15 min of inactivity**
- ⚠️ First request after sleep takes ~30 seconds
- ✅ Auto-wakes up on request
- ✅ Unlimited requests when active

### Keep It Awake (Optional):
Use a service like **UptimeRobot** to ping your app every 10 minutes:
1. Go to https://uptimerobot.com
2. Add monitor: `https://codesmith-backend.onrender.com/api/health`
3. Set interval: 10 minutes

---

## 🔧 Troubleshooting

### Build Fails:
**Issue**: Maven build timeout
**Fix**: Increase build timeout in Render settings

### App Crashes:
**Issue**: Out of memory
**Fix**: Already set `-Xmx512m` (512MB limit)

### Slow First Load:
**Issue**: Free tier spins down
**Fix**: Normal behavior, or upgrade to paid ($7/month)

---

## 💰 Cost Breakdown

### Free Tier (Current):
- ✅ Free forever
- ✅ 512MB RAM
- ✅ Shared CPU
- ⚠️ Spins down after 15 min idle

### Paid Tier ($7/month):
- ✅ Always on (no spin down)
- ✅ 2GB RAM
- ✅ Dedicated CPU
- ✅ Custom domain support

---

## 🎯 Alternative: Railway.app

If Render doesn't work:

1. Go to https://railway.app
2. Sign in with GitHub
3. "New Project" → "Deploy from GitHub"
4. Select `skdas20/CodeSmith`
5. Add start command: `mvn spring-boot:run`
6. Deploy!

Railway gives $5 free credit/month.

---

## 📊 Deployment Checklist

Before deploying:
- ✅ Code pushed to GitHub
- ✅ API keys in application.properties
- ✅ render.yaml present (if using)
- ✅ .gitignore excludes target/

After deploying:
- ✅ Test health endpoint: `/api/health`
- ✅ Test frontend: Open root URL
- ✅ Generate a test project
- ✅ Verify download works

---

## 🚨 Security Notes

### API Keys Exposed:
⚠️ Your Gemini API keys are in `application.properties` and pushed to GitHub!

**Fix for Production:**
1. Remove keys from `application.properties`
2. Add as environment variables in Render:
   ```
   GEMINI_API_KEY_PRIMARY=AIzaSyA4_FZTjsN1B7dbriB1Q3f_FR6cfCO7GIs
   GEMINI_API_KEY_SECONDARY=AIzaSyCP_2h2ozoYvLJQSRbx6T5vzsq19MuAmDM
   ```

3. Update `GeminiService.java`:
   ```java
   @Value("${GEMINI_API_KEY_PRIMARY}")
   private String primaryApiKey;
   ```

4. Update `application.properties`:
   ```properties
   # Remove actual keys, use env vars
   gemini.api.key.primary=${GEMINI_API_KEY_PRIMARY}
   gemini.api.key.secondary=${GEMINI_API_KEY_SECONDARY}
   ```

---

## ✅ Success Metrics

Your deployment is successful when:
1. ✅ Backend URL returns 200 OK
2. ✅ `/api/health` responds
3. ✅ Frontend loads at root URL
4. ✅ Can generate a test project
5. ✅ ZIP downloads successfully

---

## 🎉 You're Live!

Once deployed:
- Share URL: `https://codesmith-backend.onrender.com`
- Add to README
- Update portfolio
- Tweet about it! 🐦

---

**Need Help?**
- Render Docs: https://render.com/docs
- Railway Docs: https://docs.railway.app
- Spring Boot on Render: https://render.com/docs/deploy-spring-boot
