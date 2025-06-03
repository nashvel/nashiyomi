package com.example.tachiyomi.ui.editor

import com.example.tachiyomi.ui.screens.ProgrammingLanguage

/**
 * Provides template code snippets for different programming languages
 */
object CodeTemplates {
    /**
     * Get a template for a new file based on the programming language
     */
    fun getTemplateForLanguage(language: ProgrammingLanguage, fileName: String): String {
        return when (language) {
            ProgrammingLanguage.HTML -> htmlTemplate(fileName)
            ProgrammingLanguage.CSS -> cssTemplate(fileName)
            ProgrammingLanguage.JAVASCRIPT -> javascriptTemplate(fileName)
            ProgrammingLanguage.MARKDOWN -> markdownTemplate()
        }
    }



    private fun htmlTemplate(fileName: String): String {
        return """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <meta name="description" content="Nashiyomi - Modern manga reader and code editor application">
                <meta name="keywords" content="Nashiyomi, manga, reader, code, editor">
                <meta name="author" content="Nashiyomi">
                <meta name="theme-color" content="#3498db">
                
                <title>Nashiyomi - $fileName</title>
                
                <!-- Favicon -->
                <link rel="shortcut icon" href="favicon.ico" type="image/x-icon">
                
                <!-- CSS Variables and Custom Properties -->
                <style>
                    :root {
                        /* Color Variables */
                        --primary-color: #3498db;
                        --primary-dark: #2980b9;
                        --secondary-color: #2ecc71;
                        --accent-color: #e74c3c;
                        --text-color: #333333;
                        --text-light: #ffffff;
                        --bg-color: #f5f5f5;
                        --bg-light: #ffffff;
                        --bg-dark: #2c3e50;
                        
                        /* Spacing */
                        --spacing-xs: 0.25rem;
                        --spacing-sm: 0.5rem;
                        --spacing-md: 1rem;
                        --spacing-lg: 2rem;
                        --spacing-xl: 3rem;
                        
                        /* Typography */
                        --font-primary: 'Segoe UI', system-ui, -apple-system, sans-serif;
                        --font-secondary: 'Georgia', serif;
                        --font-mono: 'Consolas', monospace;
                        --font-size-sm: 0.875rem;
                        --font-size-base: 1rem;
                        --font-size-md: 1.125rem;
                        --font-size-lg: 1.5rem;
                        --font-size-xl: 2.25rem;
                        
                        /* Effects */
                        --shadow-sm: 0 1px 3px rgba(0, 0, 0, 0.12);
                        --shadow-md: 0 4px 6px rgba(0, 0, 0, 0.1);
                        --shadow-lg: 0 10px 15px -3px rgba(0, 0, 0, 0.1);
                        --border-radius: 0.5rem;
                        --transition: all 0.3s ease;
                    }
                    
                    /* Base Styles */
                    * {
                        margin: 0;
                        padding: 0;
                        box-sizing: border-box;
                    }
                    
                    html {
                        font-size: 100%;
                        scroll-behavior: smooth;
                    }
                    
                    body {
                        font-family: var(--font-primary);
                        line-height: 1.6;
                        color: var(--text-color);
                        background-color: var(--bg-color);
                        min-height: 100vh;
                        display: flex;
                        flex-direction: column;
                    }
                    
                    img {
                        max-width: 100%;
                        height: auto;
                        display: block;
                    }
                    
                    a {
                        color: var(--primary-color);
                        text-decoration: none;
                        transition: var(--transition);
                    }
                    
                    a:hover, a:focus {
                        color: var(--primary-dark);
                        text-decoration: underline;
                    }
                    
                    /* Skip to content for accessibility */
                    .skip-link {
                        position: absolute;
                        top: -40px;
                        left: 0;
                        background: var(--primary-color);
                        color: white;
                        padding: var(--spacing-sm) var(--spacing-md);
                        z-index: 100;
                        transition: top 0.3s;
                    }
                    
                    .skip-link:focus {
                        top: 0;
                    }
                    
                    /* Layout */
                    .container {
                        width: 100%;
                        max-width: 1200px;
                        margin: 0 auto;
                        padding: 0 var(--spacing-md);
                    }
                    
                    .page-wrapper {
                        flex: 1;
                        padding: var(--spacing-lg) 0;
                    }
                    
                    /* Header */
                    .site-header {
                        background-color: var(--primary-color);
                        color: var(--text-light);
                        padding: var(--spacing-md) 0;
                        box-shadow: var(--shadow-md);
                    }
                    
                    .navbar {
                        display: flex;
                        justify-content: space-between;
                        align-items: center;
                        flex-wrap: wrap;
                    }
                    
                    .logo {
                        font-size: var(--font-size-lg);
                        font-weight: bold;
                        color: var(--text-light);
                    }
                    
                    .logo a {
                        color: var(--text-light);
                        text-decoration: none;
                    }
                    
                    .nav-menu {
                        display: flex;
                        list-style: none;
                        gap: var(--spacing-md);
                    }
                    
                    .nav-menu a {
                        color: var(--text-light);
                        text-decoration: none;
                        padding: var(--spacing-xs) var(--spacing-sm);
                        border-radius: 4px;
                        transition: var(--transition);
                    }
                    
                    .nav-menu a:hover, .nav-menu a:focus {
                        background-color: rgba(255, 255, 255, 0.1);
                    }
                    
                    .mobile-menu-btn {
                        display: none;
                        background: transparent;
                        border: none;
                        color: var(--text-light);
                        font-size: var(--font-size-lg);
                        cursor: pointer;
                    }
                    
                    /* Hero Section */
                    .hero {
                        background: linear-gradient(135deg, var(--primary-color), var(--primary-dark));
                        color: var(--text-light);
                        padding: var(--spacing-xl) 0;
                        text-align: center;
                        margin-bottom: var(--spacing-lg);
                    }
                    
                    .hero h1 {
                        font-size: var(--font-size-xl);
                        margin-bottom: var(--spacing-md);
                    }
                    
                    .hero p {
                        font-size: var(--font-size-md);
                        max-width: 800px;
                        margin: 0 auto var(--spacing-md);
                    }
                    
                    .btn {
                        display: inline-block;
                        background-color: var(--secondary-color);
                        color: var(--text-light);
                        padding: var(--spacing-sm) var(--spacing-md);
                        border-radius: 4px;
                        text-decoration: none;
                        transition: var(--transition);
                    }
                    
                    .btn:hover, .btn:focus {
                        background-color: var(--primary-dark);
                        transform: translateY(-2px);
                        box-shadow: var(--shadow-md);
                    }
                    
                    /* Features Section */
                    .features {
                        padding: var(--spacing-lg) 0;
                    }
                    
                    .section-header {
                        text-align: center;
                        margin-bottom: var(--spacing-lg);
                    }
                    
                    .features-grid {
                        display: grid;
                        grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
                        gap: var(--spacing-md);
                    }
                    
                    .feature-card {
                        background-color: var(--bg-light);
                        border-radius: var(--border-radius);
                        overflow: hidden;
                        box-shadow: var(--shadow-sm);
                        transition: var(--transition);
                    }
                    
                    .feature-card:hover {
                        transform: translateY(-5px);
                        box-shadow: var(--shadow-md);
                    }
                    
                    .feature-content {
                        padding: var(--spacing-md);
                    }
                    
                    .feature-card h3 {
                        margin-bottom: var(--spacing-sm);
                        color: var(--primary-color);
                    }
                    
                    /* Footer */
                    .site-footer {
                        background-color: var(--bg-dark);
                        color: var(--text-light);
                        padding: var(--spacing-lg) 0;
                        margin-top: auto;
                    }
                    
                    .footer-content {
                        display: grid;
                        grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                        gap: var(--spacing-lg);
                    }
                    
                    .footer-column h4 {
                        margin-bottom: var(--spacing-md);
                        font-size: var(--font-size-md);
                    }
                    
                    .footer-links {
                        list-style: none;
                    }
                    
                    .footer-links li {
                        margin-bottom: var(--spacing-xs);
                    }
                    
                    .footer-links a {
                        color: #ddd;
                        text-decoration: none;
                        transition: var(--transition);
                    }
                    
                    .footer-links a:hover {
                        color: var(--primary-color);
                    }
                    
                    .copyright {
                        text-align: center;
                        padding-top: var(--spacing-md);
                        margin-top: var(--spacing-lg);
                        border-top: 1px solid rgba(255, 255, 255, 0.1);
                    }
                    
                    /* Responsive Design */
                    @media (max-width: 768px) {
                        .mobile-menu-btn {
                            display: block;
                        }
                        
                        .nav-menu {
                            display: none;
                            width: 100%;
                            flex-direction: column;
                            gap: var(--spacing-sm);
                            padding-top: var(--spacing-md);
                        }
                        
                        .nav-menu.active {
                            display: flex;
                        }
                        
                        .features-grid {
                            grid-template-columns: 1fr;
                        }
                    }
                </style>
            </head>
            <body>
                <!-- Accessibility Skip Link -->
                <a href="#main-content" class="skip-link">Skip to content</a>
                
                <!-- Header -->
                <header class="site-header">
                    <div class="container">
                        <nav class="navbar">
                            <div class="logo">
                                <a href="index.html">Nashiyomi</a>
                            </div>
                            
                            <button class="mobile-menu-btn" aria-label="Toggle menu">
                                ≡
                            </button>
                            
                            <ul class="nav-menu">
                                <li><a href="#" aria-current="page">Home</a></li>
                                <li><a href="#">Features</a></li>
                                <li><a href="#">Documentation</a></li>
                                <li><a href="#">About</a></li>
                                <li><a href="#">Contact</a></li>
                            </ul>
                        </nav>
                    </div>
                </header>
                
                <!-- Main Content -->
                <main id="main-content" class="page-wrapper">
                    <!-- Hero Section -->
                    <section class="hero">
                        <div class="container">
                            <h1>Welcome to Nashiyomi</h1>
                            <p>A modern manga reader with built-in code editing capabilities. Read your favorite manga and write code all in one application.</p>
                            <a href="#" class="btn">Get Started</a>
                        </div>
                    </section>
                    
                    <!-- Features Section -->
                    <section class="features">
                        <div class="container">
                            <div class="section-header">
                                <h2>Key Features</h2>
                                <p>Discover what makes Nashiyomi special</p>
                            </div>
                            
                            <div class="features-grid">
                                <div class="feature-card">
                                    <div class="feature-content">
                                        <h3>Manga Library</h3>
                                        <p>Organize and read your favorite manga in a clean, intuitive interface.</p>
                                    </div>
                                </div>
                                
                                <div class="feature-card">
                                    <div class="feature-content">
                                        <h3>Code Editor</h3>
                                        <p>Built-in multi-language code editor with syntax highlighting.</p>
                                    </div>
                                </div>
                                
                                <div class="feature-card">
                                    <div class="feature-content">
                                        <h3>Dark Mode</h3>
                                        <p>Easy on the eyes with automatic dark mode support.</p>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </section>
                    
                    <!-- Your custom content goes here -->
                </main>
                
                <!-- Footer -->
                <footer class="site-footer">
                    <div class="container">
                        <div class="footer-content">
                            <div class="footer-column">
                                <h4>Nashiyomi</h4>
                                <p>A modern manga reader and code editor all in one beautiful application.</p>
                            </div>
                            
                            <div class="footer-column">
                                <h4>Links</h4>
                                <ul class="footer-links">
                                    <li><a href="#">Home</a></li>
                                    <li><a href="#">Features</a></li>
                                    <li><a href="#">Documentation</a></li>
                                    <li><a href="#">Support</a></li>
                                </ul>
                            </div>
                            
                            <div class="footer-column">
                                <h4>Resources</h4>
                                <ul class="footer-links">
                                    <li><a href="#">API Documentation</a></li>
                                    <li><a href="#">Tutorials</a></li>
                                    <li><a href="#">Blog</a></li>
                                </ul>
                            </div>
                        </div>
                        
                        <div class="copyright">
                            <p>&copy; 2025 Nashiyomi. All rights reserved.</p>
                        </div>
                    </div>
                </footer>
                
                <!-- Simple JavaScript for mobile menu toggle -->
                <script>
                    document.addEventListener('DOMContentLoaded', function() {
                        const menuButton = document.querySelector('.mobile-menu-btn');
                        const navMenu = document.querySelector('.nav-menu');
                        
                        if (menuButton && navMenu) {
                            menuButton.addEventListener('click', function() {
                                navMenu.classList.toggle('active');
                            });
                        }
                    });
                </script>
            </body>
            </html>
        """.trimIndent()
    }

