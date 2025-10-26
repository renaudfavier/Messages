# Server Integration Guide

## Overview

The app integrates with the Interface challenge server using:
- REST API for fetching chats and messages
- Server-Sent Events (SSE) for real-time message updates
- Idempotency keys for reliable message sending

## Quick Start

### 1. Start the Server

From the challenge repository:
```bash
cd server
go run .
```

Server will run on `http://localhost:3000`

### 2. Configure the App

**For Android Emulator** (default):
- No configuration needed
- Uses `http://10.0.2.2:3000` (emulator's localhost)

**For Physical Device**:
1. Find your computer's local IP address:
   ```bash
   # macOS/Linux
   ifconfig | grep "inet "

   # Windows
   ipconfig
   ```

2. Update `ServerConfig.kt`:
   ```kotlin
   const val BASE_URL = "http://YOUR_COMPUTER_IP:3000"
   ```

**For Remote Testing (ngrok)**:
1. Install and run ngrok:
   ```bash
   ngrok http 3000
   ```

2. Copy the https URL (e.g., `https://9f98-62-194-145-77.ngrok.io`)

3. Update `ServerConfig.kt`:
   ```kotlin
   const val BASE_URL = "https://your-ngrok-url.ngrok.io"
   ```

### 3. Run the App

```bash
./gradlew installDebug
```

## Architecture

### Data Flow

```
Server (Go)
    ↓ REST API
    ↓ SSE Stream
ServerMessageRepository (Singleton)
    ↓ Kotlin Flows
    ↓ StateFlow Cache
ViewModels
    ↓ StateFlow
    ↓ Compose
UI
```

### Key Components

**`MessagesApiService`**: Retrofit interface for REST endpoints
- `GET /chats` - List all chats
- `GET /chats/{id}/messages` - Get messages for chat
- `POST /chats/{id}/messages` - Send message (with idempotency key)

**`MessageEventStream`**: SSE client for real-time updates
- Connects to `/events?stream=messages`
- Auto-reconnects on failure
- Parses JSON events into Message objects

**`ServerMessageRepository`**: Repository implementation
- Maintains in-memory cache of messages
- Deduplicates by message ID
- Merges REST API data with SSE updates
- Handles retries with exponential backoff

**`ApiMapper`**: Converts between DTOs and domain models
- Maps server "author" field to ContactId
- Parses ISO 8601 timestamps
- Handles user vs bot messages

## Network Resilience

### Retry Logic

Failed requests are retried up to 3 times with exponential backoff:
- Attempt 1: immediate
- Attempt 2: 500ms delay
- Attempt 3: 1000ms delay
- Attempt 4: 2000ms delay

### SSE Reconnection

SSE stream automatically reconnects on any failure:
- 2 second delay between reconnection attempts
- Infinite retries (until app closes)
- Preserves cached messages during reconnection

### Idempotency

Each message send includes a UUID idempotency key:
```kotlin
val idempotencyKey = UUID.randomUUID().toString()
```

The server uses this to prevent duplicate messages if the same request is retried.

### Duplicate Detection

Messages are cached in a Map by ID:
```kotlin
private val messagesCache = MutableStateFlow<Map<Int, Message>>(emptyMap())
```

When a message arrives (from API or SSE), it's added by ID:
```kotlin
messagesCache.update { cache -> cache + (message.id to message) }
```

This automatically deduplicates - if the same message arrives twice, the second one just updates the existing entry.

## Troubleshooting

### Server Connection Issues

**Problem**: "Unable to connect to server"

**Solutions**:
1. Verify server is running: `curl http://localhost:3000/chats`
2. Check `ServerConfig.BASE_URL` matches your setup
3. For emulator, use `10.0.2.2` not `localhost`
4. For physical device, ensure same WiFi network

### SSE Not Working

**Problem**: Messages don't appear in real-time

**Check**:
1. LogCat for SSE connection logs
2. Verify `/events?stream=messages` endpoint is accessible
3. Check OkHttp logging interceptor output

### Messages Appearing Twice

**Problem**: Duplicate messages in UI

**This should not happen** due to ID-based deduplication. If it does:
1. Check message IDs in logs
2. Verify server returns consistent IDs
3. Check if UI is rendering duplicates (different issue)

## Testing with Server Failures

The challenge server intentionally fails sometimes. To verify resilience:

1. **Timeout Testing**: Send multiple messages quickly
   - Some will timeout
   - Retries should succeed
   - UI should remain responsive

2. **SSE Disconnection**: Kill and restart server
   - SSE should reconnect automatically
   - Missing messages should sync on reconnect

3. **Duplicate Events**: Server may send duplicate SSE events
   - App should deduplicate by message ID
   - No duplicate messages in UI

## API Request Examples

### List Chats
```bash
curl http://localhost:3000/chats
```

Response:
```json
[
  { "id": 1, "name": "Alice" },
  { "id": 2, "name": "Bob" }
]
```

### Get Messages
```bash
curl http://localhost:3000/chats/1/messages
```

Response:
```json
[
  {
    "id": 1,
    "chat_id": 1,
    "author": "user",
    "text": "Hello",
    "sent_at": "2025-01-01T12:00:00Z",
    "idempotency_key": "uuid-here"
  }
]
```

### Send Message
```bash
curl -X POST http://localhost:3000/chats/1/messages \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: $(uuidgen)" \
  -d '{"text":"Test message"}'
```

### SSE Stream
```bash
curl -N http://localhost:3000/events?stream=messages
```

Receives:
```
data: {"id":1,"chat_id":1,"author":"bot","text":"Reply","sent_at":"...","idempotency_key":"..."}
```

## Performance Considerations

- **In-memory cache**: Messages are cached only in memory (lost on app restart)
- **SSE overhead**: Persistent connection uses some battery
- **Network usage**: Initial load fetches last 100 messages per chat

For production, consider:
- Persisting cache to Room database
- Implementing background sync
- Adding pagination for large conversations
