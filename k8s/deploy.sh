#!/bin/bash

# Create Kubernetes namespace
kubectl create namespace ingress-nginx

# Deploy Ingress Nginx Controller
kubectl apply -f ingress-nginx-controller.yaml

# Wait for Ingress Nginx Controller service to get an external IP
echo "Waiting for Ingress Nginx Controller to get an external IP..."
while [ -z "$(kubectl get svc ingress-nginx-controller -n ingress-nginx -o jsonpath='{.spec.clusterIP}')" ]; do
    sleep 5
done

# Get the IP address of Ingress Nginx Controller service
INGRESS_NGINX_IP=$(kubectl get svc ingress-nginx-controller -n ingress-nginx -o jsonpath='{.spec.clusterIP}')

# Deploy Postgres
kubectl apply -f postgres.yaml

# Deploy Tribe
kubectl apply -f online-ordering-service.yaml

# Add host entry to /etc/hosts
echo "$INGRESS_NGINX_IP api.online-ordering-service.com" | tee -a /etc/hosts

echo "Setup completed."
