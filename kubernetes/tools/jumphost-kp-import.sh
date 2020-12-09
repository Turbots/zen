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