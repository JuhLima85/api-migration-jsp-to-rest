web: java -Dserver.port=$PORT -Dspring.profiles.actve=local -jar target/devsibre-0.0.1-SNAPSHOT.jar



para iniciar um container que ja existe.(que contenha images)
 docker start NOME_CONTAINER (teste2)
ou docker stop e nome do servidor(teste2)
--------------------------------------------------------------
comando para ver as imagens no container
docker images
--------------------------------------------------------------
Renomear container
docker rename adoring_ardinghelli servidor-teste
----------------------------------------------------------------
Para Remover container
docker rm nome do container ou Id (após para o container) senão docker rm -f (para força)
----------------------------------------------------------------
Para Remover imagem
docker rmi e o Id da imagem
--------------------------------------------------------------
comando para visualizar o status do container
docker ps -a 
---------------------------------------------------------------
comando para entrar no container
docker attach nome do container ou Id
---------------------------------------------------------------
comando para sair do container
exit
-------------------------------------------------------------------
comando para mostrar atualizações ou tudo que foi mudado no container
docker diff nome do container ou Id
----------------------------------------------------------------------------
-------------------------comando para criar container--------------------------
docker run --name mybanco -e MYSQL_ROOT_PASSWORD=root -p 3306:3306 -d mysql
-----------------------------------------------------------------------------
docker exec -it "nome do container" mysql -p
docker run -it "nome do container" mysql -p

------------------------------postgres------------------------------------------
docker exec -it postgres-0 bash
psql -U postgres
\du -> para super usuario
psql -h localhost -p 5432 -U postgres

