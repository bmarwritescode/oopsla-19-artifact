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
mkdir sketch
cd sketch
wget https://bitbucket.org/gatoatigrado/sketch-frontend/get/8e90a92ccd0f.zip
wget https://bitbucket.org/gatoatigrado/sketch-backend/get/7cdf7bda0816.zip
unzip 8e90a92ccd0f.zip
unzip 7cdf7bda0816.zip
mv gatoatigrado-sketch-frontend-8e90a92ccd0f sketch-frontend
mv gatoatigrado-sketch-backend-7cdf7bda0816 sketch-backend
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
