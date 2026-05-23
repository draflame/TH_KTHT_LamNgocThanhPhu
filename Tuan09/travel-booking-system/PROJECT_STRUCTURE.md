# 📂 PROJECT STRUCTURE

```
travel-booking-system/
│
├── 📄 README.md                          ← Start here
├── 📄 QUICK_START.md                     ← 5 min setup
├── 📄 GETTING_STARTED.md                 ← Detailed guide
├── 📄 ARCHITECTURE.md                    ← System architecture
├── 📄 TEAM_ASSIGNMENT.md                 ← For 5 team members
├── 📄 API_TESTING.md                     ← API test examples
├── 📄 TESTING.md                         ← Testing guide
├── 📄 docker-compose.yml                 ← Docker orchestration
├── 📄 start-all.sh                       ← Bash script to start all
├── 📄 start-all.bat                      ← Batch script for Windows
│
├── 📁 frontend/                          ⭐ NGƯỜI 1 (ReactJS)
│   ├── 📄 package.json
│   ├── 📄 Dockerfile
│   ├── 📄 .gitignore
│   ├── 📄 README.md
│   ├── public/
│   │   └── index.html
│   └── src/
│       ├── 📄 index.js                   ← Entry point
│       ├── 📄 App.js                     ← Main component
│       ├── 📄 index.css                  ← Global styles
│       ├── components/
│       │   ├── Login.js                  ← Login page
│       │   ├── TourList.js               ← Tours list
│       │   ├── BookingForm.js            ← Booking form
│       │   └── BookingSuccess.js         ← Success page
│       ├── pages/                        ← Additional pages
│       ├── services/
│       │   └── api.js                    ← Orchestrator API calls
│       └── styles/
│           ├── Login.css
│           ├── TourList.css
│           ├── BookingForm.css
│           └── BookingSuccess.css
│
├── 📁 orchestrator-service/              ⭐ NGƯỜI 2 (Express)
│   ├── 📄 package.json
│   ├── 📄 Dockerfile
│   ├── 📄 .gitignore
│   ├── 📄 README.md
│   └── src/
│       └── 📄 index.js                   ← Main service file
│           ├── POST /login               (Forward to User Service)
│           ├── GET /tours                (Forward to Tour Service)
│           ├── GET /tours/:id            (Forward to Tour Service)
│           └── POST /book-tour           (⭐ MAIN ORCHESTRATION FLOW)
│
├── 📁 user-service/                      ⭐ NGƯỜI 3 (Express)
│   ├── 📄 package.json
│   ├── 📄 Dockerfile
│   ├── 📄 .gitignore
│   ├── 📄 README.md
│   └── src/
│       └── 📄 index.js                   ← Main service file
│           ├── POST /login               (Authentication)
│           ├── GET /users/:id            (Get user info)
│           ├── POST /users               (Register)
│           └── Mock data: users[]
│
├── 📁 tour-service/                      ⭐ NGƯỜI 4 (Express)
│   ├── 📄 package.json
│   ├── 📄 Dockerfile
│   ├── 📄 .gitignore
│   ├── 📄 README.md
│   └── src/
│       └── 📄 index.js                   ← Main service file
│           ├── GET /tours                (List all tours)
│           ├── GET /tours/:id            (Tour detail)
│           └── Mock data: tours[]
│
└── 📁 booking-payment-service/           ⭐ NGƯỜI 5 (Express)
    ├── 📄 package.json
    ├── 📄 Dockerfile.booking
    ├── 📄 Dockerfile.payment
    ├── 📄 .gitignore
    ├── 📄 README.md
    └── src/
        ├── 📄 booking-service.js         ← Booking Service (8083)
        │   ├── POST /bookings            (Create booking)
        │   ├── GET /bookings/:id         (Get booking)
        │   └── Mock data: bookings[]
        │
        └── 📄 payment-service.js         ← Payment Service (8084)
            ├── POST /payments            (Process payment - 80% success)
            ├── GET /payments/:id         (Get payment)
            └── Mock data: payments[]
```

---

## 📊 Services Map

| Service         | Port | Person  | Tech    | Role            |
| --------------- | ---- | ------- | ------- | --------------- |
| Frontend        | 3000 | Người 1 | React   | UI/UX           |
| Orchestrator    | 8080 | Người 2 | Express | Orchestration   |
| User Service    | 8081 | Người 3 | Express | Authentication  |
| Tour Service    | 8082 | Người 4 | Express | Tour Management |
| Booking Service | 8083 | Người 5 | Express | Booking         |
| Payment Service | 8084 | Người 5 | Express | Payment         |

---

## 📝 File Organization

### Entry Points