    private fun cssTemplate(fileName: String): String {
        return """
            /**
             * Nashiyomi Code Editor - Modern CSS File
             * File: $fileName
             * A comprehensive CSS template showcasing modern CSS features
             */
            
            /* CSS Custom Properties (Variables) */
            :root {
                /* Color palette */
                --primary-color: #3498db;
                --primary-dark: #2980b9;
                --secondary-color: #2ecc71;
                --secondary-dark: #27ae60;
                --accent-color: #e74c3c;
                --accent-dark: #c0392b;
                --text-primary: #333333;
                --text-secondary: #7f8c8d;
                --text-light: #ecf0f1;
                --bg-light: #f8f9fa;
                --bg-dark: #343a40;
                --bg-gray: #e9ecef;
                
                /* Spacing */
                --spacing-xs: 0.25rem;  /* 4px */
                --spacing-sm: 0.5rem;   /* 8px */
                --spacing-md: 1rem;     /* 16px */
                --spacing-lg: 1.5rem;   /* 24px */
                --spacing-xl: 2rem;     /* 32px */
                
                /* Typography */
                --font-family-sans: 'Inter', 'Segoe UI', system-ui, -apple-system, sans-serif;
                --font-family-mono: 'Fira Code', 'Consolas', monospace;
                --font-size-xs: 0.75rem;   /* 12px */
                --font-size-sm: 0.875rem;  /* 14px */
                --font-size-md: 1rem;      /* 16px */
                --font-size-lg: 1.25rem;   /* 20px */
                --font-size-xl: 1.5rem;    /* 24px */
                --font-size-xxl: 2rem;     /* 32px */
                
                /* Border radius */
                --radius-sm: 0.25rem;   /* 4px */
                --radius-md: 0.5rem;    /* 8px */
                --radius-lg: 1rem;      /* 16px */
                --radius-full: 9999px;
                
                /* Shadows */
                --shadow-sm: 0 1px 3px rgba(0, 0, 0, 0.12), 0 1px 2px rgba(0, 0, 0, 0.24);
                --shadow-md: 0 4px 6px rgba(0, 0, 0, 0.1), 0 1px 3px rgba(0, 0, 0, 0.08);
                --shadow-lg: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
                
                /* Transitions */
                --transition-fast: 150ms ease;
                --transition-normal: 300ms ease;
                --transition-slow: 500ms ease;
                
                /* Container widths */
                --container-sm: 640px;
                --container-md: 768px;
                --container-lg: 1024px;
                --container-xl: 1280px;
            }
            
            /* Modern CSS Reset */
            *,
            *::before,
            *::after {
                margin: 0;
                padding: 0;
                box-sizing: border-box;
            }
            
            /* Improve default text rendering */
            html {
                font-size: 100%;
                -webkit-text-size-adjust: 100%;
                -webkit-font-smoothing: antialiased;
                -moz-osx-font-smoothing: grayscale;
                text-rendering: optimizeLegibility;
            }
            
            body {
                font-family: var(--font-family-sans);
                font-size: var(--font-size-md);
                line-height: 1.6;
                color: var(--text-primary);
                background-color: var(--bg-light);
                min-height: 100vh;
                scroll-behavior: smooth;
            }
            
            img, picture, svg {
                max-width: 100%;
                display: block;
            }
            
            /* Remove list styles */
            ul, ol {
                list-style: none;
            }
            
            /* Enhance form element defaults */
            input, button, textarea, select {
                font: inherit;
                color: inherit;
            }
            
            /* Make images responsive */
            img {
                max-width: 100%;
                height: auto;
            }
            
            /* Container */
            .container {
                width: 100%;
                max-width: var(--container-lg);
                margin-inline: auto;
                padding-inline: var(--spacing-md);
            }
            
            /* Flexbox Layout Utilities */
            .flex {
                display: flex;
            }
            
            .flex-col {
                flex-direction: column;
            }
            
            .flex-wrap {
                flex-wrap: wrap;
            }
            
            .items-center {
                align-items: center;
            }
            
            .justify-center {
                justify-content: center;
            }
            
            .justify-between {
                justify-content: space-between;
            }
            
            .gap-sm {
                gap: var(--spacing-sm);
            }
            
            .gap-md {
                gap: var(--spacing-md);
            }
            
            .gap-lg {
                gap: var(--spacing-lg);
            }
            
            /* Grid Layout */
            .grid {
                display: grid;
                gap: var(--spacing-md);
            }
            
            .grid-cols-1 {
                grid-template-columns: 1fr;
            }
            
            .grid-cols-2 {
                grid-template-columns: repeat(2, 1fr);
            }
            
            .grid-cols-3 {
                grid-template-columns: repeat(3, 1fr);
            }
            
            .grid-cols-4 {
                grid-template-columns: repeat(4, 1fr);
            }
            
            /* Typography */
            h1, h2, h3, h4, h5, h6 {
                font-weight: 700;
                line-height: 1.2;
                margin-bottom: var(--spacing-md);
                color: var(--text-primary);
            }
            
            h1 {
                font-size: var(--font-size-xxl);
            }
            
            h2 {
                font-size: var(--font-size-xl);
            }
            
            h3 {
                font-size: var(--font-size-lg);
            }
            
            p {
                margin-bottom: var(--spacing-md);
            }
            
            .text-center {
                text-align: center;
            }
            
            .text-primary {
                color: var(--primary-color);
            }
            
            .text-secondary {
                color: var(--secondary-color);
            }
            
            .text-accent {
                color: var(--accent-color);
            }
            
            .text-light {
                color: var(--text-light);
            }
            
            /* Buttons */
            .btn {
                display: inline-flex;
                align-items: center;
                justify-content: center;
                gap: var(--spacing-sm);
                background-color: var(--bg-gray);
                color: var(--text-primary);
                border: none;
                border-radius: var(--radius-md);
                padding: var(--spacing-sm) var(--spacing-md);
                font-weight: 500;
                cursor: pointer;
                text-decoration: none;
                transition: all var(--transition-fast);
            }
            
            .btn:hover {
                opacity: 0.9;
                transform: translateY(-1px);
            }
            
            .btn:active {
                transform: translateY(0);
            }
            
            .btn-primary {
                background-color: var(--primary-color);
                color: white;
            }
            
            .btn-secondary {
                background-color: var(--secondary-color);
                color: white;
            }
            
            .btn-accent {
                background-color: var(--accent-color);
                color: white;
            }
            
            .btn-outline {
                background-color: transparent;
                border: 1px solid currentColor;
            }
            
            /* Cards */
            .card {
                background-color: white;
                border-radius: var(--radius-md);
                overflow: hidden;
                box-shadow: var(--shadow-sm);
                transition: transform var(--transition-normal), box-shadow var(--transition-normal);
            }
            
            .card:hover {
                transform: translateY(-5px);
                box-shadow: var(--shadow-md);
            }
            
            .card-header {
                padding: var(--spacing-md);
                border-bottom: 1px solid var(--bg-gray);
            }
            
            .card-body {
                padding: var(--spacing-md);
            }
            
            .card-footer {
                padding: var(--spacing-md);
                border-top: 1px solid var(--bg-gray);
            }
            
            /* Forms */
            .form-group {
                margin-bottom: var(--spacing-md);
            }
            
            .form-label {
                display: block;
                margin-bottom: var(--spacing-xs);
                font-weight: 500;
            }
            
            .form-input {
                width: 100%;
                padding: var(--spacing-sm) var(--spacing-md);
                border: 1px solid var(--bg-gray);
                border-radius: var(--radius-md);
                background-color: white;
                transition: border-color var(--transition-fast);
            }
            
            .form-input:focus {
                outline: none;
                border-color: var(--primary-color);
                box-shadow: 0 0 0 3px rgba(52, 152, 219, 0.2);
            }
            
            /* Animations */
            @keyframes fadeIn {
                from { opacity: 0; }
                to { opacity: 1; }
            }
            
            @keyframes slideIn {
                from { transform: translateY(20px); opacity: 0; }
                to { transform: translateY(0); opacity: 1; }
            }
            
            .animate-fade {
                animation: fadeIn var(--transition-normal);
            }
            
            .animate-slide {
                animation: slideIn var(--transition-normal);
            }
            
            /* Utilities */
            .rounded-sm { border-radius: var(--radius-sm); }
            .rounded-md { border-radius: var(--radius-md); }
            .rounded-lg { border-radius: var(--radius-lg); }
            .rounded-full { border-radius: var(--radius-full); }
            
            .shadow-sm { box-shadow: var(--shadow-sm); }
            .shadow-md { box-shadow: var(--shadow-md); }
            .shadow-lg { box-shadow: var(--shadow-lg); }
            
            .p-sm { padding: var(--spacing-sm); }
            .p-md { padding: var(--spacing-md); }
            .p-lg { padding: var(--spacing-lg); }
            
            .m-sm { margin: var(--spacing-sm); }
            .m-md { margin: var(--spacing-md); }
            .m-lg { margin: var(--spacing-lg); }
            
            .mb-sm { margin-bottom: var(--spacing-sm); }
            .mb-md { margin-bottom: var(--spacing-md); }
            .mb-lg { margin-bottom: var(--spacing-lg); }
            
            .hidden { display: none; }
            
            /* Dark mode using prefers-color-scheme */
            @media (prefers-color-scheme: dark) {
                :root {
                    --text-primary: #f8f9fa;
                    --text-secondary: #ced4da;
                    --bg-light: #212529;
                    --bg-dark: #111;
                    --bg-gray: #495057;
                }
                
                body {
                    background-color: var(--bg-dark);
                    color: var(--text-primary);
                }
                
                .card {
                    background-color: var(--bg-light);
                }
                
                .form-input {
                    background-color: var(--bg-light);
                    border-color: var(--bg-gray);
                    color: var(--text-primary);
                }
            }
            
            /* Responsive design with media queries */
            @media (max-width: 768px) {
                .grid-cols-4 {
                    grid-template-columns: repeat(2, 1fr);
                }
                
                .grid-cols-3 {
                    grid-template-columns: repeat(2, 1fr);
                }
            }
            
            @media (max-width: 640px) {
                .grid-cols-4,
                .grid-cols-3,
                .grid-cols-2 {
                    grid-template-columns: 1fr;
                }
                
                h1 {
                    font-size: var(--font-size-xl);
                }
                
                h2 {
                    font-size: var(--font-size-lg);
                }
            }
            
            /* Example usage - Nashiyomi-specific styles */
            .navbar {
                background-color: var(--primary-dark);
                padding: var(--spacing-md);
                box-shadow: var(--shadow-md);
            }
            
            .navbar-brand {
                font-size: var(--font-size-lg);
                font-weight: 700;
                color: white;
                text-decoration: none;
            }
            
            .navbar-nav {
                display: flex;
                gap: var(--spacing-md);
            }
            
            .navbar-link {
                color: var(--text-light);
                text-decoration: none;
                transition: opacity var(--transition-fast);
            }
            
            .navbar-link:hover {
                opacity: 0.8;
            }
            
            .hero {
                padding: var(--spacing-xl) 0;
                background: linear-gradient(135deg, var(--primary-color), var(--primary-dark));
                color: white;
            }
            
            .footer {
                background-color: var(--bg-dark);
                color: var(--text-light);
                padding: var(--spacing-lg) 0;
                margin-top: var(--spacing-xl);
            }
            
            /* Add your custom styles below */
            
        """.trimIndent()
    }

