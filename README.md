# CI/CD Pipeline for Containerized Spring Boot REST Service


## 🚀 Project Overview

This project demonstrates a **production-grade CI/CD pipeline** for a containerized Spring Boot REST service, implementing automated deployment to AWS ECS with zero-downtime strategies and rollback capabilities.

### ✨ Key Features

- **🔄 Fully Automated CI/CD Pipeline** - From code commit to production deployment
- **🐳 Containerized Deployment** - Docker images with AWS ECR integration  
- **☁️ Cloud-Native Architecture** - AWS ECS Fargate for serverless containers
- **🔄 Zero-Downtime Deployments** - Rolling updates with health checks
- **⚡ Rapid Rollback** - 5-minute emergency rollback capability
- **🔒 Enterprise Security** - Secure secrets management and IAM authentication
- **🧪 Quality Gates** - Automated testing and static code analysis

## 🏗️ Architecture

```
┌─────────────┐    ┌──────────────┐    ┌─────────────┐    ┌──────────────┐
│   GitHub    │ -> │ GitHub       │ -> │   Amazon    │ -> │   Amazon     │
│ Repository  │    │ Actions      │    │    ECR      │    │     ECS      │
│             │    │ (CI/CD)      │    │ (Registry)  │    │ (Container)  │
└─────────────┘    └──────────────┘    └─────────────┘    └──────────────┘
```

### Pipeline Flow
1. **Code Push** → GitHub Repository
2. **Build & Test** → Maven compilation, JUnit tests, Checkstyle analysis
3. **Docker Build** → Container image creation and ECR push
4. **Deploy** → ECS Fargate deployment with rolling updates
5. **Monitor** → Health checks and deployment verification

## 🛠️ Technology Stack

- **Application:** Spring Boot 3.x with JDK 21
- **Build Tool:** Maven with Maven Wrapper
- **Containerization:** Docker with multi-stage builds
- **CI/CD Platform:** GitHub Actions
- **Container Registry:** Amazon ECR
- **Container Orchestration:** Amazon ECS Fargate
- **Infrastructure:** AWS (ECS, ECR, VPC, Security Groups)
- **Code Quality:** Checkstyle, JUnit testing

## 🚀 Quick Start

### Prerequisites
- JDK 21+
- Maven 3.8+
- Docker
- AWS CLI (for manual deployment)

### Local Development
```bash
# Clone the repository
git clone https://github.com/TalaAF/CI-CD.git
cd CI-CD

# Build the application
./mvnw clean package

# Run locally
./mvnw spring-boot:run

# Build Docker image
docker build -t spring-boot-app .

# Run container
docker run -p 8080:8080 spring-boot-app
```

### Application Endpoints
- **Health Check:** `http://localhost:8080/actuator/health`
- **API Documentation:** `http://localhost:8080/swagger-ui.html`
- **Application API:** `http://localhost:8080/api/employees`

## 🔄 CI/CD Pipeline

### Automated Triggers
- **Push to `main`** → Full deployment pipeline
- **Push to `develop`** → Build and test only
- **Pull Requests** → Build and test validation
- **Manual Trigger** → Rollback capability

### Pipeline Stages

#### 1. **Build & Test**
- ✅ Java compilation with Maven
- ✅ Unit test execution (JUnit)
- ✅ Static code analysis (Checkstyle)
- ✅ JAR artifact generation

#### 2. **Docker Build & Push**
- ✅ Multi-stage Docker build
- ✅ Image tagging (commit SHA + latest)
- ✅ Push to Amazon ECR
- ✅ Vulnerability scanning

#### 3. **Deploy to ECS**
- ✅ ECS task definition update
- ✅ Rolling deployment strategy
- ✅ Health check validation
- ✅ Deployment monitoring

#### 4. **Notification**
- ✅ Success/failure reporting
- ✅ Deployment metrics
- ✅ Performance monitoring

### Performance Metrics
- **Build Time:** ~41 seconds
- **Docker Build:** ~16 seconds  
- **Deployment:** ~4 minutes
- **Rollback:** ~5 minutes
- **Total Pipeline:** ~6 minutes

## 🔄 Rollback Procedures

### Emergency Rollback
1. Navigate to **Actions** → **CI/CD Pipeline**
2. Click **"Run workflow"**
3. Set **Rollback:** `true`
4. Specify **Target revision:** (e.g., `2`)
5. Click **"Run workflow"**

### Automatic Rollback Triggers
- Health check failures
- Deployment timeout
- Container startup errors

