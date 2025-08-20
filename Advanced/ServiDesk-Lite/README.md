# 🚀 ServiDesk-Lite

A lightweight **Service Desk API** inspired by ServiceNow.  
Built with **.NET 9**, **EF Core**, **SQLite**, **Swagger**, and **Docker**.

---

## ✨ Features
- 🎫 **Ticket Management** (create, update, delete, list tickets)
- ⏱ **SLA tracking** (breach flag stored in DB)
- 🗄 **EF Core with SQLite** for persistence
- 📖 **Swagger UI** auto-generated docs
- 🐳 **Docker support** (Dockerfile + docker-compose)
- ✅ **Unit tests** included with `xUnit`

---

## 🛠 Tech Stack
- **.NET 9**
- **Entity Framework Core 9**
- **SQLite** (lightweight DB)
- **Swagger / Swashbuckle**
- **Docker + docker-compose**

---

## 🚦 Getting Started

### 1️⃣ Run in Development (local)
```bash
# Restore dependencies
dotnet restore

# Apply migrations
dotnet ef database update --project src/ServiDesk.Infrastructure --startup-project src/ServiDesk.Api

# Run API
dotnet run --project src/ServiDesk.Api
