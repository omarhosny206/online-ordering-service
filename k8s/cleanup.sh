#!/bin/bash

# Delete Kubernetes namespace and all resources within it
kubectl delete namespace ingress-nginx

# Delete other resources
kubectl delete -f .

# Remove host entry from /etc/hosts
INGRESS_NGINX_HOST="api.online-ordering-service.com"
grep -v "$INGRESS_NGINX_HOST" /etc/hosts > /etc/hosts_temp
cp /etc/hosts_temp /etc/hosts
rm -f /etc/hosts_temp

echo "Cleanup has been done."