## 🔒 Security Features

### Secrets Management
- **AWS credentials** stored in GitHub Secrets
- **Environment-specific** configuration
- **No hardcoded** sensitive data
- **IAM role-based** authentication

### Container Security
- **Private ECR registry** with IAM authentication
- **Read-only container** filesystem
- **Security group** restrictions
- **VPC isolation**

## 🌍 Environment Configuration

### Development
```yaml
Environment: development
Profile: dev
Port: 8080
Database: H2 (in-memory)
```

### Production  
```yaml
Environment: production
Profile: prod
Port: 8080
Database: AWS RDS (configured via environment variables)
Scaling: Auto-scaling enabled
```

## 📊 Monitoring & Observability

### Application Monitoring
- **Health Checks:** Spring Boot Actuator
- **Metrics:** Micrometer with CloudWatch
- **Logging:** CloudWatch Logs integration
- **Alerting:** ECS service events

### Infrastructure Monitoring
- **Container Insights:** ECS performance metrics
- **CloudWatch Dashboards:** Custom application metrics
- **AWS X-Ray:** Distributed tracing (optional)

## 🚀 Deployment History

### Recent Deployments
- **v1.0.22** - Added rollback functionality and enhanced monitoring
- **v1.0.21** - Implemented zero-downtime deployment strategy  
- **v1.0.20** - Initial production deployment with ECS Fargate

## 🤝 Contributing

### Branching Strategy
```
main/           # Production-ready code
develop/        # Integration branch
feature/*       # New features
hotfix/*        # Emergency fixes
```

### Development Workflow
1. Create feature branch from `develop`
2. Implement changes with tests
3. Create pull request to `develop`
4. Code review and approval
5. Merge to `develop` for testing
6. Deploy to production via `main`

### Code Quality Standards
- **Test Coverage:** Minimum 80%
- **Checkstyle:** Must pass all checks
- **Code Review:** Required for all changes
- **Documentation:** Update README for significant changes

## 📚 Documentation

### Project Documentation
- **Architecture Decision Records (ADRs):** `docs/adr/`
- **API Documentation:** Generated via Swagger/OpenAPI
- **Deployment Guide:** `docs/deployment.md`
- **Troubleshooting:** `docs/troubleshooting.md`

### External Links
- **AWS ECS Documentation:** [ECS User Guide](https://docs.aws.amazon.com/ecs/)
- **GitHub Actions:** [Workflow Documentation](https://docs.github.com/en/actions)
- **Spring Boot:** [Reference Documentation](https://spring.io/projects/spring-boot)

## 🆘 Troubleshooting

### Common Issues

#### Pipeline Failures
```bash
# ECR authentication issues
aws ecr get-login-password --region us-east-1

# Check ECS service status
aws ecs describe-services --cluster spring-boot-cluster --services spring-boot-service
```

#### Application Issues
```bash
# Check container logs
aws logs tail /ecs/spring-boot-app --follow

# Health check endpoint
curl http://[ECS-PUBLIC-IP]:8080/actuator/health
```

#### Rollback Procedures
1. Identify last known good version
2. Use manual workflow dispatch
3. Monitor deployment progress
4. Verify application functionality

## 📈 Performance Optimization

### Current Optimizations
- **Multi-stage Docker builds** for smaller images
- **Maven dependency caching** in CI/CD
- **ECS task right-sizing** for optimal resource usage
- **Rolling deployments** for zero downtime

### Future Enhancements
- **Blue/Green deployments** for even safer releases
- **Automated scaling** based on traffic patterns  
- **Performance testing** integration in pipeline
- **Multi-region deployment** for high availability

## 📞 Support

### Getting Help
- **Issues:** Create GitHub issue for bugs or feature requests
- **Discussions:** Use GitHub Discussions for questions
- **Documentation:** Check `docs/` directory for detailed guides

---

## 🏆 Project Achievements

- ✅ **Production-Grade Pipeline** - Enterprise-level CI/CD implementation
- ✅ **Zero-Downtime Deployments** - Continuous service availability
- ✅ **5-Minute Rollbacks** - Rapid recovery capabilities
- ✅ **Automated Quality Gates** - Comprehensive testing and analysis
- ✅ **Cloud-Native Architecture** - Scalable AWS infrastructure
- ✅ **Security Best Practices** - Industry-standard secret management

**Built with ❤️ by [Tala Faraj](https://github.com/TalaAF)**

---

*This project demonstrates modern DevOps practices and cloud-native application deployment strategies suitable for enterprise production environments.*
