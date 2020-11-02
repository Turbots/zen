#!/usr/bin/env bash

# Install Contour as Ingress Controller
kubectl create namespace projectcontour
kubectl apply -f https://projectcontour.io/quickstart/contour.yaml

# Configure External DNS with Google DNS
kubectl create secret generic gcp-key -n projectcontour --from-file=tools/gcp.key.json
helm upgrade -i external-dns bitnami/external-dns -n projectcontour --values tools/external-dns-values.yaml

# Install Cert Manager for automatic TLS certificate management
kubectl create namespace cert-manager
kubectl create secret generic gcp-key -n cert-manager --from-file=tools/gcp.key.json
helm install cert-manager jetstack/cert-manager -n cert-manager --version v1.0.3 --set installCRDs=true
kubectl apply -f tools/cert-issuers.yaml -n cert-manager

# Install Gitlab
kubectl create namespace gitlab
helm upgrade -i gitlab gitlab/gitlab -n gitlab --timeout 600s --values tools/gitlab-values.yaml
kubectl get secret gitlab-gitlab-initial-root-password -n gitlab -ojsonpath='{.data.password}' | base64 --decode ; echo

# Install Harbor
kubectl create namespace harbor
helm upgrade -i harbor bitnami/harbor -n harbor --values tools/harbor-values.yaml

# Install ArgoCD
kubectl create namespace argocd
helm upgrade -i argocd argo/argo-cd -n argocd --values tools/argocd-values.yaml
kubectl get pods -n argocd -l app.kubernetes.io/name=argocd-server -o name | cut -d'/' -f 2; echo
# This creates an HTTPProxy object which allows TLS traffic to pass through to the ArgoCD server- important for the CLI
kubectl apply -f tools/argocd-passthrough.yaml

# Install Velero (optional)
kubectl create namespace velero
helm upgrade -i velero vmware-tanzu/velero -n velero --values tools/velero-values.yaml --set-file credentials.secretContents.cloud=tools/gcp.key.json

# Install Tanzu Build Service
# Documentation: https://docs.pivotal.io/build-service/1-0/installing.html
# docker login harbor.hubau.cloud
# docker login registry.pivotal.io
# kbld relocate -f images.lock --lock-output images-relocated.lock --repository harbor.hubau.cloud/library
# kubectl create namespace kpack
# ytt -f values.yaml -f manifests/ | kbld -f images-relocated.lock -f- | kapp deploy -a tanzu-build-service -f- -y
# Download images.lock, values.yaml and descriptor file from https://network.pivotal.io as detailed in the documentation.

# Configure Tanzu Build Service
kp secret create harbor-credentials -n default --registry harbor.hubau.cloud --registry-user admin

kp image create zen -n default --tag harbor.hubau.cloud/library/zen:latest --blob https://gitlab.hubau.cloud/root/zen
