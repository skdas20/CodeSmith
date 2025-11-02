# 🐳 Deploy CodeSmith to Render (Docker)

## ✅ Step-by-Step Guide

Since Render uses Docker for Java apps, here's the exact deployment process:

---

## 📋 Prerequisites

- ✅ GitHub account (done!)
- ✅ Render account (free): https://render.com
- ✅ Dockerfile already in repo (done!)

---

## 🚀 Deployment Steps

### 1. Create Render Account

1. Go to: **https://render.com**
2. Click **"Get Started"**
3. Sign up with **GitHub** (easiest option)
4. **Authorize** Render to access your repositories

---

### 2. Create New Web Service

1. From Render Dashboard, click **"New +"**
2. Select **"Web Service"**
3. Connect your repository: **`skdas20/CodeSmith`**
4. Click **"Connect"**

---

### 3. Configure Service

Fill in these settings:

#### Basic Settings:
```
Name: codesmith
Region: Oregon (or closest to you)
Branch: main
Root Directory: (leave blank)
```

#### Environment:
```
Environment: Docker
```

#### Build Settings:
Render will **auto-detect** your Dockerfile - no commands needed!

#### Instance Type:
```
Instance Type: Free
```

#### Advanced (Optional):
```
Auto-Deploy: Yes (automatically deploys on git push)
```

---

### 4. Environment Variables (Important!)

Click **"Advanced"** → **"Add Environment Variable"**

Add these:

```
PORT = 8080
```

For security, also add your API keys:
```
GEMINI_API_KEY_PRIMARY = AIzaSyA4_FZTjsN1B7dbriB1Q3f_FR6cfCO7GIs
GEMINI_API_KEY_SECONDARY = AIzaSyCP_2h2ozoYvLJQSRbx6T5vzsq19MuAmDM
```

---

### 5. Create Web Service

1. Review all settings
2. Click **"Create Web Service"**
3. Render will start building your Docker image (10-15 minutes first time)

---

### 6. Monitor Deployment

Watch the logs in Render dashboard:
```
Building Docker image...
Installing dependencies...
Building application...
Starting service...
✓ Deploy succeeded!
```

When you see **"Live"** status, your app is deployed! 🎉

---

### 7. Get Your URL

Your app will be live at:
```
https://codesmith.onrender.com
```

Or whatever name you chose:
```
https://YOUR-SERVICE-NAME.onrender.com
```

---

## 🔧 Update Frontend API URL

After deployment, update your code:

### 1. Edit `src/main/resources/static/app.js`:

```javascript
// OLD:
const API_URL = 'http://localhost:8080/api';

// NEW (replace with your actual Render URL):
const API_URL = 'https://codesmith.onrender.com/api';
```

### 2. Commit and push:

```bash
git add src/main/resources/static/app.js
git commit -m "Update API URL for production"
git push origin main
```

### 3. Render auto-redeploys!

Wait 2-3 minutes, and your update will be live!

---

## ✅ Test Your Deployment

### 1. Health Check:
```
https://codesmith.onrender.com/api/health
```

Should return: `"CodeSmith API is running!"`

### 2. Frontend:
```
https://codesmith.onrender.com/
```

Should show the purple gradient UI!

### 3. Generate a Test Project:
- Enter: "Test System"
- Click: "Generate Project"
- Should download a ZIP file

---

## ⚠️ Free Tier Limitations

### What to Expect:
- ✅ **Free forever**
- ✅ 512 MB RAM
- ✅ Unlimited bandwidth
- ⚠️ **Spins down after 15 min of inactivity**
- ⚠️ **First request after sleep: ~30 seconds**
- ⚠️ **Build time: 10-15 minutes** (subsequent builds faster)

### Cold Start Behavior:
```
First request → Wakes up (30 sec) → Fast responses
15 min idle → Spins down
Next request → Wakes up (30 sec) → Fast again
```

---

## 💡 Keep It Awake (Optional)

Use **UptimeRobot** to ping every 14 minutes (keeps it awake):

