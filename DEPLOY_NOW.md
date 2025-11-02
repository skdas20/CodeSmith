# 🚀 Deploy CodeSmith NOW (Docker on Render)

## ⚡ Super Quick Guide (5 Steps)

---

## Step 1: Go to Render
👉 **https://render.com**
- Click "Get Started"
- Sign up with GitHub

---

## Step 2: Create Web Service
- Click **"New +"** → **"Web Service"**
- Find and select: **`skdas20/CodeSmith`**
- Click **"Connect"**

---

## Step 3: Configure
```
Name: codesmith
Environment: Docker
Branch: main
Instance Type: Free
```

That's it! Render auto-detects your Dockerfile.

---

## Step 4: Add Environment Variable
Click **"Advanced"** → Add:
```
PORT = 8080
```

Optional (for security):
```
GEMINI_API_KEY_PRIMARY = AIzaSyA4_FZTjsN1B7dbriB1Q3f_FR6cfCO7GIs
GEMINI_API_KEY_SECONDARY = AIzaSyCP_2h2ozoYvLJQSRbx6T5vzsq19MuAmDM
```

---

## Step 5: Deploy!
- Click **"Create Web Service"**
- Wait 10-15 minutes (first time)
- See "Live" status? **YOU'RE DONE!** 🎉

---

## 🌐 Your Live URL
```
https://codesmith.onrender.com
```
(or whatever name you chose)

---

## ✅ Test It

### Health Check:
```
https://codesmith.onrender.com/api/health
```

### Frontend:
```
https://codesmith.onrender.com/
```

### Generate Project:
Enter "Test System" → Click Generate → Download ZIP! ✅

---

## 🔄 Update API URL in Code

After first deploy, do this **ONCE**:

### 1. Edit `src/main/resources/static/app.js`:
```javascript
const API_URL = 'https://codesmith.onrender.com/api';
```

### 2. Push update:
```bash
git add .
git commit -m "Update API URL for production"
git push origin main
```

Render auto-redeploys! Wait 2-3 min.

---

## ⚠️ Free Tier Info

- ✅ FREE forever
- ⚠️ Sleeps after 15 min idle
- ⚠️ First wake: ~30 sec
- ✅ Perfect for demos/portfolio

---

## 💡 Keep It Awake (Optional)

**UptimeRobot** (free): https://uptimerobot.com

Monitor: `https://codesmith.onrender.com/api/health`
Interval: 5 minutes

Boom! Stays awake 24/7! 🎉

---

## 🎯 Visual Flow

```
GitHub (skdas20/CodeSmith)
    ↓
Render (connects repo)
    ↓
Dockerfile detected
    ↓
Docker builds image
    ↓
Deploys container
    ↓
LIVE at https://codesmith.onrender.com
    ↓
Update API URL in code
    ↓
Push to GitHub
    ↓
Auto-redeploys
    ↓
100% WORKING! 🚀
```

---

## 📱 What You'll See in Render

### During Build:
```
=== Building Docker image
Step 1/8: FROM maven:3.8.5-openjdk-17 AS build
Step 2/8: WORKDIR /app
...
Step 8/8: ENTRYPOINT ["java", "-jar", "app.jar"]
=== Build succeeded
=== Starting service
=== Deploy live
```

### When Live:
```
Status: ● Live
Latest Deploy: Success
URL: https://codesmith.onrender.com
```

---

## 🐛 If Something Goes Wrong

### Build Failed?
- Check logs in Render dashboard
- Common: Maven timeout → Click "Manual Deploy" → "Clear build cache & deploy"

### App Won't Start?
- Check PORT env variable is 8080
- Look at logs for Java errors

### 404 Error?
- Wait for "Live" status
- Try `/api/health` endpoint

---

## 💪 You Got This!

1. Go to Render.com
2. Connect GitHub
3. Select Docker
4. Deploy!
5. Update API URL
6. **BOOM! Live app!** 🎉

---

**Total Time**: 15 minutes
**Difficulty**: Easy ⭐⭐
**Cost**: FREE

---

## 🎊 After Deploy

✅ Add to portfolio
✅ Share on LinkedIn
✅ Update resume
✅ Show friends
✅ Tweet about it!

Your CodeSmith is **LIVE ON THE INTERNET!** 🌍

---

**Questions?** Check **RENDER_DOCKER_DEPLOYMENT.md** for detailed guide.
