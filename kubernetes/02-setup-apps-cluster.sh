#!/usr/bin/env bash
# Installs the necessary tools for secure workload connectivity on this K8S on Google Cloud (basically External DNS and Cert Manager)

# Install Contour as Ingress Controller
kubectl create namespace projectcontour
kubectl apply -f https://projectcontour.io/quickstart/contour.yaml

# Configure External DNS with Google DNS
kubectl create secret generic gcp-key -n projectcontour --from-file=tools/gcp.key.json
helm upgrade -i external-dns bitnami/external-dns -n projectcontour --values tools/external-dns-values.yaml

# Install Cert Manager for automatic TLS certificate management
kubectl create namespace cert-manager
kubectl create secret generic gcp-key -n cert-manager --from-file=tools/gcp.key.json
helm upgrade -i cert-manager jetstack/cert-manager -n cert-manager --version v1.0.3 --set installCRDs=true
kubectl apply -f tools/cert-issuers.yaml -n cert-manager
