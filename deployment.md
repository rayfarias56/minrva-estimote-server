# Deployment


## Staging

For project staging we use a free Heroku app located at (https://minrva-wayfinder.herokuapp.com/). The advantage of using Heroku for staging is that it's free and easy to deploy and setup. For an understanding on how the project was setup for deployment see [here](https://devcenter.heroku.com/articles/java-webapp-runner). Additionally, the enviornment variables had to be set using "heroku config:set"

Firstly, for deployment privileges create a Heroku account and then request app co-owner rights with someone who already has rights. You should also make sure you have the Heroku CLI (toolbelt) installed. Then deployment is as easy as issuing the command:

$ git subtree push --prefix minrva-estimote-server heroku master

This command simply executes a push to heroku yet only of the project sub folder. This allows Heroku to easily detect the pom.xml and Procfile. 


SN: The web app may take a while to start or show a "Failure to start error" when accessing the url after it's been down for awhile. Heroku's free app puts a project to sleep after 30 minutes of inaccess. After your first access the project will start and will work just fine on subsequent accesses. In otherwords, just refresh the page and it'll run at normal speed without errors (unless those errors are from the project itself). 
