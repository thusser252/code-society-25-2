# Changes Made to Address Instructor Feedback

## Issue
The instructor requested that static files should be served from a sub-folder called `public` or `static` so that public files are separated from the code.

## Changes Made

### 1. Directory Structure
- Created a new `public/` folder to contain all static files
- Moved the following files from the root directory to `public/`:
  - `index.html`
  - `contact.html`
  - `style.css`
  - `hero.jpg`
  - `logo.png`

### 2. Server Configuration Updates
Updated `server.js` to properly serve static files from the `public` folder:

```javascript
// Before:
app.use(express.static(__dirname));

// After:
app.use(express.static(path.join(__dirname, 'public')));
```

### 3. Route Updates
Updated route handlers to serve HTML files from the `public` folder:

```javascript
// Before:
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'index.html'));
});

// After:
app.get('/', (req, res) => {
    res.sendFile(path.join(__dirname, 'public', 'index.html'));
});
```

### Final Directory Structure
```
trishtanhusser/
├── server.js          (server code)
├── package.json       (dependencies)
├── package-lock.json  (dependency lock)
├── README.md          (original readme)
├── CHANGES.md         (this file)
├── node_modules/      (dependencies)
└── public/            (static files)
    ├── index.html
    ├── contact.html
    ├── style.css
    ├── hero.jpg
    └── logo.png
```

## Benefits
- Clean separation between server code and static assets
- Better organization following Express.js best practices
- Static files are now properly isolated in their own directory
- Server code is cleaner and more maintainable

## Testing
The server has been tested and confirmed working on `http://localhost:3000`
- Home page loads correctly
- Contact page loads correctly
- CSS and images load properly
- Contact form submission works as expected
