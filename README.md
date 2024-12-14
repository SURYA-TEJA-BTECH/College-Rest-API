# **COLLEGE-REST-API**

This project allows users to perform CRUD (Create, Read, Update, Delete) operations on student data. However, only users with admin privileges can delete or update student data and have the ability to change a student's status to "inactive." It uses the H2 in-memory database to store student data. The database is lightweight and runs entirely in memory, making it suitable for testing and development purposes. Once the application is shut down, the data is lost.

---

## **H2 Database**

H2 is an in-memory relational database primarily used for lightweight, embedded databases in Java applications. It supports SQL queries and is commonly used for testing and development because it doesn't require installation.

**URL to access the H2 database**: [http://localhost:8083/h2-console/](http://localhost:8083/h2-console/)

![H2 Database Image](https://github.com/user-attachments/assets/3d9ca197-069d-4a0f-af97-671e2efa7eb8)

---

## **Validations**

The following validations have been implemented on student data:  

- `@NotEmpty(message = "Name is required")`  
- `@NotEmpty(message = "Address is required")`  
- `@NotEmpty(message = "Students should register at least one student")`  

---

## **Assumptions Made**

In this project, **soft deleting** is used instead of hard deleting student data. This is achieved by using a `StudentStatus` field of type `enum`. When a user tries to delete student data, it doesn't get permanently deleted from the database. Instead, the `StudentStatus` enum is set to `INACTIVE`, indicating that the student is inactive but still exists in the system for audit or record-keeping purposes.

---

## **Project Structure**

![Project Structure](https://github.com/user-attachments/assets/86d9e2d3-c61a-4942-b5fc-c65c0d4a190d)

---

## **REST Endpoints**

Here’s a quick breakdown of the REST endpoints:  

1. `GET /api/students`: Retrieves a list of students, paginated by `pageNo`.  
2. `GET /api/students/{id}`: Retrieves details of a student by their ID.  
3. `POST /api/students`: Registers a new student.  
4. `PUT /api/students`: Updates student data.  
5. `PATCH /api/students/{id}`: Updates the address of a student.  
6. `DELETE /api/students/{id}`: Soft deletes a student by setting their status to `INACTIVE`.  

---

## **Testing with Postman or Swagger**

You can test the endpoints using **Postman** or **Swagger UI**.  

### **Swagger UI**

Swagger documentation is included to test the endpoints directly from the browser. It provides sample data for each endpoint and automatically generates the necessary URL. You can access the Swagger documentation by visiting the following URL: [http://localhost:8083/swagger-ui/index.html#/](http://localhost:8083/swagger-ui/index.html#/)

**Swagger UI Documentation**  
The Swagger UI will display the available endpoints, response codes, and response messages, allowing you to interact with the API easily.  

![Swagger UI Screenshot](https://github.com/user-attachments/assets/54fa7359-246d-4cf1-accc-972707e94ede)

---

## **Future Enhancements**

1. **Sending Emails to Students**  
   I plan to implement an email notification system to send emails to students:  
   - After successful registration.  
   - Whenever their details are updated.  

---

## **Conclusion**

This project provides simple and effective management of student data with the ability to soft delete records. The API is fully documented using Swagger, making it easy for developers to test and interact with the endpoints. The use of H2 as an in-memory database ensures that the application is lightweight and easy to set up for testing or development purposes.  
