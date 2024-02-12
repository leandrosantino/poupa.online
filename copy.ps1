scp -i ./key.pem ./app/dist/* ec2-user@54.92.165.232:/home/ec2-user/poupa.online/app/dist
scp -i ./key.pem ./app/nginx.conf ec2-user@54.92.165.232:/home/ec2-user/poupa.online/app
scp -i ./key.pem ./app/Dockerfile ec2-user@54.92.165.232:/home/ec2-user/poupa.online/app

# scp -i ./key.pem ./api/build/libs/poupa-online-api-0.0.1.jar ec2-user@54.92.165.232:/home/ec2-user/poupa.online/api/build/libs
scp -i ./key.pem ./api/prod.env ec2-user@54.92.165.232:/home/ec2-user/poupa.online/api
scp -i ./key.pem ./api/Dockerfile ec2-user@54.92.165.232:/home/ec2-user/poupa.online/api

scp -i ./key.pem ./docker-compose.yaml ec2-user@54.92.165.232:/home/ec2-user/poupa.online
scp -i ./key.pem ./nginx.conf ec2-user@54.92.165.232:/home/ec2-user/poupa.online
scp -i ./key.pem ./proxy.dockerfile ec2-user@54.92.165.232:/home/ec2-user/poupa.online

