# 📺 VOD (Video On Demand) Android TV App

An Android TV application that **fetches categorized movie and series content**, allowing users to **browse**, **autoplay trailers**, and **stream full videos** directly on their device. Built with performance and scalability in mind, the app supports focus-aware TV navigation and dynamic content rendering.

---

## 🔧 Technologies Used

- 🤖 **Android** – Optimized for Android TV interface and hardware  
- 🟠 **Kotlin** – Modern, expressive language for Android development  
- 🧱 **Jetpack Compose** – Declarative UI framework, ideal for focus-aware UIs  
- 🌐 **Retrofit** – Networking library for API calls  
- 🗃️ **DataStore** – Local persistence for storing session data (AUID, preferences)  
- 🧭 **Navigation** – Type-safe, argument-driven in-app routing  
- 🧪 **Dagger Hilt** – Dependency injection for scalable and testable architecture  
- 🧠 **MVVM** – Clean architecture (multi-module MVVM)  
- 🔁 **Lifecycle-aware** – State management in ViewModel  
- 🖼️ **Coil** – Image loading for smooth UI

---

## 🧱 Architecture Overview

- The project is split across multiple modules:

- 📦 **app** -  Main entry point and composition root; sets up Hilt and NavHost
- 📦 **data** - Handles remote API access and local storage logic; handles DI
- 📦 **domain** - Contains repository interfaces, and core business models
- 📦 **presentation** - UI layer built with Jetpack Compose; includes ViewModels, Screens, AppNavigation, Utilties for UI

## 🔁 Data Flow

1. Retrieve the Advertising ID from the Android TV.
2. Use the Advertising ID to request an **AUID** (anonymous user ID) from the server.
3. Cache the AUID using **DataStore**.
4. Request a categorized **VOD list** using the AUID and display in UI.
5. If the selected item is a **series**, it is possible to fetch its **seasons and episodes**.
6. UI presents Rails with items that support **focus navigation**, **lazy loading**, and **details expansion**.

---

## 🔮 Planned Improvements

- 👤 User profiles with personalized preferences
- ⭐ Favorites and watchlist functionality
- 📥 Offline playback capabilities
- 📈 Paging support for large catalogs
- 🧪 Unit and UI test coverage
- 🌐 Multi-language and subtitle support
- 🔐 Parental controls and content restrictions

---

## 🧠 Architectural Decisions

- **Clean architecture** ensures separation of concerns across modules
- All dependencies are declared through **interfaces**, enabling easier mocking and testing
- **ViewModels** serve as the state holder and coordinator between UI and domain logic
- Business logic is **isolated from the UI**, making the presentation layer purely reactive
- **Hilt** simplifies dependency graph creation, especially for test scenarios and dynamic injection

---
