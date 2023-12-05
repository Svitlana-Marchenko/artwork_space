# Artwork Space Web App

## Overview

Artwork Space is a web application built with Java, Spring Boot, React, and TypeScript. The backend utilizes the H2 database, providing a seamless and responsive experience for art enthusiasts. The app features user authorization, with roles including Collectioneer, Curator, and Artist, each offering different functionalities. Unauthenticated users can explore artworks and exhibitions.

## Key Features

1. **User Roles:**
   - Collectioneer: Create and manage personal art collections.
   - Curator: Provide reviews and ratings, organize exhibitions.
   - Artist: Organize auctions for artworks, post new artworks.

2. **Authorization:**
   - Differentiated roles with specific functions.
   - Unauthenticated users can view artworks and exhibitions.

3. **Artwork Management:**
   - Collectioneers can create collections from existing artworks.
   - Curators can provide reviews, ratings, and organize exhibitions.
   - Artists can organize auctions and post new artworks.

## Tech Stack

- **Backend:**
  - Java
  - Spring Boot
  - H2 Database

- **Frontend:**
  - React
  - TypeScript

## Getting Started

### Clone the Repository:

1. Visit the [Artwork Space Git repository](https://github.com/Svitlana-Marchenko/artwork_space).
2. Clone the repository to your local machine using the following command:
```bash
   git clone https://github.com/Svitlana-Marchenko/artwork_space.git
```

## Frontend

```bash
   npm install
   npm install -D tailwindcss
   npx tailwindcss init
   npm install react-hot-toast
   npm start
```

### Issues and Troubleshooting

- Після запуску не відображаються стилі
  
  У файлі `index.css` необхідно ось такі імпорти:
  ```
  @tailwind base;
  @tailwind components;
  @tailwind utilities;
  ```
  замінити на
  ```
  @import "tailwindcss/base";
  @import "tailwindcss/components";
  @import "tailwindcss/utilities";
  ```
  


