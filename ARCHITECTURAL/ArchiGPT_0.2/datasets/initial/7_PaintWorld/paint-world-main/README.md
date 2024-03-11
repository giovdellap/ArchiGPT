# Paint World Mobile Application
An interactive playful application for children - software engineering course @ La Sapienza


## Project Structure





>
### Docker image for the mysql server with complete database.
>
> https://hub.docker.com/r/1972811/paint-world-swe

#### Dockerfile present in the parent directory of the repo.

>
How to run the docker container?
```
docker run --name some-mysql-name -p 3306:3306 -e MYSQL_ROOT_PASSWORD=password -d mysql_image_name:tag
```
[further read/reference](https://github.com/bazzani/mysql-5.5-docker)


>
### Paint world application user stories
> https://docs.google.com/spreadsheets/d/1j87l5g-WOTSfDJ2Ae4qy8TKJXvKh_xshLGgGfjgbdf4/
>


>
### Paint world sprint planning 
> https://docs.google.com/spreadsheets/d/1vwZyeXahJmSL6TOCS4kDRIFk0XPY_yCuXHgS21s3phI/
>



## External Requirement:


Simulator software for 2D graphics.
- https://solar2d.com/
- Download and install the above to run the applicatin present in ***Painting*** directory
