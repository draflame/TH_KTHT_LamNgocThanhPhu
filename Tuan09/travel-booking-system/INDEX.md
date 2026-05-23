# 📚 DOCUMENTATION INDEX

**Travel Booking System - Orchestration-Driven SOA**

Chào mừng! Đây là hệ thống đặt tour hoàn chỉnh cho **5 người** làm việc độc lập.

---

## 🚀 START HERE (Choose one)

### ⚡ I want to start in 5 minutes

👉 [QUICK_START.md](QUICK_START.md)

### 📖 I want detailed step-by-step guide

👉 [GETTING_STARTED.md](GETTING_STARTED.md)

### 👥 I want to know my role (for 5 team members)

👉 [TEAM_ASSIGNMENT.md](TEAM_ASSIGNMENT.md)

---

## 📑 ALL DOCUMENTATION

### 🌍 Overview

- **[README.md](README.md)** - Main project overview
- **[QUICK_START.md](QUICK_START.md)** - Setup in 5 minutes
- **[GETTING_STARTED.md](GETTING_STARTED.md)** - Detailed setup guide

### 🏗️ Architecture & Design

- **[ARCHITECTURE.md](ARCHITECTURE.md)** - System architecture, patterns, best practices
- **[PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)** - Folder structure and file organization

### 👥 Team & Assignment

- **[TEAM_ASSIGNMENT.md](TEAM_ASSIGNMENT.md)** - Responsibilities for 5 people
  - Người 1: Frontend (ReactJS)
  - Người 2: Orchestrator Service
  - Người 3: User Service
  - Người 4: Tour Service
  - Người 5: Booking + Payment Service

### 🧪 Testing & API

- **[API_TESTING.md](API_TESTING.md)** - cURL, Postman examples
- **[TESTING.md](TESTING.md)** - Complete testing guide

### 📦 Individual Service Documentation

- [frontend/README.md](frontend/README.md) - Frontend details
- [orchestrator-service/README.md](orchestrator-service/README.md) - Orchestrator details
- [user-service/README.md](user-service/README.md) - User Service details
- [tour-service/README.md](tour-service/README.md) - Tour Service details
- [booking-payment-service/README.md](booking-payment-service/README.md) - Booking & Payment details

---

## 🎯 Quick Navigation

### For Setup & Running

1. First time? → [QUICK_START.md](QUICK_START.md) (5 min)
2. Need details? → [GETTING_STARTED.md](GETTING_STARTED.md)
3. Using Docker? → See docker-compose.yml

### For Understanding Architecture

1. What is Orchestration? → [ARCHITECTURE.md](ARCHITECTURE.md)
2. How is code organized? → [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md)
3. What's the flow? → [ARCHITECTURE.md](ARCHITECTURE.md#flow-chi-tiết-book-tour)

### For Team Development

1. What's my role? → [TEAM_ASSIGNMENT.md](TEAM_ASSIGNMENT.md)
2. What should I build? → Find your person number
3. How do I test? → [API_TESTING.md](API_TESTING.md)

### For Testing

