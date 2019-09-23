resource "kubernetes_service" "k8s_svc" {
  metadata {
    name = "${var.app_name}-svc"
  }
  spec {
    selector = {
      app = "${kubernetes_pod.k8s_pod.metadata.0.labels.app}"
    }
    session_affinity = "ClientIP"
    port {
      name="http"
      port = "${var.port}"
      target_port = "${var.target_port}"
    }

    type = "LoadBalancer"
  }
}

resource "kubernetes_pod" "k8s_pod" {
  metadata {
    name = "${var.app_name}-pod"
    labels = {
      app = "${var.app_name}"
    }
  }

  spec {
    container {
      image = "${var.docker_image}"
      name  = "${var.app_name}"

      dynamic "env" {
        for_each = "${var.env_map}"
        content {
          name = "${env.key}"
          value   = "${env.value}"
        }
      }
    }
  }
}