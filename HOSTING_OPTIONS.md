# 🌐 CodeSmith Hosting Options

## Comparison Table

| Platform | Cost | Java Support | Ease | Recommendation |
|----------|------|--------------|------|----------------|
| **Render** | Free tier | ✅ Excellent | ⭐⭐⭐⭐⭐ | **BEST** ✅ |
| **Railway** | $5 credit/mo | ✅ Good | ⭐⭐⭐⭐ | Good alternative |
| **Heroku** | $7/month | ✅ Good | ⭐⭐⭐⭐ | No free tier |
| **Vercel** | Free | ❌ No Java | ⭐⭐ | Not suitable |
| **Netlify** | Free | ❌ No Java | ⭐⭐ | Not suitable |
| **AWS** | Pay as you go | ✅ Yes | ⭐⭐ | Complex |
| **Google Cloud** | Free $300 credit | ✅ Yes | ⭐⭐ | Complex |

---

## 🏆 Recommended: Render.com

### Why Render?
- ✅ **Free tier forever**
- ✅ Native Java/Spring Boot support
- ✅ Auto-deploys from GitHub
- ✅ HTTPS included
- ✅ Easy to use
- ✅ No credit card required for free tier

### Free Tier Specs:
- 512 MB RAM
- Shared CPU
- Spins down after 15 min inactivity
- Unlimited requests when active

### Deployment Steps:

1. **Go to Render**: https://render.com
2. **Sign up** with GitHub
3. **New Web Service** → Select `skdas20/CodeSmith`
4. **Configure**:
   - Name: `codesmith`
   - Environment: `Java`
   - Build: `mvn clean install`
   - Start: `java -jar target/codesmith-1.0.0.jar`
5. **Deploy!**

**Your app will be live at**: `https://codesmith.onrender.com`

---

## 🚂 Alternative: Railway.app

### Why Railway?
- ✅ $5 free credit/month
- ✅ Simple deployment
- ✅ Nice UI
- ✅ Good for Java

### Free Tier:
- $5 credit/month (enough for light usage)
- Pay only for what you use

### Deployment Steps:

1. **Go to Railway**: https://railway.app
2. **Sign in** with GitHub
3. **New Project** → Deploy from GitHub
4. **Select** `skdas20/CodeSmith`
5. **Add start command**: `mvn spring-boot:run`
6. **Deploy!**

---

## 🐳 Docker Deployment (Any Platform)

Already included `Dockerfile` in your repo!

### Use with:
- Render (Docker support)
- Railway (auto-detects Dockerfile)
- AWS ECS
- Google Cloud Run
- Any Docker host

### Build locally:
```bash
docker build -t codesmith .
docker run -p 8080:8080 codesmith
```

---

## ☁️ Cloud Platforms (Advanced)

### AWS (Amazon Web Services)
**Best for**: Production, scalability
**Cost**: ~$10-20/month
**Steps**:
1. Use Elastic Beanstalk (easiest)
2. Or EC2 + Docker
3. Or ECS with Fargate

### Google Cloud Platform
**Best for**: Free $300 credit (new accounts)
**Cost**: Free for 3 months, then ~$10-20/month
**Steps**:
1. Use Cloud Run (serverless)
2. Or App Engine
3. Or Compute Engine + Docker

### Azure
**Best for**: If you have Azure credits
**Cost**: ~$10-20/month
**Steps**:
1. Use App Service
2. Or Container Instances

---

## 🎯 Quick Start: Render (Recommended)

### 5-Minute Deployment:

```bash
# 1. Go to Render.com and sign up

# 2. New Web Service → Connect GitHub

# 3. Select: skdas20/CodeSmith

# 4. Settings:
Name: codesmith
Environment: Java
Build Command: mvn clean install
Start Command: java -jar target/codesmith-1.0.0.jar

# 5. Click "Create Web Service"

# 6. Wait 5-10 minutes...

# 7. DONE! Your app is live! 🎉
```

**Access at**: `https://codesmith.onrender.com`

---

## ⚠️ Important: Update API URL

After deployment, update the frontend API URL:

### For Render:
1. Your backend URL: `https://codesmith.onrender.com`

2. Update `src/main/resources/static/app.js`:
   ```javascript
   // Change from:
   const API_URL = 'http://localhost:8080/api';

   // To:
   const API_URL = 'https://codesmith.onrender.com/api';
   ```

3. Commit and push:
   ```bash
   git add .
   git commit -m "Update API URL for production"
   git push origin main
   ```

4. Render auto-redeploys!

---

## 💡 Pro Tips

### 1. Keep Free Tier Awake
Use **UptimeRobot** to ping every 10 minutes:
- URL: https://uptimerobot.com
- Monitor: `https://codesmith.onrender.com/api/health`
- Interval: 10 minutes

### 2. Environment Variables (Security)
Don't hardcode API keys! Use environment variables:

In Render dashboard, add:
```
GEMINI_API_KEY_PRIMARY=your_key_here
GEMINI_API_KEY_SECONDARY=your_key_here
```

Then update `application.properties`:
```properties
gemini.api.key.primary=${GEMINI_API_KEY_PRIMARY}
gemini.api.key.secondary=${GEMINI_API_KEY_SECONDARY}
```

### 3. Custom Domain (Optional)
Render supports custom domains (even on free tier):
- Buy domain (Namecheap, GoDaddy)
- Add to Render settings
- Update DNS records
- Done!

---

## 📊 Cost Comparison (Monthly)

| Usage Level | Render | Railway | Heroku | AWS |
|-------------|--------|---------|--------|-----|
| **Development** | FREE | $5 | $7 | $10 |
| **Light Production** | FREE* | $10 | $7 | $15 |
| **Medium Production** | $7 | $20 | $25 | $30 |
| **Heavy Production** | $25 | $50 | $50 | $100+ |

*Free tier spins down after 15 min idle

---

## ✅ Deployment Checklist

Before deploying:
- [ ] Code pushed to GitHub
- [ ] API keys ready (for env vars)
- [ ] Tested locally (`mvn spring-boot:run`)
- [ ] `.gitignore` excludes `target/`

After deploying:
- [ ] Test `/api/health` endpoint
- [ ] Test frontend loads
- [ ] Generate a test project
- [ ] Verify ZIP download works
- [ ] Add custom domain (optional)
- [ ] Set up monitoring (UptimeRobot)

---

## 🎉 Summary

**Quick & Free**: Use **Render.com** ✅
**More control**: Use **Railway** or **Heroku**
**Enterprise**: Use **AWS**, **GCP**, or **Azure**

**Recommended Path**:
1. Start with Render (free)
2. Test and iterate
3. Upgrade if needed ($7/month for always-on)
4. Scale to cloud platforms when popular

---

**Ready to deploy?** Follow `DEPLOYMENT.md` for step-by-step instructions! 🚀
