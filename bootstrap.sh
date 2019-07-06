cd ~

# # Update and upgrade machine
# sudo apt -y update
# sudo apt -y upgrade

# # Install git
# sudo apt install -y git

# Get and install Sketch from source
sudo apt install -y autoconf automake libtool bash bison flex gcc g++ make perl zsh
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


# # Clone repo
# git clone https://github.com/plum-umd/java-sketch.git

# # Get make, maven, and openjdk for building jsketch
# sudo add-apt-repository ppa:openjdk-r/ppa
# sudo apt-get update
# sudo apt install -y openjdk-8-jdk
# sudo apt install -y make
# sudo apt install -y maven

# # Build JLibSketch
# cd java-sketch
# cd jskparser
# make

# sudo apt-get -y update
# sudo apt-get -y install gnupg2 curl git build-essential software-properties-common
# sudo add-apt-repository -y ppa:ubuntu-toolchain-r/test
# sudo sh -c 'echo "deb http://apt.postgresql.org/pub/repos/apt/ trusty-pgdg main" >> /etc/apt/sources.list.d/pgdg.list'
# wget --quiet -O - https://www.postgresql.org/media/keys/ACCC4CF8.asc | sudo apt-key add -
# sudo apt-get -y update
# sudo apt-get install -y gcc g++ gcc-6 g++-6 postgresql-10 libpq-dev redis-server imagemagick libmagickwand-dev libxml2-dev libxslt1-dev
# sudo update-alternatives --remove-all gcc
# sudo update-alternatives --install /usr/bin/gcc gcc /usr/bin/gcc-6 80 --slave /usr/bin/g++ g++ /usr/bin/g++-6
# sudo -u postgres createuser vagrant --superuser
# sudo rm ~/mysql-apt-config_0.7.2-1_all.deb

# # Install RVM
# gpg2 --keyserver hkp://pool.sks-keyservers.net --recv-keys 409B6B1796C275462A1703113804BB82D39DC0E3 7D2BAF1CF37B13E2069D6956105BD0E739499BDB
# curl -sSL https://get.rvm.io | bash -s stable
# source /home/vagrant/.rvm/scripts/rvm

# # Install Ruby
# rvm install 2.4.5
# rvm use ruby-2.4.5
# gem install bundler

# # Setup RDL
# echo "Installing RDL ..."
# git clone https://github.com/plum-umd/rdl
# git clone https://github.com/mckaz/db-types
# cd rdl
# git checkout pldi-comp-types
# gem build rdl.gemspec
# gem install rdl-2.1.0.gem
# cd ~

# # Setup Discourse
# echo "Installing Discourse ..."
# git clone https://github.com/mckaz/discourse-typecheck
# cd discourse-typecheck
# bundle install
# bundle exec rake db:create db:migrate
# RAILS_ENV=test bundle exec rake db:create db:migrate
# cd ~

# # Setup Huginn
# echo "Installing Huginn ..."
# git clone https://github.com/ngsankha/huginn
# cd huginn
# cp .env.example .env
# bundle install
# bundle exec rake db:create db:migrate db:seed
# cd ~

# # Setup Journey
# git clone https://github.com/mckaz/journey
# cd journey
# cp /vagrant/journey_database.yml config/database.yml
# bundle install
# bundle exec rake db:migrate
# cd ~

# # Setup Code.org
# git clone --depth 1 --no-single-branch https://github.com/mckaz/code-dot-org
# cd code-dot-org
# bundle install
# cd pegasus
# rake pegasus:setup_db
# RAILS_ENV=test rake pegasus:setup_db
# cd ../dashboard
# bundle exec rails db:environment:set RAILS_ENV=development
# bundle exec rake db:create
# bundle exec rake db:schema:load
# git checkout nondep
# bundle install
# git checkout staging
# cd ~

# # Setup Wikipedia Gem
# git clone https://github.com/ngsankha/wikipedia-client
# cd wikipedia-client
# bundle install
# cd ~

# # Setup Twitter Gem
# git clone https://github.com/ngsankha/twitter
# cd twitter
# bundle install
# cd ~
