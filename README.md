# 🚀 DevOps Orange Technical Assignment

## 🧩 Overview
This project demonstrates the setup and automation of a complete CI/CD environment using **Docker**, **Minikube**, **Terraform**, **Ansible**, **Helm**, and **GitLab**.  
It implements an end-to-end DevOps pipeline — from code build to deployment on Kubernetes.



---
# 🚀 Complete Installation Guide: Docker, Minikube, kubectl & Terraform

```bash

# =============================================
# COMPLETE INSTALLATION SCRIPT - COPY ALL BELOW
# =============================================

# 1. UPDATE SYSTEM
sudo apt-get update -y
sudo apt upgrade -y

# 2. INSTALL DOCKER
sudo apt update
sudo apt install ca-certificates curl -y
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc
echo \
  "Types: deb
URIs: https://download.docker.com/linux/ubuntu
Suites: $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}")
Components: stable
Signed-By: /etc/apt/keyrings/docker.asc" | sudo tee /etc/apt/sources.list.d/docker.sources > /dev/null
sudo apt update
sudo apt install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y
sudo systemctl start docker
sudo systemctl enable docker
sudo docker run hello-world

# 3. CONFIGURE DOCKER FOR NON-ROOT USER
sudo groupadd docker 2>/dev/null || true
sudo usermod -aG docker $USER
newgrp docker
docker run hello-world

# 4. INSTALL MINIKUBE
curl -LO https://github.com/kubernetes/minikube/releases/latest/download/minikube-linux-amd64
sudo install minikube-linux-amd64 /usr/local/bin/minikube
rm minikube-linux-amd64

# 5. INSTALL KUBECTL
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl"
curl -LO "https://dl.k8s.io/release/$(curl -L -s https://dl.k8s.io/release/stable.txt)/bin/linux/amd64/kubectl.sha256"
echo "$(cat kubectl.sha256)  kubectl" | sha256sum --check
sudo install -o root -g root -m 0755 kubectl /usr/local/bin/kubectl
rm kubectl kubectl.sha256

# 6. START MINIKUBE CLUSTER
minikube start --driver=docker

# 7. INSTALL TERRAFORM
sudo apt-get update && sudo apt-get install -y gnupg software-properties-common
wget -O- https://apt.releases.hashicorp.com/gpg | gpg --dearmor | sudo tee /usr/share/keyrings/hashicorp-archive-keyring.gpg > /dev/null
echo "deb [arch=$(dpkg --print-architecture) signed-by=/usr/share/keyrings/hashicorp-archive-keyring.gpg] https://apt.releases.hashicorp.com $(lsb_release -cs) main" | sudo tee /etc/apt/sources.list.d/hashicorp.list
sudo apt update
sudo apt-get install terraform -y

# =============================================
# VERIFICATION COMMANDS
# =============================================
echo "=== VERIFICATION ==="
docker --version
echo "---"
minikube version
echo "---"
kubectl version --client
echo "---"
terraform --version
echo "---"
minikube status
echo "---"
kubectl get nodes
echo "=== INSTALLATION COMPLETE ==="
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


