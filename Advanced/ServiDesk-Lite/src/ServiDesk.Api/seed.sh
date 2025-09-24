#!/bin/bash
API="http://localhost:5216/api/tickets"
echo "Seeding dummy tickets into $API ..."

curl -s -X POST $API -H "Content-Type: application/json" -d '{
  "title":"Outlook stuck on sending",
  "description":"Emails not going out since morning",
  "priority":1, "category":"Software",
  "status":"Open", "assignee":"Unassigned"
}' > /dev/null

curl -s -X POST $API -H "Content-Type: application/json" -d '{
  "title":"VPN disconnects",
  "description":"Drops every 15 minutes",
  "priority":2, "category":"Network",
  "status":"InProgress", "assignee":"Alex"
}' > /dev/null

curl -s -X POST $API -H "Content-Type: application/json" -d '{
  "title":"Printer jam",
  "description":"3rd floor HP 4200",
  "priority":0, "category":"Hardware",
  "status":"Resolved", "assignee":"Jamie"
}' > /dev/null

curl -s -X POST $API -H "Content-Type: application/json" -d '{
  "title":"System not turning on",
  "description":"Blue screen error",
  "priority":3, "category":"General",
  "status":"Open", "assignee":"abc"
}' > /dev/null

curl -s -X POST $API -H "Content-Type: application/json" -d '{
  "title":"WiFi Slowness",
  "description":"Cafeteria network drops",
  "priority":1, "category":"Network",
  "status":"InProgress", "assignee":"Sam"
}' > /dev/null

echo "✅ Dummy tickets seeded!"
