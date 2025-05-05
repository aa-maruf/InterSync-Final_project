# InternSync

InternSync is an internship management platform that connects students with companies offering internship opportunities. The application allows users to log in, browse and apply for internships, and employers to post job listings.

##  Implemented Features

- **User Authentication**:
  - Secure login and registration using Firebase Authentication.
  - Password reset functionality.
- **Internship Browsing & Applications**:
  - Users can view available internships and apply.
- **Job Posting**:
  - Companies can post internship opportunities.
- **Navigation Drawer**:
  - Smooth navigation across different app sections.
- **Firebase Firestore Integration**:
  - Real-time database for job postings and applications.
- **RecyclerView UI**:
  - Dynamic display of job listings.
- **CV Upload**:
  - Users can upload CVs for job applications.
- **Email Notifications**:
  - Employers receive applicant details via email.

##  Installation & Setup

1. Clone the repository:
   ```
   git clone https://github.com/aa-maruf/InterSync-Final_project.git
   ```
2. Open the project in **Android Studio**.
3. Install dependencies and necessary Gradle plugins.
4. Configure Firebase:
   - Add `google-services.json` in the `app/` directory.
   - Set up Firestore database with collections (`Users`, `JobPost`, `PublicJobPostings`).
5. Build and run the project on an emulator or a physical Android device.

##  Project Structure

```
InternSync/
│── app/src/main/java/com/example/project_login_regetration/
│   ├── authentication/      # Login, registration, password reset
│   ├── job_posting/         # Internship listings & job posting features
│   ├── navigation/          # Navigation drawer setup
│   ├── models/              # Data models for job postings and user profiles
│   ├── adapters/            # RecyclerView adapters for job listings
│   ├── utils/               # Firebase helper functions
```

##  Future Work

- **Intern Progress Tracking** (Implement internship progress updates for interns and mentors)
- **Chat Feature** (Enable messaging between users and employers)
- **Enhanced Job Recommendation System** (Suggest relevant internships based on user profiles)
- **Performance Monitoring** (Allow mentors to review intern performance)

---

