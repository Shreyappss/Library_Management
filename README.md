# Folio — Campus Library

A student-first Library Management System with a calm editorial identity, curated catalogue and practical borrowing workflow.

## Run
Open `index.html` in a modern browser, or serve this directory with any static file server. No build step or package installation is required.

## Features
- Student overview with active loans, due reminders and featured titles
- Searchable catalogue with category and availability filters
- Book detail views and simulated 14-day checkout/return flow
- Borrowing history and student book requests
- Responsive mobile navigation, accessible labels and reduced-motion support
- Java borrowing service and Kotlin book-request domain/service examples under `backend/`

## Technology roles
- **HTML** — semantic application structure and accessible forms/dialogs
- **CSS** — responsive visual system, layouts, book-cover artwork and motion
- **JavaScript** — catalogue interactions, filtering, checkout/return and local persistence
- **Java** — typed borrowing service/domain model reference for a future API
- **Kotlin** — validated book-request model and request workflow reference

## Important demo boundary
The deployed static frontend stores its demo state in browser `localStorage`. It has no live backend, shared database, server-side inventory, real student authentication, librarian account or actual notification/approval workflow. Java and Kotlin are companion backend reference modules; they are not wired to the static site. Each browser has its own independent demo data.

## Project layout
```
index.html
styles.css
app.js
backend/
  java/LibraryService.java
  kotlin/BookRequest.kt
```
