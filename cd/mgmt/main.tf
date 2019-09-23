provider "kubernetes" {
  host = "http://127.0.0.1:8001"
  token = "eyJhbGciOiJSUzI1NiIsImtpZCI6IiJ9.eyJpc3MiOiJrdWJlcm5ldGVzL3NlcnZpY2VhY2NvdW50Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9uYW1lc3BhY2UiOiJkZWZhdWx0Iiwia3ViZXJuZXRlcy5pby9zZXJ2aWNlYWNjb3VudC9zZWNyZXQubmFtZSI6ImRlZmF1bHQtdG9rZW4tcW1obGIiLCJrdWJlcm5ldGVzLmlvL3NlcnZpY2VhY2NvdW50L3NlcnZpY2UtYWNjb3VudC5uYW1lIjoiZGVmYXVsdCIsImt1YmVybmV0ZXMuaW8vc2VydmljZWFjY291bnQvc2VydmljZS1hY2NvdW50LnVpZCI6IjdkMWI3YWZkLWRkZWItMTFlOS1hODdjLTAyNTAwMDAwMDAwMSIsInN1YiI6InN5c3RlbTpzZXJ2aWNlYWNjb3VudDpkZWZhdWx0OmRlZmF1bHQifQ.aBkOAlkMRdEjqyZTxw-N7CDu-9nEuo1LTdx6laq9ml1V9GE94gFidhBE1rHnvOrFFjR5L6Wstogs-hNh8uqd9BmJJzkkPoQDCeExx3BSr5dErgHs4b7vxPxidogvB027GnmAUI1e4S5V3Uj8KvJOj2-MyBrKEy0AWFs8_zdUaJ9v7jJW6JskTMtEynimyv_xsI8cvP9xQEA9Be5mXa0uiFTF0OyIIUSUBDHWUvvkkPDrXhU8G8ie5NUrXcjjNHyw9qtuUdUKUKVfgAVIaM7qpl3nliS0RMOfrJdniXgPewICXKJ0vYEq2a-bAMNafbe4H60l_MdaT166SIwbJbM_YA"
}

module "zookeeper" {
  source = "../modules/services"

  docker_image="bitnami/zookeeper"
  app_name="zookeeper"
  port=2181
  target_port=2181
  env_map = {
    "ALLOW_ANONYMOUS_LOGIN"="yes"
  }
}

module "kafka" {
  source="../modules/services"

  docker_image="bitnami/kafka"
  app_name="kafka"
  port=9092
  target_port=9092
  env_map= {
    "ALLOW_PLAINTEXT_LISTENER"="yes",
    "KAFKA_CFG_ZOOKEEPER_CONNECT"="zookeeper-svc:2181"
  }
}

module "kafka-manager" {
  source="../modules/services"

  docker_image="sheepkiller/kafka-manager"
  app_name="kafka-manager"
  port=9000
  target_port=9000
  env_map= {
    "ZK_HOSTS"="zookeeper-svc:2181"
    "APPLICATION_SECRET"="letmein"
  }
}