- `frontend/src/index.js` - React app
- `user-service/src/index.js` - User Service
- `tour-service/src/index.js` - Tour Service
- `booking-payment-service/src/booking-service.js` - Booking
- `booking-payment-service/src/payment-service.js` - Payment
- `orchestrator-service/src/index.js` - Orchestrator

### Configuration Files

- `package.json` - Dependencies & scripts
- `Dockerfile` - Docker image
- `.gitignore` - Git ignore

### Documentation

- `README.md` - Main overview
- `QUICK_START.md` - 5 min setup
- `GETTING_STARTED.md` - Detailed guide
- `ARCHITECTURE.md` - System design
- `TEAM_ASSIGNMENT.md` - Responsibilities
- `API_TESTING.md` - API examples
- `TESTING.md` - Testing guide

---

## 🔄 Data Flow

```
User (Browser)
    ↓
Frontend (React 3000)
    │ HTTP REST
    ↓
Orchestrator (8080)
    ├─ HTTP REST → User Service (8081)
    ├─ HTTP REST → Tour Service (8082)
    ├─ HTTP REST → Booking Service (8083)
    └─ HTTP REST → Payment Service (8084)
    ↓
Response to Frontend
    ↓
UI Update
```

---

## 🎯 Key Files by Role

### Người 1 (Frontend)

- `frontend/src/App.js` - Main component
- `frontend/src/services/api.js` - Orchestrator calls
- `frontend/src/components/` - UI components
- `frontend/src/styles/` - CSS files

### Người 2 (Orchestrator)

- `orchestrator-service/src/index.js` - Main logic
  - Login forwarding
  - Tours forwarding
  - **Book tour orchestration flow**

### Người 3 (User Service)

- `user-service/src/index.js` - User management
  - POST /login
  - GET /users/:id
  - POST /users

### Người 4 (Tour Service)

- `tour-service/src/index.js` - Tour management
  - GET /tours
  - GET /tours/:id

### Người 5 (Booking + Payment)

- `booking-payment-service/src/booking-service.js`
  - POST /bookings
  - GET /bookings/:id
- `booking-payment-service/src/payment-service.js`
  - POST /payments (80% success)
  - GET /payments/:id

---

## 📦 Dependencies

### Frontend

```json
{
  "react": "^18.2.0",
  "react-dom": "^18.2.0",
  "axios": "^1.6.0",
  "react-router-dom": "^6.0.0"
}
```

### All Services

```json
{
  "express": "^4.18.2",
  "cors": "^2.8.5",
  "body-parser": "^1.20.2",
  "axios": "^1.6.0" (Only Orchestrator)
}
```

---

## 🚀 Quick Commands

```bash
# Clone & Setup
git clone <repo>
cd travel-booking-system

# Option 1: Docker Compose
docker-compose up -d

# Option 2: Manual - Start services in separate terminals
cd frontend && npm install && npm start
cd orchestrator-service && npm install && npm start
cd user-service && npm install && npm start
cd tour-service && npm install && npm start
cd booking-payment-service && npm install && npm run booking
cd booking-payment-service && npm install && npm run payment

# Access
http://localhost:3000 (Frontend)
http://localhost:8080 (Orchestrator)

# Login
user1 / 123456
```

---

## 📋 Checklist Before Starting

- [ ] Node.js installed (v16+)
- [ ] npm installed
- [ ] All services structure created
- [ ] `npm install` run in each service
- [ ] Ports available (3000, 8080-8084)
- [ ] CORS enabled in all services
- [ ] Mock data initialized
- [ ] Error handling implemented
- [ ] Logging implemented

---

## 🔗 File Cross-References

**Frontend calls these endpoints:**

- `orchestrator-service/src/index.js` - All POST/GET requests

**Orchestrator calls these services:**

- `user-service/src/index.js` - User validation
- `tour-service/src/index.js` - Tour info
- `booking-payment-service/src/booking-service.js` - Create booking
- `booking-payment-service/src/payment-service.js` - Process payment

**Each service has:**

- `index.js` - Server & API endpoints
- `package.json` - Dependencies
- `Dockerfile` - Containerization
- `README.md` - Service documentation

---

## 🎯 Integration Points

1. **Frontend ↔ Orchestrator**
   - File: `frontend/src/services/api.js`
   - URL: `http://localhost:8080`

2. **Orchestrator ↔ User Service**
   - File: `orchestrator-service/src/index.js`
   - URL: `http://localhost:8081`

3. **Orchestrator ↔ Tour Service**
   - File: `orchestrator-service/src/index.js`
   - URL: `http://localhost:8082`

4. **Orchestrator ↔ Booking Service**
   - File: `orchestrator-service/src/index.js`
   - URL: `http://localhost:8083`

5. **Orchestrator ↔ Payment Service**
   - File: `orchestrator-service/src/index.js`
   - URL: `http://localhost:8084`

---

**Happy Coding! 🚀**
