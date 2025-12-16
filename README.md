# 🚀 DevOps Orange Technical Assignment

## 🧩 Overview
This project demonstrates the setup and automation of a complete CI/CD environment using **Docker**, **Minikube**, **Terraform**, **Ansible**, **Helm**, and **GitLab**.  
It implements an end-to-end DevOps pipeline — from code build to deployment on Kubernetes.

---

## ⚡ Quick Start
```bash
# 1️⃣ Start Minikube
minikube start --driver=docker

# 2️⃣ Create namespaces
kubectl create namespace build
kubectl create namespace dev
kubectl create namespace test

# 3️⃣ Install Docker
sudo apt-get update -y
sudo apt-get install ca-certificates curl -y
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] \
https://download.docker.com/linux/ubuntu $(lsb_release -cs) stable" | sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update -y
sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y

# 4️⃣ Install Terraform & Helm
sudo apt-get install terraform -y
curl https://raw.githubusercontent.com/helm/helm/main/scripts/get-helm-3 | bash

# 5️⃣ Deploy GitLab via Docker Compose
docker compose up -d

# 6️⃣ Deploy Nexus via Terraform + Ansible
terraform init && terraform apply -auto-approve
ansible-playbook playbook.yaml

# 7️⃣ Deploy GitLab Runner
helm repo add gitlab https://charts.gitlab.io/
helm install gitlab-runner gitlab/gitlab-runner \
  --namespace gitlab-runner --create-namespace \
  --set gitlabUrl="http://host.minikube.internal:8082/" \
  --set runnerRegistrationToken="YOUR_TOKEN" \
  --set rbac.create=true --set runners.privileged=true

# 8️⃣ Deploy MySQL
helm install mysql bitnami/mysql -n dev --set auth.rootPassword=rootpassword,auth.database=mydb

# 9️⃣ Run GitLab Pipeline
# -> build image, push to Nexus, deploy via Helm
```

 ## 🧩 Architecture Diagram
        +-------------+
        |   Developer |
        +------+------+   
               |
               v
        +------+------+
        |   GitLab CI |
        +------+------+   
               |
     (1) Build | Test | Deploy
               |
               v
        +------+------+
        |    Nexus    |  <-- Docker image storage
        +------+------+
               |
               v
        +------+------+
        |  Minikube   |  <-- Local Kubernetes cluster
        +------+------+
               |
               v
        +------+------+
        |  Helm Chart |  <-- Deploy Spring Boot App
        +------+------+
               |
               v
        +------+------+
        | Application |
        +-------------+


