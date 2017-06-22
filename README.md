# JAVA-spring-boot-jwt-facebook
Spring Boot &amp; JWT &amp; Facebook API integration (backend)

Project for quick bootstrapping Spring Boot token-based authentication with third-party authentication provider (Facebook) 

Should be launched in pair with frontend part https://github.com/JackGoode/ANGULAR-spring-boot-jwt-facebook project. 
Minimum requirement is to have a registered facebook application with id and secret to fill the <b>appId</b> and <b>appSecret</b> properties: 

Provides support for facebook login with configurable permissions to access user data in Graph API. 
After successful FB login "Profile" screen has button to fill the fields with facebook data 
  
You also should fill in the <b>tokenSigningKey</b> property with random hash to encode the generated tokens

Both JAVA and ANGULAR applications can be deployed to Heroku