# Multi-Tenant Network Isolation

A web-based network isolation simulator built using Spring Boot, Java, Thymeleaf, HTML, CSS, and JavaScript.

This project demonstrates how multiple tenants can share the same network infrastructure while remaining logically isolated from one another. The application simulates VLAN-based network separation and ACL-style communication rules.

## Project Description

In a multi-tenant network, different departments or organizations may use the same physical network. For security and privacy, one tenant should not access another tenant's devices.

This application simulates network isolation using:

- Separate tenant networks
- Tenant-specific devices
- Communication permission checking
- Same-tenant communication
- Cross-tenant blocking
- Shared services access
- REST APIs
- Interactive web dashboard

## Technologies Used

- Java 21
- Spring Boot
- Spring Web
- Thymeleaf
- HTML5
- CSS3
- JavaScript
- Maven
- Docker
- Render

## Network Design

| Tenant | VLAN-like ID | Network |
|---|---:|---|
| Computer Science | 10 | 192.168.10.0/24 |
| Electronics | 20 | 192.168.20.0/24 |
| Administration | 30 | 192.168.30.0/24 |
| Shared Services | 100 | 192.168.100.0/24 |

## Communication Rules

1. Devices in the same tenant can communicate.
2. Devices from different tenants cannot communicate.
3. Approved tenants can access shared services.
4. Unknown devices are blocked.
5. Every communication attempt returns an explanation.

## Example Devices

| Device | IP Address | Tenant |
|---|---|---|
| CS-PC-1 | 192.168.10.10 | Computer Science |
| CS-PC-2 | 192.168.10.11 | Computer Science |
| ECE-PC-1 | 192.168.20.10 | Electronics |
| ECE-PC-2 | 192.168.20.11 | Electronics |
| ADMIN-PC-1 | 192.168.30.10 | Administration |
| ADMIN-PC-2 | 192.168.30.11 | Administration |
| WEB-SERVER | 192.168.100.10 | Shared Services |

## Project Structure

```text
multi-tenant-network/
├── Dockerfile
├── pom.xml
├── README.md
└── src/
    └── main/
        ├── java/
        │   └── com/
        │       └── nikhil/
        │           └── multitenantnetwork/
        │               ├── MultiTenantNetworkApplication.java
        │               ├── controller/
        │               │   ├── NetworkApiController.java
        │               │   └── PageController.java
        │               ├── model/
        │               │   ├── Tenant.java
        │               │   ├── Device.java
        │               │   └── CommunicationResult.java
        │               └── service/
        │                   └── NetworkIsolationService.java
        └── resources/
            ├── application.yaml
            ├── templates/
            │   └── index.html
            └── static/
                ├── css/
                │   └── style.css
                └── js/
                    └── app.js
```

## Requirements

- Java 21 or later
- Maven 3.9 or later
- Git
- Internet connection for downloading dependencies

## Run Locally

Clone the repository:

```bash
git clone https://github.com/YOUR_USERNAME/multi-tenant-network.git
cd multi-tenant-network
```

Start the application:

```bash
mvn spring-boot:run
```

Open the application:

```text
http://localhost:8080/
```

## Test the REST APIs

### Get all tenants

```text
GET /api/network/tenants
```

Example:

```text
http://localhost:8080/api/network/tenants
```

### Get all devices

```text
GET /api/network/devices
```

Example:

```text
http://localhost:8080/api/network/devices
```

### Check communication

```text
GET /api/network/check?sourceIp=SOURCE_IP&destinationIp=DESTINATION_IP
```

Example for allowed communication:

```text
http://localhost:8080/api/network/check?sourceIp=192.168.10.10&destinationIp=192.168.10.11
```

Expected result:

```json
{
  "sourceIp": "192.168.10.10",
  "destinationIp": "192.168.10.11",
  "sourceDevice": "CS-PC-1",
  "destinationDevice": "CS-PC-2",
  "allowed": true,
  "message": "ALLOWED: Same-tenant communication"
}
```

Example for blocked communication:

```text
http://localhost:8080/api/network/check?sourceIp=192.168.10.10&destinationIp=192.168.20.10
```

Expected result:

```json
{
  "sourceIp": "192.168.10.10",
  "destinationIp": "192.168.20.10",
  "sourceDevice": "CS-PC-1",
  "destinationDevice": "ECE-PC-1",
  "allowed": false,
  "message": "BLOCKED: Cross-tenant communication is not allowed"
}
```

## Demonstration

Use the dashboard to perform these tests:

| Source | Destination | Expected Result |
|---|---|---|
| CS-PC-1 | CS-PC-2 | Allowed |
| CS-PC-1 | ECE-PC-1 | Blocked |
| ECE-PC-1 | ADMIN-PC-1 | Blocked |
| CS-PC-1 | WEB-SERVER | Allowed |

## Docker Deployment

Build the Docker image:

```bash
docker build -t multi-tenant-network .
```

Run the container:

```bash
docker run -p 8080:8080 -e PORT=8080 multi-tenant-network
```

Open:

```text
http://localhost:8080/
```

## Render Deployment

This project can be deployed on Render using Docker.

1. Push the project to GitHub.
2. Open Render.
3. Create a new Web Service.
4. Select the GitHub repository.
5. Choose Docker as the runtime.
6. Set the Dockerfile path to:

```text
./Dockerfile
```

7. Keep the Docker build context as:

```text
.
```

8. Click **Deploy Web Service**.

The application uses the Render port through the following configuration:

```yaml
server:
  port: ${PORT:8080}
  address: 0.0.0.0
```

## Project Objectives

- Understand multi-tenant networking.
- Simulate VLAN-based network separation.
- Implement ACL-style access control.
- Prevent unauthorized cross-tenant communication.
- Provide controlled access to shared services.
- Develop a web-based network management dashboard.

## Advantages

- Improves tenant privacy.
- Prevents unauthorized communication.
- Uses shared infrastructure efficiently.
- Provides a simple visual demonstration.
- Can be extended with databases and authentication.

## Future Enhancements

- Add MySQL database support.
- Add user authentication and authorization.
- Add tenant and device management screens.
- Store communication history.
- Add audit logs.
- Add network topology visualization.
- Add administrator and tenant roles.
- Add monitoring and reporting features.

## Important Note

This project is a software simulation of VLAN and ACL-based network isolation. It does not configure physical switches or routers. Instead, the Spring Boot application evaluates communication requests and decides whether they should be allowed or blocked.

## Author

**Nikhil Dutta Anala**

## License

This project is created for educational and academic purposes.