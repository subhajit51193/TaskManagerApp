
# TaskManagerApp

A task manager app where user can register and signin and create,update and delete task and see task details


## Features

- Register
- Login
- Logout
- Create task
- Get Task
- Update task
- Delete Task
- Exception handling
- Role based authorization


## API Reference

#### Follow below link for detailed API reference for the application

```http
  https://documenter.getpostman.com/view/24254665/2s9YsGhDQU
```
## Configurations Backend

Check following configurations Change DB details accordingly

```bash
#db specific properties
spring.datasource.url=jdbc:mysql://localhost:3306/taskmanager
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=root
spring.datasource.password=sql@subhajit51193

#ORM s/w specific properties
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

#RSA Key related
rsa.private-key=classpath:certs/private.pem
rsa.public-key=classpath:certs/public.pem

#Debugging
logging.level.org.springframework.security=DEBUG



```
    
## Run Locally Backend

Clone the project

```bash
  git clone https://github.com/subhajit51193/TaskManagerApp
```

Go to the project directory

```bash
  cd TaskManagerApp
```
```bash
  cd BackEnd
```

Install dependencies

```bash
  mvn clean install
```

Start the server

```bash
  mvn spring-boot:run
```

## Run Locally Frontend

Clone the project

```bash
  git clone https://github.com/subhajit51193/TaskManagerApp
```

Go to the project directory

```bash
  cd TaskManagerApp
```
```bash
  cd FrontEnd
```

Install dependencies

```bash
  npm install
```

Start the server

```bash
  npm run dev
```

## Deployment

Not Deployed



## Tech Stack

**Client:** Java, SpringBoot,JavaScript,React,TailwindCSS

**DataBase:** MySQL

**Server:** Embedded Tomcat


## Authors

- [@Subhajit Saha](https://github.com/subhajit51193)


## Feedback

If you have any feedback, please reach out to us at nnorth87@gmail.com


## 🔗 Links
[![portfolio](https://img.shields.io/badge/my_portfolio-000?style=for-the-badge&logo=ko-fi&logoColor=white)](https://subhajit51193.github.io/)
[![linkedin](https://img.shields.io/badge/linkedin-0A66C2?style=for-the-badge&logo=linkedin&logoColor=white)](https://www.linkedin.com/in/subhajit-saha-103110185/)




