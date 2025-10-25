# Messages

Android messaging application built for the Interface engineering challenge. Implements a clean, modern chat interface with focus on architecture, UX polish, and adaptive design.

## Tech Stack

- **UI**: Jetpack Compose with Material 3 (including Adaptive components for large screens)
- **Architecture**: MVVM with Clean Architecture (Domain/Data/Presentation layers)
- **DI**: Hilt
- **Reactive**: Kotlin Flows & StateFlow
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

## Building

```bash
./gradlew assembleDebug
```

**Requirements**: Android SDK 26+, targeting SDK 36

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
- ✅ **Polish**: Auto-scroll to new messages, configuration change survival, proper state management

## Prioritization & Trade-offs

Given the time constraints, I focused on:

1. **Architecture First**: Clean separation of concerns, testable code structure, reactive patterns
2. **UX Polish**: Smooth interactions, keyboard management, smart scrolling behavior
3. **Adaptive Design**: Proper support for various form factors (tablets/foldables)
4. **Code Quality**: Clear patterns, proper state management, Compose best practices

**Deferred for production**:
- Server integration (using fake repository with reactive state to demonstrate architecture)
- Offline persistence layer
- Network resilience & retry logic
- Optimistic sending
- Comprehensive test coverage

## Implementation Highlights

- **Reactive State**: `MutableStateFlow` in repository ensures real-time UI updates without manual coordination
- **Smart Auto-scroll**: Only triggers on new messages, preserving scroll position on config changes and initial load
- **Proper State Management**: TextField follows Compose guidelines with `remember(key)` for derived state
- **Clean Architecture**: Domain/Data/Presentation separation enables easy server integration later
- **Type-safe Navigation**: Leverages Kotlin serialization for compile-time safety
