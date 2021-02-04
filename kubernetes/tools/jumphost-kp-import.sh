wget https://github.com/vmware-tanzu/kpack-cli/releases/download/v0.1.4/kp-linux-0.1.4
chmod +x kp-linux-0.1.4
sudo mv kp-linux-0.1.4 /usr/local/bin/kp

wget https://github.com/pivotal-cf/pivnet-cli/releases/download/v2.0.2/pivnet-linux-amd64-2.0.2
chmod +x pivnet-linux-amd64-2.0.2
sudo mv pivnet-linux-amd64-2.0.2 /usr/local/bin/pivnet

wget https://github.com/vmware-tanzu/carvel-ytt/releases/download/v0.30.0/ytt-linux-amd64
wget https://github.com/vmware-tanzu/carvel-kbld/releases/download/v0.27.0/kbld-linux-amd64
wget https://github.com/vmware-tanzu/carvel-kapp/releases/download/v0.34.0/kapp-linux-amd64

pivnet login --api-token <TOKEN>

pivnet download-product-files --product-slug='build-service' --release-version='1.0.3' --product-file-id=817468
pivnet download-product-files --product-slug='tbs-dependencies' --release-version='100.0.49' --product-file-id=842517

tar xvf build-service-1.0.3.tar -C /tmp

docker login harbor.tanzu.be
docker login registry.pivotal.io

kbld relocate -f images.lock --lock-output images-relocated.lock --repository harbor.hubau.cloud/library/build-service

kubectl create namespace kpack

ytt -f values.yaml -f manifests/ | kbld -f images-relocated.lock -f- | kapp deploy -a tanzu-build-service -f- -y

# Download images.lock, values.yaml and descriptor file from https://network.pivotal.io as detailed in the documentation.
kp import -f descriptor-100.0.34.yaml
