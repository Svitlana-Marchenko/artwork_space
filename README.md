# artwork_space

## Frontend

### Getting Started

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
  


