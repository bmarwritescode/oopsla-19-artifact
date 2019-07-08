cd ~

# Install git
sudo apt install -y git

# Get make, maven, and openjdk for building jsketch
sudo add-apt-repository -y ppa:openjdk-r/ppa
sudo apt-get -y update
sudo apt install -y openjdk-8-jdk
sudo apt install -y make
sudo apt install -y maven

# Get and install Sketch from source
sudo apt install -y autoconf automake libtool bash bison flex gcc g++ perl zsh unzip
cp /vagrant/sketch.tar.gz ./
tar -xzf sketch.tar.gz
cd sketch
cd sketch-frontend
make assemble-noarch
cd ../sketch-backend
./autogen.sh
chmod +x ./configure
./configure
make clean
make
cd ../sketch-frontend
make system-install DESTDIR=/usr/bin SUDOINSTALL=1
cd ../..

# Clone JSketch repo
git clone https://github.com/plum-umd/java-sketch.git

# Build JLibSketch
cd java-sketch
cd jskparser
make

# Copy artifact materials into Java-sketch repo
cd ..
cp -R /vagrant/artifact_materials/artifact_examples ./artifact_examples/
cp -R /vagrant/artifact_materials/artifact_results ./artifact_results/
cp -R /vagrant/artifact_materials/artifact_scripts ./artifact_scripts/
cp -R /vagrant/artifact_materials/benchmarks ./benchmarks/

# Install Sloccount
sudo apt install -y sloccount
