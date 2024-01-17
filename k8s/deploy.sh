#!/bin/bash

# Create Kubernetes namespace
kubectl create namespace ingress-nginx

# Deploy Ingress Nginx Controller
kubectl apply -f ingress-nginx-controller.yaml

# Wait for Ingress Nginx Controller to be fully ready
echo "Waiting for Ingress Nginx Controller to be fully ready..."
kubectl wait --namespace ingress-nginx --for=condition=available deployment/ingress-nginx-controller --timeout=300s

# Get the ClusterIP of the Ingress Nginx Controller service
INGRESS_NGINX_IP=$(kubectl get svc ingress-nginx-controller -n ingress-nginx -o jsonpath='{.spec.clusterIP}')

# Deploy other resources
kubectl apply -f .

# Add host entry to /etc/hosts
echo "$INGRESS_NGINX_IP api.online-ordering-service.com" >> /etc/hosts

echo "Deployment has been done successfully."
