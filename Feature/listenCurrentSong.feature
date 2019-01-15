
Feature:
As a user, I want to listen to my university's current song, so I can discover awesome music. 
 
Scenario: Users logged in a university, should be able to listen current song.
 
Given "BCNACTIVA" is playing: 

	
		|  title 		| artistName	| duration | 				songUrl      |         imageUrl  			|     
		|   Hero    | Adele   		| 	300 	 | http://www.test.com |	 http://www.test.com  |	       
		
		
 
When user searches for university "BCNACTIVA" current song 
Then will return a song:  

	|  title 		| artistName	| duration | 				songUrl      |        imageUrl  			|     
	|   Hero    | Adele   		| 	300 	 | http://www.test.com |   http://www.test.com  |
		 
Scenario: If there is not a current song, user should receive a Not Found error.  

Given "BCNACTIVA" is not playing any song
When user searches for university's "BCNACTIVA" current song 
Then Not Found error will be returned  


Scenario: If there is no time left to the current song, user should receive a Not Found error. 

Given "BCNACTIVA" is playing a song:

		|  title 		| artistName	| duration | 				songUrl    |        imageUrl  			|   
		|   Hero    | Adele   		| 	1 	 | http://www.test.com |   http://www.test.com  |
		
And System waits for "2" seconds   
When user searches for "BCNACTIVA" song  
Then Not Found error will be shown


