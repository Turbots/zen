# Automated Path to Production with Open Source and VMware Tanzu

## Setup the Tooling cluster

In order to have a fully automated CI/CD pipeline, we require a couple of tools to be installed on our Kubernetes tooling cluster.

Run the `kubernetes/01-setup-tools-cluster.sh` script to install the following:

- Project Contour as the Ingress Controller
- External DNS using Google Cloud DNS (you need a GCP key)
- Cert Manager for automatic TLS Certificate management (you need a GCP key)
- Gitlab for the Git repositories and CI/CD pipeline
- Harbor as the container registry
- ArgoCD as the GitOps tool of choice

## Setup the Application cluster
