# WinPAMS
A Windows Forms application to manage pet adoptions. This application was created as a project as a requirement
for the course "DCIT-24" at the Cavite State University - CCAT Campus.

## Features

- **User Registration and Authentication**: Users can create accounts within the application,
  providing necessary information such as name, contact details, and preferences. Secure
  authentication mechanisms ensure only registered users can access the system.
- **Pet Browsing**: Users can browse through a comprehensive list of pets available for
  adoption within the application. Pets are categorized based on species, breed, age, and other
  relevant attributes. Each pet listing includes detailed information, such as photos,
  descriptions, health status, and behavioral traits, displayed in an easy-to-navigate interface.
- **Adoption Request Submission**: Interested users can submit adoption requests for specific
  pets directly through the application. Adoption requests may include additional
  information, such as reasons for adoption, living conditions, and previous pet ownership
  experience, provided via intuitive input forms.
- **Administrator Dashboard**: Administrators have access to a dedicated dashboard within
  the application for managing adoption requests and overseeing system activities. The
  dashboard provides an overview of pending, approved, and rejected adoption requests,
  along with relevant details of each request, displayed in a tabular format for easy
  monitoring.
- **Interview Scheduling**: Administrators can schedule interviews with prospective adopters
  directly within the application. The scheduling feature allows administrators to set up
  convenient meeting times and communicate with users through integrated messaging
  capabilities.
- **Application Review and Approval**: Administrators review adoption requests within the
  application, considering factors such as applicant suitability, pet compatibility, and
  adherence to adoption policies. They have the authority to approve or reject adoption
  applications based on their assessment, with options to update the status of each request
  accordingly.


## Technologies Used
[![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)]()
[![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)]()
[![IntelliJ](https://img.shields.io/badge/Intellij%20Idea-000?logo=intellij-idea&style=for-the-badge)]()
[![VSCode](https://img.shields.io/badge/Vscode-007ACC?style=for-the-badge&logo=visualstudiocode&logoColor=white)]()


## Project Structure
The project is structured into the following main components:

- **com.winpams.user**: The main Windows Forms application project containing the user interface
- **com.winpams.core**: A class library project containing the core business logic and data access
- **com.winpams.data**: A class library project containing data models and database access
- **com.winpams.admin**: A class library project containing the admin dashboard functionality