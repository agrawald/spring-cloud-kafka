output "endpoint_url" {
  value = "http://${kubernetes_service.k8s_svc.load_balancer_ingress.0.ip}:${var.port}"
}