### Setup:
1. Go to: **https://uptimerobot.com** (free)
2. Sign up (free account)
3. **Add New Monitor**:
   ```
   Monitor Type: HTTP(s)
   Friendly Name: CodeSmith
   URL: https://codesmith.onrender.com/api/health
   Monitoring Interval: 5 minutes (free tier)
   ```
4. Save

Your app will now stay awake during active hours! 🎉

---

## 🔐 Security Best Practice

### Move API Keys to Environment Variables:

#### 1. In Render Dashboard:
Go to **Environment** tab, add:
```
GEMINI_API_KEY_PRIMARY = your_key_here
GEMINI_API_KEY_SECONDARY = your_key_here
```

#### 2. Update `application.properties`:
```properties
# Use environment variables
gemini.api.key.primary=${GEMINI_API_KEY_PRIMARY:AIzaSyA4_FZTjsN1B7dbriB1Q3f_FR6cfCO7GIs}
gemini.api.key.secondary=${GEMINI_API_KEY_SECONDARY:AIzaSyCP_2h2ozoYvLJQSRbx6T5vzsq19MuAmDM}
```

The `:default_value` ensures it works locally too!

---

## 🐛 Troubleshooting

### Build Failed:
**Check logs** in Render dashboard:
- Maven dependency issues? → Check `pom.xml`
- Docker build failed? → Check `Dockerfile`
- Out of memory? → Normal for free tier, retry

### App Won't Start:
- Check port is 8080 (PORT env var)
- Check logs for Java errors
- Ensure Dockerfile EXPOSE 8080

### Slow First Load:
- Normal! Free tier spins down
- Use UptimeRobot to keep awake
- Or upgrade to paid ($7/month)

### Can't Access:
- Wait for "Live" status in dashboard
- Check URL is correct
- Try `/api/health` endpoint first

---

## 📊 Render Dashboard

### Useful Features:
- **Logs**: Real-time application logs
- **Metrics**: CPU, Memory usage
- **Shell**: SSH into your container
- **Environment**: Manage env variables
- **Settings**: Configure service

---

## 🎯 Quick Command Reference

### Deploy/Redeploy:
```bash
# Just push to GitHub:
git add .
git commit -m "Your changes"
git push origin main

# Render auto-deploys!
```

### Manual Redeploy:
In Render Dashboard → Click **"Manual Deploy"** → **"Deploy latest commit"**

### View Logs:
Render Dashboard → **Logs** tab

### Restart Service:
Render Dashboard → **Manual Deploy** → **"Clear build cache & deploy"**

---

## 💰 Upgrade Options

If you need more:

### Starter ($7/month):
- ✅ **Always on** (no spin down!)
- ✅ 512 MB RAM
- ✅ Faster builds
- ✅ Custom domain

### Standard ($25/month):
- ✅ 2 GB RAM
- ✅ Dedicated resources
- ✅ Priority support

---

## ✅ Deployment Checklist

### Before Deploy:
- [x] Dockerfile exists
- [x] Code on GitHub
- [x] render.yaml configured
- [ ] Render account created
- [ ] Repository connected

### After Deploy:
- [ ] Check "Live" status
- [ ] Test `/api/health`
- [ ] Test frontend loads
- [ ] Update API URL in code
- [ ] Generate test project
- [ ] Set up UptimeRobot (optional)
- [ ] Add custom domain (optional)

---

## 🎉 Success!

Once deployed, your CodeSmith is live at:
```
https://codesmith.onrender.com
```

Share it:
- Add to your portfolio
- Update your resume
- Share on LinkedIn/Twitter
- Show to friends! 🚀

---

## 📝 Summary

**Deploy with Docker on Render**:
1. Connect GitHub repo
2. Select "Docker" environment
3. Render auto-detects Dockerfile
4. Deploy!
5. Update API URL
6. Done! 🎉

**Total time**: ~15 minutes (first deployment)
**Cost**: FREE forever (with spin-down)
**Upgrade**: $7/month for always-on

---

**Need help?** Check Render docs: https://render.com/docs/docker
