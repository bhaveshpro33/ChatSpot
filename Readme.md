# ChatSpot

ChatSpot is a real-time chat application built using **Spring Boot**, **MQTT**, and **MongoDB**. It enables users to communicate in private chatrooms with a seamless and efficient messaging experience.

## Features

- 📡 **Real-time messaging** with MQTT
- 🔐 **User authentication & status management**
- 🗂 **MongoDB as the database** for storing chat messages
- 🎨 **Frontend served from the static folder**
- 🏗 **Built with Spring Boot & MQTT** for smooth communication

## Technologies Used

- **Spring Boot** - Backend framework
- **MQTT** - Lightweight messaging protocol
- **MongoDB** - NoSQL database for chat storage
- **Lombok** - Simplifies Java class development
- **Maven** - Dependency management
- **Docker** - Containerized deployment
- **SockJS** - Fallback support in frontend

## Understanding MQTT

### MQTT Protocol
MQTT is a lightweight messaging protocol used for real-time communication, especially in scenarios where network bandwidth is limited. It works on a **publish-subscribe model**, meaning that messages are sent to a broker and then distributed to subscribers. In **ChatSpot**, MQTT ensures efficient and reliable message delivery between users.

Example:
- **John** publishes a message to `marianne/queue/messages`.
- **Marianne** subscribes to that topic and receives messages in real-time.

![MQTT Pub/Sub Model](src\main\resources\static\img\MQTT.png)

### Docker for Deployment
The application can be **containerized using Docker**, allowing it to run consistently across different environments. Docker makes it easy to deploy **ChatSpot** in any cloud platform.

#### Running with Docker
```sh
docker build -t chatspot .
docker run -p 8080:8080 chatspot
```

### SockJS in Frontend
SockJS is used as a **WebSocket fallback mechanism** in the frontend. It ensures compatibility with browsers that do not support WebSockets by falling back to techniques like **XHR streaming and long polling**.

## Setup Instructions

### 1️⃣ Clone the Repository
```sh
git clone https://github.com/your-username/chatspot.git
cd chatspot
```

### 2️⃣ Configure MongoDB
- Update `application.properties` with your **MongoDB URI**:
```properties
spring.data.mongodb.uri=mongodb+srv://<username>:<password>@cluster.mongodb.net/chatspot
spring.data.mongodb.database=chatspot
```

### 3️⃣ Build & Run the Application
```sh
mvn clean install
mvn spring-boot:run
```

The application will start at: `http://localhost:8080/`

## API Endpoints

| Method | Endpoint | Description |
|--------|---------|-------------|
| GET | `/users` | Get all users |
| GET | `/chatrooms/{chatId}` | Get chatroom details |
| POST | `/messages` | Send a new message |

## Contributing
Feel free to open an issue or submit a pull request. Contributions are welcome!

## License
This project is licensed under the MIT License.

---
Developed with ❤️ using **Spring Boot & MQTT**.

