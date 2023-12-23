# Online Ordering System
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=Hibernate&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)
![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens)
![Swagger](https://img.shields.io/badge/-Swagger-%23Clojure?style=for-the-badge&logo=swagger&logoColor=white)
![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)
[![Maven](https://badgen.net/badge/icon/maven?icon=maven&label)](https://https://maven.apache.org/)

![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Kubernates](https://img.shields.io/badge/kubernetes-%23326ce5.svg?style=for-the-badge&logo=kubernetes&logoColor=white)
![AWS](https://img.shields.io/badge/Amazon_AWS-FF9900?style=for-the-badge&logo=amazonaws&logoColor=white)
![Ubuntu](https://img.shields.io/badge/Ubuntu-E95420?style=for-the-badge&logo=ubuntu&logoColor=white)
![GitHub Actions](https://img.shields.io/badge/github%20actions-%232671E5.svg?style=for-the-badge&logo=githubactions&logoColor=white)

This platform offers a seamless experience for customers to browse, order, and manage their purchases. Additionally,
sellers have access to tools for managing their inventory and interacting with customers. Below are the key features
provided by the system:
  
## **Features**

- ### Customer Features:

  - **User Cart Management:**
    - **View Cart:** Customers can view their shopping cart, displaying the products they've added.
    - **Clear Cart:** Customers can clear their entire cart, removing all selected items.

  - **Product Catalog:**
    - **Browse Products:** Users can view the list of available products.
    - **View Product Details:** Customers can see detailed information about a specific product.
    - **Add to Cart:** Users can add products to their shopping cart.

  - **Order Management:**
    - **View Order Details:** Customers can view detailed information about a specific order.
    - **Place Order:** Users can place an order, confirming their product selections.
    - **Cancel Order:** Users can cancel their orders if they havn't created a delivery request for them those orders.

  - **Category Management:**
    - **View Categories:** Users can see a list of product categories.
    - **Add Category (Admin):** Admin users can add new product categories.

  - **Delivery Information:**
    - **View Delivery Details:** Users can see their delivery details.

  - **User Authentication:**
    - **Sign In:** Users can sign in to the system using JWT (JSON Web Tokens) for secure authentication.
    - **Token Management:** Users can obtain authentication tokens for secure access and can refresh tokens later.

- ### Seller Features:

  - **Seller Product Management:**
    - **View Seller Products:** Sellers can view a list of products they are selling.
    - **Add Seller Product:** Sellers can add new products to their inventory.
    - **Update Seller Product:** Sellers can update information about the products they are selling.
    - **Delete Seller Product:** Sellers can remove products from their inventory.

- ### Authentication and Authorization:

  - **Authentication Using JWT:**
    - The system employs JSON Web Tokens for secure user authentication, ensuring a reliable and secure login process.

  - **Role-based Authorization:**
    - The system implements role-based authorization with three roles: Admin, Seller, and Customer.
    - **Admin:** Has access to administrative functionalities, such as adding categories and managing products.
    - **Seller:** Can manage their products, view and update product information.
    - **Customer:** Engages in browsing, ordering, and managing their purchases.

These features collectively provide a comprehensive online ordering experience, with secure authentication and role-based authorization tailored to the specific needs of administrators, sellers, and customers. Feel free to explore and make the most of your online shopping and selling experience!

## **Usage**
- Run in **development** environment:
  ```shell
  docker-compose -f docker-compose-dev.yaml up -d --build
  # to stop --> docker-compose -f docker-compose-dev.yaml down
  ```
- Run in **production** environment:
  ```shell
  docker-compose up -d --build
  # to stop --> docker-compose down
  ```

## **CI/CD 🚀** [`🔗`](./.github/workflows/cicd.yaml)
![CICD](https://github.com/omarhosny206/omarhosny206/assets/58389695/3e00292e-6229-41f2-aad8-2ee1ebfe9ec0)
  - **CI**:
    - Checkout the code.
    - Login to dockerhub.
    -  Build the docker image.
    - Push the docker image to dockerhub. 
  - **CD** (on AWS EC2 Ubuntu machine as GitHub Actions Self-hosted Runner [`🔗`](./setup-github-actions-runner.sh)):
    - Stop & Remove existing containers.
    - Delete existing images.
    - Checkout the code.
    - Run the containers via docker-compose.yaml file.

## **Database Design**
![DB_DIAGRAM](https://github.com/omarhosny206/github-actions/assets/58389695/4f216cfe-9aed-4893-b079-c7247df11890)

## **Tech Stack ⚡**
- Programming Language: Java 17
- Backend Framework: Spring Boot v3.1.2
- Database Engine: PostgreSQL
- Other Frameworks: Spring Security Data JPA, Hibernate
- API Documentation: Swagger via OpenApi 3.0
- Containerization and Orchestration: Docker [`🔗`](./Dockerfile) [`🔗`](./docker-compose.yaml), Kubernetes [`🔗`](./k8s)
- Cloud: AWS (EC2 Ubuntu Machine)
- CI/CD: GitHub Actions [`🔗`](./.github/workflows/cicd.yaml) [`🔗`](./setup-github-actions-runner.sh)
- Operating System: Ubuntu

## **Features to add in the future 💭**
- Adding OAuth 2.0.
- Signup with Google, Facebook and other platforms.
