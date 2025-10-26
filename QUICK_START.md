# Quick Start Guide

## 🚀 Get Running in 5 Minutes

### Step 1: Start the Server
```bash
# In the Interface challenge server directory
cd /path/to/challenge/server
go run .
```

You should see:
```
Server running on http://localhost:3000
```

### Step 2: Run the App
```bash
# In this project directory
./gradlew installDebug
```

Or use Android Studio:
1. Open project
2. Click Run ▶️

### Step 3: Test It Works

1. **See the chat list** - Should load chats from server
2. **Open a chat** - Tap any conversation
3. **Send a message** - Type and hit send
4. **Bot replies** - Should appear automatically via SSE

### Troubleshooting

**Problem**: "Unable to connect"
- ✅ Check server is running: `curl http://localhost:3000/chats`
- ✅ Using emulator? Default config should work
- ✅ Using physical device? Update `ServerConfig.BASE_URL`

**Problem**: "No chats appear"
- ✅ Check LogCat for HTTP errors
- ✅ Verify server has chat data
- ✅ Try: `curl http://localhost:3000/chats`

**Problem**: "Messages don't appear in real-time"
- ✅ Check LogCat for SSE connection logs
- ✅ Verify: `curl -N http://localhost:3000/events?stream=messages`

### Configuration

**For Physical Device**:
Edit `app/src/main/java/com/renaudfavier/messages/chat/data/api/ServerConfig.kt`:
```kotlin
const val BASE_URL = "http://YOUR_IP:3000"  // Find IP: ifconfig (Mac/Linux) or ipconfig (Windows)
```

**For ngrok**:
```bash
ngrok http 3000
```
Then update `ServerConfig.BASE_URL` with the ngrok https URL.

### What to Expect

✅ Chat list sorted by most recent message
✅ Real-time messages appear instantly
✅ Retries work when server is slow
✅ No duplicate messages
✅ Keyboard dismisses after send
✅ Auto-scrolls to new messages

### Server Behavior

The challenge server intentionally:
- ⏱️ Times out randomly
- 🔌 Drops connections
- 2️⃣ Sends duplicate events

Your app handles all of these! Check LogCat to see retry logic in action.

### Next Steps

1. Try sending multiple messages quickly - watch retries
2. Kill and restart server - watch SSE reconnect
3. Rotate device - watch state persist
4. Check `SERVER_INTEGRATION.md` for deep dive

---

**Need Help?**
- Check `INTEGRATION_SUMMARY.md` for implementation details
- Check `SERVER_INTEGRATION.md` for troubleshooting
- Check LogCat for detailed logs (HTTP interceptor is enabled)
