# Softweb-Solutions-Smart-Service-Request-Portal-Application

Smart Service Request Portal 

The Smart Service Request Portal is a full-stack, AI-enhanced web application designed to help internal users (e.g., employees, IT staff) efficiently manage and monitor service or maintenance requests. Key innovations include automated prioritization based on urgency and AI-powered prediction of future service loads using historical data. 

Basically, an app for the company to keep track of employee maintenance  

 

Features 

1. User Login Page 

Use Firebase to create a user Login Page  

Enable firebase council to store the data of user login information such as username and password 

Have security questions incase user forgets password 

Have an Admin Login and have an employee login 

1. User Portal 

Submit, edit, and view service requests 

Comments on the requests  

Authentication Feature 

Monitor status, category, and assigned technician 

Mobile-friendly and responsive (React + Bootstrap + Flexbox) 

2. Admin Dashboard 

View and filter all tickets by status, category, or technician 

Filters include, highest urgency, least urgent and moderately urgent 

Other filters can be services that are done, in progress, and still pending to look at 

Display AI-generated urgency score and predicted resolution time 

Visual reports: load trends, performance stats, and graphs 

3. Analytics Engine 

Analyze historical data for: 

1.) Peak request times 

2.) Most frequent issue categories 

3.) Technician workloads 

4.) How long requests would typically take to be completed   

Predict upcoming ticket volume using statistical models 

4. Java Microservice 

Handles background jobs: 

1.) Auto-closes tickets after resolution 

2.) Sends overdue notifications 

Fully tested using JUnit – Use Java to test application 

5. Authentication and Roles 

Role-based access with Firebase Auth 

1.) Admin: Full access to dashboard and analytics 

2.) Employee: Submit/view personal tickets only 

6. Data Storage and Offline Support 

Firebase Firestore/Realtime Database for persistent data 

Offline ticket draft saving using localStorage 

 

 AI/Analytics Highlights 

Predictive analytics engine using Python (Pandas, NumPy, Matplotlib) 

Optional NLP-based ticket categorization 

Server-side rendered visualizations (heatmaps, line graphs) 



Sample Service Types 

Internal Use (Softweb employees): 

IT support (hardware/software/network issues) 

Office maintenance (lighting, HVAC, workspace repairs) 

Security/access (badge reissue, restricted access requests) 

 

Client Use: 

Manufacturing: Equipment maintenance, hazard reporting 

Healthcare: Device servicing, sanitation, IT helpdesk 

Corporate: HR services, facility management, onboarding support 

 

 