# Electronic Store - Microservices Architecture

## Overview
An electronic store application built with Spring Cloud microservices architecture. This project demonstrates a modern, scalable e-commerce platform with separate services for different business domains.

## Architecture

### Core Components

#### 1. Service Registry (Eureka Server)
- **Port**: 8761
- **Purpose**: Service discovery and registration
- **Features**:
  - Service registration and discovery
  - Health monitoring
  - Load balancing support

#### 2. API Gateway (Spring Cloud Gateway)
- **Port**: 9090
- **Purpose**: Single entry point for all client requests
- **Features**:
  - Dynamic routing
  - Authentication filtering
  - Load balancing
  - Service discovery integration

### Microservices

#### Auth Service
- Handles user authentication and authorization
- Implements JWT (JSON Web Tokens)

#### Product Service
- Manages product catalog
- Endpoint: `/api/products/**`

#### Category Service
- Handles product categories
- Endpoint: `/api/categories/**`

#### Order Service
- Manages customer orders
- Endpoint: `/api/orders/**`

#### Order Item Service
- Handles individual items within orders
- Endpoint: `/api/order-items/**`

#### Cart Service
- Manages shopping cart functionality
- Endpoint: `/api/carts/**`

#### Cart Item Service
- Handles individual items in the shopping cart
- Endpoint: `/api/cart-item/**`

#### Payment Service
- Processes payments
- Endpoint: `/api/payment/**`

## Order Flow

The ordering process in the Electronic Store follows a series of steps to ensure a smooth transaction. Here's a detailed breakdown of the order flow:

### 1. User Authentication
1. User logs in through the Auth Service
2. Receives a JWT token for subsequent authenticated requests

### 2. Browsing and Cart Management
1. User browses products through the Product Service
2. Adds items to cart via the Cart Service
3. Cart Service communicates with Product Service to validate product availability
4. Cart items are stored with user session in Cart Item Service

### 3. Checkout Process
1. User initiates checkout
2. System validates cart contents and recalculates totals
3. User confirms shipping and billing information

### 4. Order Creation
1. System creates an order in the Order Service with status "PENDING"
2. Order Service creates order items in Order Item Service
3. System reserves inventory by updating Product Service

### 5. Payment Processing
1. System redirects to payment page
2. Payment Service processes the payment through a payment gateway
3. On successful payment:
   - Order status is updated to "PAYMENT_RECEIVED"
   - Payment record is created
   - Order Service is notified of successful payment

### 6. Order Fulfillment
1. Order Service updates status to "PROCESSING"
2. Inventory is updated in Product Service
3. Order is prepared for shipping

### 7. Shipping
1. Order status changes to "SHIPPED" when dispatched
2. Tracking information is updated
3. User receives shipping confirmation

### 8. Delivery and Completion
1. Order is marked as "DELIVERED" upon successful delivery
2. User can initiate returns/exchanges if needed

### Error Handling
- Failed payments trigger order status update to "PAYMENT_FAILED"
- Inventory issues trigger notifications to the user
- Order cancellation is possible before shipping

## Technology Stack

- **Framework**: Spring Boot 2.7.x
- **Service Discovery**: Spring Cloud Netflix Eureka
- **API Gateway**: Spring Cloud Gateway
- **Security**: Spring Security + JWT
- **Distributed Tracing**: Spring Cloud Sleuth + Zipkin
- **Configuration**: Spring Cloud Config
- **Build Tool**: Maven
- **Java Version**: 11+

## Getting Started

### Prerequisites
- Java 11 or higher
- Maven 3.6.3 or higher
- Git
- (Optional) Docker and Docker Compose

### Installation

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Electronic-Store-Microservice-Anguar
   ```

2. **Build the project**
   ```bash
   mvn clean install
   ```

3. **Start the services**
   Start the services in the following order:
   ```bash
   # 1. Start Eureka Server
   cd service-registry
   mvn spring-boot:run

   # 2. Start other services in any order
   cd ../auth-service
   mvn spring-boot:run

   cd ../product-service
   mvn spring-boot:run

   cd ../category-service
   mvn spring-boot:run

   cd ../order-service
   mvn spring-boot:run

   cd ../order-item-service
   mvn spring-boot:run

   cd ../payment-service
   mvn spring-boot:run

   cd ../cart-service
   mvn spring-boot:run

   cd ../cart-item-service
   mvn spring-boot:run

   # 3. Finally, start the API Gateway
   cd ../cloud-gateway
   mvn spring-boot:run
   ```

### Using Docker (Alternative)

1. **Build Docker images**
   ```bash
   mvn clean package -DskipTests
   docker-compose build
   ```

2. **Start all services**
   ```bash
   docker-compose up -d
   ```

## API Documentation

Once the services are running, you can access:

- **Eureka Dashboard**: http://localhost:8761
- **API Gateway**: http://localhost:9090
- **Swagger UI** (if enabled): http://localhost:8080/swagger-ui.html (for individual services)

## Development

### Code Style
- Follow Google Java Style Guide
- Use meaningful commit messages
- Write unit tests for new features

### Branching Strategy
- `main` - Production-ready code
- `develop` - Development branch
- `feature/*` - New features
- `bugfix/*` - Bug fixes
- `release/*` - Release preparation

## Monitoring

### Distributed Tracing
- **Zipkin**: http://localhost:9411 (if configured)
- **Sleuth**: Integrated for trace and span IDs in logs

### Logging
- Centralized logging with ELK stack (Elasticsearch, Logstash, Kibana) - recommended for production
- Log aggregation with Papertrail or similar services

## Security

- JWT-based authentication
- Role-based access control (RBAC)
- Password encryption with BCrypt
- HTTPS recommended for production
- CORS configuration
- CSRF protection

## Testing

### Unit Tests
```bash
mvn test
```

### Integration Tests
```bash
mvn verify
```

## Deployment

### Production Deployment
1. Configure application properties for production
2. Set up a CI/CD pipeline
3. Use container orchestration (Kubernetes recommended)
4. Configure monitoring and alerting

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## Acknowledgments

- Spring Framework Team
- Spring Cloud Team
- Open Source Community

## Support

For support, please open an issue in the GitHub repository.

---

*This README is a living document. Please update it as the project evolves.*
