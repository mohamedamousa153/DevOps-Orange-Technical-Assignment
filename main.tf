provider "kubernetes" {
  config_path    = "~/.kube/config"
  config_context = "minikube"
}


resource "kubernetes_namespace_v1" "build" {
  metadata { name = "build" }
}

resource "kubernetes_namespace_v1" "dev" {
  metadata { name = "dev" }
}

resource "kubernetes_namespace_v1" "uat" {
  metadata { name = "uat" }
}


resource "kubernetes_persistent_volume_claim" "nexus_pvc" {
  metadata {
    name      = "nexus-pvc"
    namespace = kubernetes_namespace_v1.build.metadata[0].name 
  }
  spec {
    access_modes = ["ReadWriteOnce"]
    
    resources {
      requests = { storage = "10Gi" }
    }
  }
}

resource "kubernetes_deployment" "nexus" {
  metadata {
    name      = "nexus"
    namespace = kubernetes_namespace_v1.build.metadata[0].name 
    labels    = { app = "nexus" }
  }

  spec {
    replicas = 1

    selector {
      match_labels = { app = "nexus" }
    }

    template {
      metadata { labels = { app = "nexus" } }

      spec {
        container {
          name  = "nexus"
          image = "sonatype/nexus3:latest"

          port { container_port = 8081 }

          volume_mount {
            name       = "nexus-data"
            mount_path = "/nexus-data"
          }
        }

        volume {
          name = "nexus-data"
          persistent_volume_claim {
            claim_name = kubernetes_persistent_volume_claim.nexus_pvc.metadata[0].name
          }
        }
      }
    }
  }
}

resource "kubernetes_service" "nexus_svc" {
  metadata {
    name      = "nexus-service"
    namespace = kubernetes_namespace_v1.build.metadata[0].name
    labels    = { app = "nexus" }
  }

  spec {
    type     = "NodePort"
    selector = { app = "nexus" }

    port {
      port        = 8081   
      target_port = 8081     
      protocol    = "TCP"
      node_port   = 30001     
    }
  }
}
