#!/bin/bash

# 0. sudo 설치
echo "0. sudo 설치"
apt-get update
apt-get install sudo -y
echo "sudo 설치 완료"

# 1. OpenJDK 17 JRE 설치
echo "1. OpenJDK 17 JRE 설치"
sudo apt-get install openjdk-17-jre -y

# 2. 특정 계정(teammeta) 생성 및 sudo 권한 부여
echo "2. teammeta 계정 생성"
sudo adduser --disabled-password --gecos "" teammeta
echo "teammeta:1234" | sudo chpasswd
sudo usermod -aG sudo teammeta

# 3. 로케일 설정
echo "3. 로케일 설정"
sudo apt-get -y install locales
sudo apt-get -y install language-pack-ko
sudo locale-gen ko_KR.utf8
sudo dpkg-reconfigure locales
echo "로케일이 설정되었습니다."

# 4. 환경변수 설정
echo "4. 환경변수 설정"
echo "export LANGUAGE=ko_KR.UTF-8" >> /home/teammeta/.bashrc
echo "export LANG=ko_KR.UTF-8" >> /home/teammeta/.bashrc
source /home/teammeta/.bashrc
echo "환경변수가 설정되었습니다."

# 5. JAR 파일을 ./teammeta/ 폴더로 이동
echo "5. JAR 파일을 ./teammeta/ 폴더로 이동"
cp /home/mp4.jar /home/teammeta/mp4.jar
cd /home/teammeta

# 6. 백그라운드에서 JAR 파일 실행
echo "6. 백그라운드에서 JAR 파일 실행"
sudo -u teammeta nohup java -jar /home/teammeta/mp4.jar > /home/teammeta/mp4.log 2>&1 &

# 7. 삼바 설치
echo "7. 삼바 설치"
sudo apt-get -y install samba

# 8. 삼바 설정
echo "8. 삼바 설정"
sudo cp /etc/samba/smb.conf /etc/samba/smb.conf.backup
sudo sh -c "echo -e '[video]\n   path = /home/teammeta/video\n   read only = no\n   guest ok = yes\n   create mask = 0777\n   directory mask = 0777' >> /etc/samba/smb.conf"
sudo smbpasswd -a teammeta
sudo systemctl restart smbd

# 9. 백그라운드에서 실행된 JAR 파일의 실시간 로그 출력
echo "9. 백그라운드에서 실행된 JAR 파일의 실시간 로그 출력"
tail -f /home/teammeta/mp4.log