    private fun javascriptTemplate(fileName: String): String {
        return """
            /**
             * Nashiyomi Code Editor - JavaScript File
             * File: $fileName
             */
            
            // Main function that runs when the document is ready
            document.addEventListener('DOMContentLoaded', function() {
                console.log('Nashiyomi Code Editor - JavaScript is running!');
                init();
            });
            
            /**
             * Initialize the application
             */
            
            // Utility functions
            const Utils = {
                /**
                 * Format a date object to a readable string
                 * @param {Date} date - The date to format
                 * @param {string} format - Optional format specification
                 * @returns {string} - Formatted date string
                 */
                formatDate(date, format = 'full') {
                    if (!date) return 'Invalid date';
                    
                    switch(format) {
                        case 'short':
                            return date.getMonth()+1 + '/' + date.getDate() + '/' + date.getFullYear();
                        case 'time':
                            return date.toLocaleTimeString();
                        case 'full':
                        default:
                            return date.toLocaleString();
                    }
                },
                
                /**
                 * Log messages with timestamp and level
                 * @param {string} message - Message to log
                 * @param {string} level - Log level (info, warn, error, debug)
                 */
                log(message, level = 'info') {
                    if (level === 'debug' && !CONFIG.debug) return;
                    
                    const timestamp = this.formatDate(new Date(), 'time');
                    const prefix = '[' + timestamp + '] [' + level.toUpperCase() + ']';
                    
                    switch(level) {
                        case 'error':
                            console.error(prefix + ' ' + message);
                            break;
                        case 'warn':
                            console.warn(prefix + ' ' + message);
                            break;
                        case 'debug':
                            console.debug(prefix + ' ' + message);
                            break;
                        default:
                            console.log(prefix + ' ' + message);
                    }
                },
                
                /**
                 * Generate a random ID string
                 * @returns {string} - A random ID string
                 */
                generateId() {
                    return Math.random().toString(36).substring(2) + Date.now().toString(36);
                },
                
                /**
                 * Make an asynchronous HTTP request
                 * @param {string} url - The URL to request
                 * @param {Object} options - Request options
                 * @returns {Promise<Object>} - Response data
                 */
                async fetchData(url, options = {}) {
                    const defaultOptions = {
                        method: 'GET',
                        headers: {
                            'Content-Type': 'application/json',
                            'Accept': 'application/json'
                        }
                    };
                    
                    const mergedOptions = { ...defaultOptions, ...options };
                    
                    try {
                        this.log('Fetching data from: ' + url, 'debug');
                        const response = await fetch(url, mergedOptions);
                        
                        if (!response.ok) {
                            throw new Error('HTTP error ' + response.status);
                        }
                        
                        const data = await response.json();
                        return data;
                    } catch (error) {
                        this.log('Error fetching data: ' + error.message, 'error');
                        throw error;
                    }
                }
            };
            
            // Data models
            class DataModel {
                constructor(data = {}) {
                    this.id = data.id || Utils.generateId();
                    this.createdAt = data.createdAt ? new Date(data.createdAt) : new Date();
                    this.updatedAt = data.updatedAt ? new Date(data.updatedAt) : new Date();
                }
                
                toJSON() {
                    return {
                        id: this.id,
                        createdAt: this.createdAt.toISOString(),
                        updatedAt: new Date().toISOString()
                    };
                }
            }
            
            class User extends DataModel {
                constructor(data = {}) {
                    super(data);
                    this.name = data.name || 'Anonymous';
                    this.email = data.email || '';
                    this.role = data.role || 'user';
                }
                
                isAdmin() {
                    return this.role === 'admin';
                }
                
                toJSON() {
                    return {
                        ...super.toJSON(),
                        name: this.name,
                        email: this.email,
                        role: this.role
                    };
                }
            }
            
            // Application class
            class Application {
                constructor(config) {
                    this.config = config;
                    this.user = null;
                    this.data = [];
                    this.isRunning = false;
                    
                    Utils.log('Application initialized: ' + config.appName + ' v' + config.version);
                }
                
                /**
                 * Initialize the application and load resources
                 * @returns {Promise<void>}
                 */
                async init() {
                    try {
                        Utils.log('Initializing application...');
                        
                        // Create a mock user
                        this.user = new User({
                            name: 'Nashiyomi User',
                            email: 'user@example.com',
                            role: 'admin'
                        });
                        
                        Utils.log('User authenticated: ' + this.user.name);
                        Utils.log('Application initialized successfully', 'info');
                        return true;
                    } catch (error) {
                        Utils.log('Initialization error: ' + error.message, 'error');
                        return false;
                    }
                }
                
                /**
                 * Start the application
                 */
                async start() {
                    if (this.isRunning) {
                        Utils.log('Application is already running', 'warn');
                        return;
                    }
                    
                    Utils.log('Starting ' + this.config.appName + '...');
                    
                    const initialized = await this.init();
                    if (!initialized) {
                        Utils.log('Could not start application due to initialization failure', 'error');
                        return;
                    }
                    
                    this.isRunning = true;
                    Utils.log('Application started successfully');
                    
                    // Load some sample data
                    await this.loadSampleData();
                    
                    // Process data example
                    this.processData();
                }
                
                /**
                 * Load sample data for demonstration
                 */
                async loadSampleData() {
                    Utils.log('Loading sample data...');
                    
                    // Mock data loading from API
                    try {
                        // Simulate API delay
                        await new Promise(resolve => setTimeout(resolve, 500));
                        
                        this.data = [
                            { id: 1, title: 'Item One', status: 'active', priority: 'high' },
                            { id: 2, title: 'Item Two', status: 'pending', priority: 'medium' },
                            { id: 3, title: 'Item Three', status: 'completed', priority: 'low' }
                        ];
                        
                        Utils.log('Loaded ' + this.data.length + ' data items');
                    } catch (error) {
                        Utils.log('Error loading sample data: ' + error.message, 'error');
                    }
                }
                
                /**
                 * Process the loaded data
                 */
                processData() {
                    if (!this.data || this.data.length === 0) {
                        Utils.log('No data to process', 'warn');
                        return;
                    }
                    
                    Utils.log('Processing data...');
                    
                    // Filter, map and reduce operations example
                    const activeItems = this.data.filter(item => item.status === 'active');
                    const itemTitles = this.data.map(item => item.title);
                    const priorityCount = this.data.reduce((acc, item) => {
                        acc[item.priority] = (acc[item.priority] || 0) + 1;
                        return acc;
                    }, {});
                    
                    // Output results
                    Utils.log('Active items: ' + activeItems.length);
                    Utils.log('All item titles: ' + itemTitles.join(', '));
                    Utils.log('Priority distribution: ' + JSON.stringify(priorityCount));
                }
                
                /**
                 * Shutdown the application
                 */
                shutdown() {
                    if (!this.isRunning) {
                        Utils.log('Application is not running', 'warn');
                        return;
                    }
                    
                    Utils.log('Shutting down application...');
                    // Clean up resources, save state, etc.
                    
                    this.isRunning = false;
                    Utils.log('Application shut down successfully');
                }
            }
            
            // Initialize and start the application
            const app = new Application(CONFIG);
            
            // Use an immediately invoked async function to use await at the top level
            (async () => {
                try {
                    await app.start();
                    
                    // This is where you would typically initialize your UI or event listeners
                    Utils.log('Ready to handle user interactions');
                    
                    // Simulate a shutdown after some time (in a real app this would be triggered by user action)
                    // setTimeout(() => app.shutdown(), 5000);
                } catch (e) {
                    Utils.log('Application error: ' + e.message, 'error');
                }
            })();
            
            // Export important objects for testing or extension
            if (typeof module !== 'undefined' && module.exports) {
                module.exports = { app, Utils, User, Application };
            }
        """.trimIndent()
    }

