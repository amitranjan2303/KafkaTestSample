# #Kafka Test Sample using Java

 Require Dependencies
 1. Docker Intallation 
 2. JDK Installation
 3. Kafka Installation
 4. Postman Installation

 Steps to use sample
# * Note for the latest changes please follow Dependencies Latest Guide line

   1 Start Docker 
   2 Start Zookeeper
   3 Start Kaka
   
 And now run you consumer and producer then open your postman 
   - use below json request to produce message 
      
    URL - http://localhost:8080/api/v1/agent/publish
 
 ```
   {
    "deviceId": "kjjkkljkljlk",
    "timestamp": "2025-05-23T17:28:17.925622511",
    "latitude": 30.2667,
    "longitude": -97.7431
  }

 ```
 
  - use below url to get produced messaage
  
    URL - http://localhost:8080/api/v1/user/message/{id}
  

<----------------------------------Enjoy your code--------------------------------->

## Author

Amit Ranjan  
Software Engineer  
<br>
**Mobile**: +91 9643 595 785  
**E-mail**: amitranjan2303@gmail.com  
**Skype**: amitranjan2330  
**LinkedIn**: https://www.linkedin.com/in/amitranjan5/