1. How to test API? → [API_TESTING.md](API_TESTING.md)
2. Complete testing guide? → [TESTING.md](TESTING.md)
3. Test scenarios? → [TESTING.md](TESTING.md#test-scenarios)

---

## 📊 System Overview

```
Frontend (React 3000)
    ↓ Only HTTP calls
Orchestrator (8080)
    ├→ User Service (8081)
    ├→ Tour Service (8082)
    ├→ Booking Service (8083)
    └→ Payment Service (8084)
```

**Key Principle:** Services NEVER call each other directly. Only Orchestrator coordinates.

---

## 📋 Checklist

### Before Starting

- [ ] Node.js v16+ installed
- [ ] npm installed
- [ ] Read QUICK_START.md or GETTING_STARTED.md
- [ ] Choose: Docker or Manual setup

### During Development

- [ ] Each person works on assigned service
- [ ] Follow TEAM_ASSIGNMENT.md
- [ ] Test your service endpoints
- [ ] Test integration with Orchestrator
- [ ] Use API_TESTING.md for examples

### Before Deployment

- [ ] All services running
- [ ] Can login successfully
- [ ] Can view tours
- [ ] Can complete booking flow
- [ ] Payment processes (success/fail)
- [ ] No console errors
- [ ] Logs are clear

---

## 🔍 Find What You Need

### "How do I...?"

**...start the system?**
→ [QUICK_START.md](QUICK_START.md) or [GETTING_STARTED.md](GETTING_STARTED.md#⚡-cách-1-chạy-từng-service-riêng-dễ-nhất)

**...set up Frontend?**
→ [TEAM_ASSIGNMENT.md](TEAM_ASSIGNMENT.md#👤-người-1-frontend-reactjs) or [frontend/README.md](frontend/README.md)

**...set up User Service?**
→ [TEAM_ASSIGNMENT.md](TEAM_ASSIGNMENT.md#👥-người-3-user-service-expressjs) or [user-service/README.md](user-service/README.md)

**...understand the orchestration flow?**
→ [ARCHITECTURE.md](ARCHITECTURE.md#flow-chi-tiết-book-tour)

**...test an API?**
→ [API_TESTING.md](API_TESTING.md)

**...run all services together?**
→ [GETTING_STARTED.md](GETTING_STARTED.md#🐳-cách-2-chạy-với-docker-compose)

**...debug issues?**
→ [GETTING_STARTED.md](GETTING_STARTED.md#🔍-debugging) or [TESTING.md](TESTING.md#debugging-tips)

---

## 🌟 Key Features

✅ **Orchestration-Driven SOA** - Centralized orchestrator coordinates all services  
✅ **5 Independent Services** - Each person works on one service  
✅ **REST API** - Simple HTTP communication  
✅ **Mock Data** - No database needed  
✅ **Random Payment** - 80% success rate for testing  
✅ **Complete Documentation** - Everything is documented  
✅ **Docker Support** - Run with docker-compose  
✅ **Easy Testing** - Postman/cURL examples

---

## 📞 Support Resources

| Need                 | Document                   |
| -------------------- | -------------------------- |
| Quick setup (5 min)  | QUICK_START.md             |
| Detailed setup       | GETTING_STARTED.md         |
| Architecture details | ARCHITECTURE.md            |
| My role in team      | TEAM_ASSIGNMENT.md         |
| API examples         | API_TESTING.md             |
| Complete testing     | TESTING.md                 |
| File organization    | PROJECT_STRUCTURE.md       |
| Service details      | Individual README.md files |

---

## 🎓 Learning Path

### Beginner

1. Read: [README.md](README.md)
2. Setup: [QUICK_START.md](QUICK_START.md)
3. Try: Login and browse tours

### Intermediate

1. Read: [ARCHITECTURE.md](ARCHITECTURE.md)
2. Read: [TEAM_ASSIGNMENT.md](TEAM_ASSIGNMENT.md) - Find your role
3. Setup your assigned service
4. Test API: [API_TESTING.md](API_TESTING.md)

### Advanced

1. Understand: Full [ARCHITECTURE.md](ARCHITECTURE.md)
2. Extend: Add new features
3. Optimize: Performance improvements
4. Deploy: Docker/Kubernetes

---

## 💡 Tips

- **Start simple:** Use Docker Compose for first-time setup
- **Read once:** ARCHITECTURE.md explains everything
- **Test often:** Use Postman/cURL to verify APIs
- **Debug easily:** Check logs in terminal windows
- **Scale later:** Once working, add databases/caching

---

## 🚀 Quick Links

| Purpose               | Link                                         |
| --------------------- | -------------------------------------------- |
| **🏃 Start in 5 min** | [QUICK_START.md](QUICK_START.md)             |
| **🚗 Detailed guide** | [GETTING_STARTED.md](GETTING_STARTED.md)     |
| **👥 Team roles**     | [TEAM_ASSIGNMENT.md](TEAM_ASSIGNMENT.md)     |
| **🏗️ Architecture**   | [ARCHITECTURE.md](ARCHITECTURE.md)           |
| **📂 File structure** | [PROJECT_STRUCTURE.md](PROJECT_STRUCTURE.md) |
| **🧪 API testing**    | [API_TESTING.md](API_TESTING.md)             |
| **🔬 Full testing**   | [TESTING.md](TESTING.md)                     |

---

## ❓ FAQ

**Q: Can we start before everyone is ready?**
A: Yes! Your service can run independently. Test with cURL or Postman.

**Q: What if my service fails?**
A: Check logs, ensure port is available, verify API endpoints.

**Q: Can we use a real database?**
A: Yes! Replace mock data with database calls. But start with mocks first.

**Q: How do we integrate?**
A: Each person ensures their service works. Orchestrator will call them all.

**Q: What about authentication?**
A: For now, mock login. In production, add JWT tokens.

**Q: Can we modify the flow?**
A: Yes! This is a template. Adapt to your needs.

---

## 🎉 You're All Set!

Pick a starting point above and begin!

**Recommended order:**

1. Read this file (you're doing it! ✓)
2. Read [QUICK_START.md](QUICK_START.md) (5 min)
3. Read [TEAM_ASSIGNMENT.md](TEAM_ASSIGNMENT.md) (find your role)
4. Start your assigned service

Good luck! 🚀

---

**Last updated:** May 2026  
**Version:** 1.0  
**For:** Learning Orchestration-Driven SOA Architecture
