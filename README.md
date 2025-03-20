# Project Documentation: Product Store App

## Introduction  
The Product Store App is a Kotlin-based Android application that fetches and displays a list of products from an API. It is built using the MVVM architecture and integrates Hilt for dependency injection, Coroutines and Flow for asynchronous programming, and Retrofit for networking. Offline feature is also implemented.  

This document provides:  
✔ Overview of the project  
✔ Technologies used  
✔ Screenshots of the UI  
✔ Steps to run the app  
✔ Trade-offs made during development  
✔ Possible improvements if given more time  

---

## Project Overview  

### 📂 Technologies & Tools Used  
| **Category** | **Technology Used** |
|-------------|---------------------|
| **Programming Language** | Kotlin |
| **Architecture** | MVVM (Model-View-ViewModel) |
| **Dependency Injection** | Hilt (Dagger) |
| **Networking** | Retrofit + OkHttp |
| **Asynchronous Programming** | Coroutines + Flow |
| **Navigation** | Jetpack Navigation Component |
| **Testing** | JUnit, MockK|
| **Image Loading** | Glide |
| **Build System** | Gradle |
| **Local Data Storage** | ROOM |

---

##  Screenshots 

### Splash screen 
| *Screenshot of the splash screen* |

<img width="264" alt="Screenshot 2025-03-20 at 12 36 23" src="https://github.com/user-attachments/assets/2014b6bf-889c-4701-b08e-16133241fbf9" />

### Entry Screen

| *Screenshot of the start screen* |

<img width="270" alt="Screenshot 2025-03-20 at 12 37 33" src="https://github.com/user-attachments/assets/7d3abe27-122d-4b06-a64d-85c358013d23" />

###  Product Home Screen

| *Screenshot of the Home screen* |

➡ It Displays a **list of products** fetched from the API.  
➡ Clicking on a product navigates to the **Product Details screen**.  

<img width="304" alt="Screenshot 2025-03-20 at 12 39 56" src="https://github.com/user-attachments/assets/cc56940c-fff8-4c40-a6cc-1b0a18c4fc75" />

###  Product Details Screen

| *Screenshot of the product details screen* |

<img width="319" alt="Screenshot 2025-03-20 at 12 40 35" src="https://github.com/user-attachments/assets/e60c0580-6342-4aeb-afc5-dd8f6ea04a16" />

➡ Displays **product image, name, category, price, and description**.  



---

##  Steps to Run the App 

### **1️⃣ Prerequisites**  
Before running the app, make sure you have:  
- **Android Studio (latest version)**  
- **Gradle 8+**  
- **JDK 11+**  
- **Internet connection (for API calls)**  

### **2️⃣ Clone the Repository**  
Open your terminal or command prompt and run:  
```sh
git clone https://github.com/your-repo-link.git
cd your-repo-folder
```

### **3️⃣ Open the Project in Android Studio**  
1. Open **Android Studio**  
2. Click on **File → Open Project**  
3. Select the cloned repository folder  
4. Let **Gradle sync**  

### **4️⃣ Run the App on an Emulator or Physical Device**  
- Click on **Run ▶** in Android Studio  
- Choose an **Emulator** or **connect a real device**  
- The app should launch and display the **product list**  

---

## 🔎 Trade-offs & Improvements

### Trade-offs Made
1. **Limited UI Enhancements** – Focused on functionality rather than animations.  
2. **Limited Testing Coverage** – While **ViewModel and Repository logic** were tested, UI tests were skipped due to time constraints.  

###  What Would I Do Differently with More Time?
1. **Improve UI with Animations & Loaders**  
   - Add **Shimmer Effect** to improve loading experience.  

2. **Increase Test Coverage**  
   - Add **UI tests using Espresso**.  
   - Use **MockWebServer** for API response testing.  

3. **Optimize API Calls**  
   - Implement **pagination** instead of fetching all products at once.   

---

## Conclusion  
This project successfully implements a **Product Store app** using **modern Android development practices**. While some trade-offs were made, future improvements can make it **more robust, user-friendly, and performant**.  
