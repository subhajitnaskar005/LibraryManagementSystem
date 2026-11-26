# Smart Library Management System

A full-stack web application to manage a library's books, members, and borrowing. Built with **Spring Boot**, **Hibernate/JPA**, and **MySQL**, with a plain HTML, CSS, and JavaScript frontend.

## Key Features
- Add, update, delete, and search books by title or author
- Member registration with duplicate-email checks
- Waitlist: when all copies are issued, members can reserve a book, and it is auto-issued to the next person when returned
- Dashboard with totals for books, members, issued and overdue books, waitlist, and fines
- Centralized exception handling with clear 400/404 error responses
