This Spring Boot application uses both MVC and REST controllers. Thymeleaf templates are used for the Admin and Doctor dashboards, while REST APIs serve all other modules. The application interacts with two databases—MySQL (for patient, doctor, appointment, and admin data) and MongoDB (for prescriptions). All controllers route requests through a common service layer, which in turn delegates to the appropriate repositories. MySQL uses JPA entities while MongoDB uses document models.

1. User accesses AdminDashboard or Appointment pages.
2. The action is routed to the appropriate Thymeleaf or REST controller.
3. The controller routes request to either an MVC controller (HTML) or REST controller (JSON).
4. Service Layer → Central business logic, independent of controllers.
5. Decision Point → Service decides whether to use MySQL (relational data) or MongoDB (prescriptions).
6. Repository Access → Calls JPA Repository (MySQL) or MongoRepository (MongoDB).
7. Database Interaction → Fetches or updates data in the respective database.
8. Response Back → Service returns data → Controller → User (via Thymeleaf page or JSON API).
