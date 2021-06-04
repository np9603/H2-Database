# H2-Database

**Task 1 - Implement an enhanced feature to H2 database in terms of data storage and indexing. Such features can be adding a new supported data type (e.g., media data, password data) and improving the indexing.**

For implementing an enhanced feature for H2 Database in terms of data storage and indexing I decided to implement a new data type called PhoneNumber. Phone number is an important piece of information which is present in almost every database. For my project, I considered valid US phone numbers which follow the given constraints:

1. The phone number should have a length 10/12/13.
2. The user input should follow one of the following patterns:
2345678901
234-567-8901
(234)5678901
(234)567-8901
3. Finally, I compare this number with a list built with all the valid area codes as implemented by the North American Numbering Plan (NANP). It compares the first three digits from the user input and then compares it with the list entries and then returns if the number is a valid number or not. It also checks for the first digit of the number. If it is 0 or 1 then it is an invalid US number, otherwise it returns true.

H2 allows users to create their own data types by implementing the minimal required CustomDataTypesHandler API. To use this feature, the CustomPhoneDataTypeHandler.java file should implement all the abstract methods defined in the CustomDataTypesHandler interface. 

Following are the changes made to implement this feature: 

ErrorCode.java - Added another unique custom error code (90150) for throwing exceptions when the user enters an invalid phone number by incrementing the last error code value in the original errorcode file.

![image](https://user-images.githubusercontent.com/46695666/120840681-88dda680-c538-11eb-8c7a-e376e9113247.png)

Phone.java - The validatePhoneNumber function takes the phone number from the user as a string and then checks if it is a valid number or not. All the valid US area codes are stored in a list of strings called areaCodes. Along with this, regex patterns are used to eliminate invalid numbers and restrict the valid inputs to the above mentioned patterns.

![image](https://user-images.githubusercontent.com/46695666/120840763-a14dc100-c538-11eb-935a-da3e0191bf41.png)

![image](https://user-images.githubusercontent.com/46695666/120840832-b9254500-c538-11eb-95cf-51b04cc9df1e.png)

SysProperties.java - The CustomPhoneDataTypeHandler class name that is used must be the same on client and server to work correctly. This function provides support for user defined custom data types. The initial value is set to null if there are no custom data types used.

![image](https://user-images.githubusercontent.com/46695666/120840943-df4ae500-c538-11eb-9060-91ce8c58e6ce.png)

Value.java - This is the base class file which contains all the data types, conversion methods and comparison methods supported by the H2 database. For adding the custom data type Phone, the following code is used.

![image](https://user-images.githubusercontent.com/46695666/120841505-947d9d00-c539-11eb-9c9b-5cd73c74eea4.png)
![image](https://user-images.githubusercontent.com/46695666/120841744-e32b3700-c539-11eb-8d47-caf401fc52ae.png)

ValuePhone.java - This class overrides the methods from the Value class which is the base class for all data types.

_messages_en.prop - Added a line in the original error code file “90150=Invalid US phone number or phone number format “ to display the custom error message for invalid phone numbers.


Task 2 - Implement an enhanced feature to H2 database in terms of query processing and optimization. Such features can be adding a new query operator (e.g., new aggregate function, approximate search) and adding more join operations.
