# Messages

Android messaging application built for the Interface engineering challenge. Implements a clean, modern chat interface with **full server integration**, network resilience, and adaptive design.

🎉 **Status**: Fully integrated with server, ready to demo!

<img width="595" height="600" alt="image" src="https://github.com/user-attachments/assets/95a31763-7589-4c0e-88bf-bab9255cc742" /> <img width="282" height="600" alt="image" src="https://github.com/user-attachments/assets/7d1e9412-9e45-4293-83bc-a4bc08dc8bed" />



## Tech Stack

- **UI**: Jetpack Compose with Material 3 (including Adaptive components for large screens)
- **Architecture**: MVVM with Clean Architecture (Domain/Data/Presentation layers)
- **DI**: Hilt
- **Reactive**: Kotlin Flows & StateFlow
- **Networking**: Retrofit + OkHttp with Server-Sent Events (SSE)
- **Navigation**: Compose Navigation with type-safe arguments
- **Language**: Kotlin

## Features

- **Contact List**: Displays conversations sorted by most recent message
- **Conversation View**: Real-time message thread with auto-scroll for new messages
- **Message Input**: Text field with send button and keyboard management
- **Unread Tracking**: Automatic marking of messages as read when opening conversations
- **Adaptive UI**: Optimized layouts for foldables, tablets, and large screens using Material 3 Adaptive components
- **Responsive UI**: Survives configuration changes (rotation) with state preservation

## Architecture

```
app/
├── core/
│   ├── domain/     # Contact entities and repository interfaces
│   ├── data/       # Fake data implementations
│   └── ui/         # Theming and shared UI components
└── chat/
    ├── domain/     # Message entities, repository, and use cases
    ├── data/       # FakeMessageRepository with reactive state
    └── presentation/
        ├── contacts/      # Contact list screen
        └── conversation/  # Message thread screen
```

### Key Design Decisions

- **Reactive State Management**: `MutableStateFlow` in repository ensures UI updates automatically when data changes
- **UseCases**: Business logic encapsulated (e.g., `GetConversationListUseCase`, `ReadAllMessagesUseCase`)
- **Unidirectional Data Flow**: ViewModels expose immutable StateFlows, actions flow through sealed classes
- **Compose Best Practices**: State hoisting, remember keys for derived state, LaunchedEffect for side effects

## Server Setup

This app connects to the Interface challenge server. To run:

1. **Start the server** (from challenge repository):
   ```bash
   cd server
   go run .
   ```
   Server runs on `http://localhost:3000`

2. **Configure connection**:
   - **Emulator**: Default config (`http://10.0.2.2:3000`) works automatically
   - **Physical device**: Update `ServerConfig.BASE_URL` in `ServerConfig.kt` with your computer's IP
   - **ngrok**: Run `ngrok http 3000` and update `ServerConfig.BASE_URL` with the ngrok URL

## Building

```bash
./gradlew assembleDebug
```

**Requirements**:
- Android SDK 26+, targeting SDK 36
- Server running on localhost:3000 (or configured endpoint)

## Testing

```bash
./gradlew test              # Unit tests
./gradlew connectedCheck    # Instrumented tests
```

## Challenge Requirements

### Core Features ✓
- ✅ Chat list screen showing all conversations
- ✅ Individual chat view with message thread
- ✅ Send messages functionality
- ✅ Real-time message updates reflected in UI

### Bonus Features Implemented
- ✅ **Read/Unread Indicator**: Messages automatically marked as read when opening conversations
- ✅ **Fluid UX**: Non-blocking state changes, smooth animations, smart keyboard handling
- ✅ **Adaptive Design**: Optimized for tablets, foldables, and large screens using Material 3 Adaptive
- ✅ **Network Resilience**: Retry logic with exponential backoff, handles timeouts and failures gracefully
- ✅ **Idempotency**: UUID-based idempotency keys prevent duplicate message sends
- ✅ **Real-time Updates**: SSE streaming with automatic reconnection on failure
- ✅ **Polish**: Auto-scroll to new messages, configuration change survival, proper state management

## Prioritization & Trade-offs

Given the time constraints, I focused on:

1. **Full Server Integration**: Complete API implementation with SSE streaming
2. **Network Resilience**: Retry logic, exponential backoff, idempotency, duplicate detection
3. **Architecture**: Clean separation enabling easy testing and future enhancements
4. **UX Polish**: Smooth interactions, keyboard management, smart scrolling behavior
5. **Adaptive Design**: Proper support for various form factors (tablets/foldables)

**Deferred for production**:
- Offline persistence layer (Room database)
- Optimistic UI updates (currently relies on server response)
- Comprehensive test coverage (unit + integration tests)
- Advanced error recovery strategies (e.g., queue failed messages)

## Implementation Highlights

### Network Layer
- **SSE Streaming**: Real-time message updates via Server-Sent Events with automatic reconnection
- **Idempotency**: UUID-based keys ensure messages aren't duplicated on retries
- **Retry Logic**: Exponential backoff for failed requests (max 3 attempts)
- **Duplicate Detection**: In-memory cache deduplicates messages by ID
- **Error Resilience**: Graceful degradation when server is unreliable

### Architecture
- **Repository Pattern**: `ServerMessageRepository` with in-memory cache + SSE updates
- **Reactive State**: `MutableStateFlow` broadcasts changes to all subscribers
- **Clean Separation**: Domain/Data/Presentation layers with clear boundaries
- **Easy Configuration**: Single `ServerConfig.kt` file for endpoint management

### UI/UX
- **Smart Auto-scroll**: Only triggers on new messages, preserving scroll position
- **Proper State Management**: TextField follows Compose guidelines with `remember(key)`
- **Type-safe Navigation**: Kotlin serialization for compile-time safety
- **Adaptive Layouts**: Material 3 Adaptive components for tablets/foldables