    private fun markdownTemplate(): String {
        return """
            # Nashiyomi Documentation
            
            Welcome to the Nashiyomi documentation. This is a comprehensive Markdown template created in the Nashiyomi Code Editor.
            
            ![Nashiyomi Logo](https://via.placeholder.com/150x50?text=Nashiyomi)
            
            ## Table of Contents
            
            - [Introduction](#introduction)
            - [Features](#features)
            - [Getting Started](#getting-started)
            - [Code Examples](#code-examples)
            - [Formatting Guide](#formatting-guide)
            - [Advanced Usage](#advanced-usage)
            - [Troubleshooting](#troubleshooting)
            - [FAQ](#faq)
            - [Contributing](#contributing)
            
            ## Introduction
            
            Nashiyomi is a modern manga reader with built-in code editing capabilities. This document serves as a guide to help you get the most out of the application.
            
            > **Note:** This is a template file. Feel free to modify it according to your needs.
            
            ## Features
            
            ### Core Features
            
            - **Manga Library Management**: Organize your manga collection efficiently
            - **Code Editor**: Built-in multi-language editor with syntax highlighting
            - **Dark Mode**: Automatic dark mode support for comfortable reading
            - **Cross-platform**: Available on multiple platforms
            
            ### Code Editor Features
            
            1. **Syntax Highlighting**: Support for multiple languages
            2. **Code Templates**: Pre-built templates for quick starts
            3. **File Management**: Create, edit, and organize your code files
            4. **Custom Themes**: Personalize your coding environment
            
            ## Getting Started
            
            ### Installation
            
            ```bash
            # Clone the repository
            git clone https://github.com/example/nashiyomi.git
            
            # Navigate to the project directory
            cd nashiyomi
            
            # Build the project
            ./gradlew build
            ```
            
            ### Configuration
            
            Create a `config.yaml` file in the root directory with the following settings:
            
            ```yaml
            theme: dark
            language: en
            defaultEditor: html
            autoSave: true
            ```
            
            ## Code Examples
            
            ### HTML Example
            
            ```html
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Nashiyomi</title>
                <link rel="stylesheet" href="styles.css">
            </head>
            <body>
                <header>
                    <h1>Welcome to Nashiyomi</h1>
                    <nav>
                        <ul>
                            <li><a href="#">Home</a></li>
                            <li><a href="#">Library</a></li>
                            <li><a href="#">Settings</a></li>
                        </ul>
                    </nav>
                </header>
                <main>
                    <section class="content">
                        <h2>Your Manga Collection</h2>
                        <p>Start building your collection today!</p>
                    </section>
                </main>
                <footer>
                    <p>&copy; 2025 Nashiyomi - Modern Manga Reader</p>
                </footer>
            </body>
            </html>
            ```
            
            ### JavaScript Example
            
            ```javascript
            /**
             * Nashiyomi App Controller
             * A simple JavaScript example
             */
            class NashiyomiApp {
                constructor() {
                    this.appName = "Nashiyomi";
                    this.initialized = false;
                    console.log("App instance created");
                }
                
                init() {
                    console.log("Initializing app...");
                    // Setup code here
                    this.initialized = true;
                    return this;
                }
                
                start() {
                    if (!this.initialized) {
                        this.init();
                    }
                    console.log("App started successfully!");
                }
            }
            
            // Create and start the app
            const app = new NashiyomiApp();
            app.start();
            ```
            
            ## Formatting Guide
            
            ### Text Formatting
            
            *Italic text* or _italic text_
            
            **Bold text** or __bold text__
            
            ***Bold italic text*** or ___bold italic text___
            
            ~~Strikethrough text~~
            
            ### Lists
            
            #### Unordered Lists
            
            - Item 1
            - Item 2
              - Subitem 2.1
              - Subitem 2.2
            - Item 3
            
            #### Ordered Lists
            
            1. First item
            2. Second item
               1. Subitem 2.1
               2. Subitem 2.2
            3. Third item
            
            ### Links and Images
            
            [Visit Nashiyomi Website](https://example.com/nashiyomi)
            
            ![Alt text for image](https://via.placeholder.com/300x200?text=Nashiyomi+Screenshot)
            
            ### Tables
            
            | Feature | Description | Status |
            | ------- | ----------- | ------ |
            | Manga Library | Organize and read manga | ✅ |
            | Code Editor | Edit code with syntax highlighting | ✅ |
            | Dark Mode | Enhanced reading comfort | ✅ |
            | Cloud Sync | Sync your settings across devices | 🚧 |
            
            ### Blockquotes
            
            > This is a blockquote
            > 
            > It can span multiple lines
            
            ### Horizontal Rule
            
            ---
            
            ## Advanced Usage
            
            ### Keyboard Shortcuts
            
            | Action | Windows/Linux | macOS |
            | ------ | ------------- | ----- |
            | Save | <kbd>Ctrl</kbd> + <kbd>S</kbd> | <kbd>⌘</kbd> + <kbd>S</kbd> |
            | New File | <kbd>Ctrl</kbd> + <kbd>N</kbd> | <kbd>⌘</kbd> + <kbd>N</kbd> |
            | Find | <kbd>Ctrl</kbd> + <kbd>F</kbd> | <kbd>⌘</kbd> + <kbd>F</kbd> |
            | Replace | <kbd>Ctrl</kbd> + <kbd>H</kbd> | <kbd>⌘</kbd> + <kbd>H</kbd> |
            
            ### Custom Themes
            
            Nashiyomi supports custom themes through CSS files. Create a theme in the following format:
            
            ```css
            /* darkspace.css */
            :root {
                --bg-primary: #1a1a2e;
                --text-primary: #e6e6e6;
                --accent-color: #4d8cff;
            }
            ```
            
            ## Troubleshooting
            
            ### Common Issues
            
            **Problem**: App crashes on startup
            
            **Solution**: Try clearing the cache and restarting the application
            
            ```bash
            # Clear cache command
            ./nashiyomi --clear-cache
            ```
            
            **Problem**: Code editor not saving files
            
            **Solution**: Check write permissions in your directory
            
            ## FAQ
            
            **Q: How do I update Nashiyomi?**
            
            A: Use the built-in updater or download the latest version from our website.
            
            **Q: Is Nashiyomi open source?**
            
            A: Yes, Nashiyomi is open source and available under the MIT license.
            
            ## Contributing
            
            We welcome contributions to Nashiyomi! Please read our contributing guidelines before submitting pull requests.
            
            ### Code of Conduct
            
            Please note that this project is released with a Contributor Code of Conduct. By participating in this project you agree to abide by its terms.
            
            ---
            
            *Created with the Nashiyomi Code Editor*
            
            Copyright © 2025 Nashiyomi. All rights reserved.
        """.trimIndent()
    }
}
