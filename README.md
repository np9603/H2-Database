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


**Task 2 - Implement an enhanced feature to H2 database in terms of query processing and optimization. Such features can be adding a new query operator (e.g., new aggregate function, approximate search) and adding more join operations.**

For implementing an enhanced feature for H2 Database in terms of query processing and optimization, I decided to implement a new aggregate function called GreaterThanAverage and LessThanAverage. Aggregate functions are functions that combines multiple records and then perform the function and return a single value as result.

● GreaterThanAverage is an aggregate function that calculates the average of all the values of a column in the table and returns the count of records that have a value greater than the average. The initial input is given in integer data type format and the aggregate function returns the result in integer format. 

● LessThanAverage is an aggregate function that calculates the average of all the values of a column in the table and returns the count of records that have a value less than and equal to the average. The initial input is given in integer data type format and the aggregate function returns the result in integer format.

For performing the same operation in H2, we have to use a subquery.

GreaterThanAverage
Example: Select count(*) from employee where salary > (Select avg(salary) from employee);

Using implemented GreaterThanAverage aggregate function:
Select GT_AVG(salary) from employee;

LessThanAverage
Example: Select count(*) from employee where salary <= (Select avg(salary) from employee);

Using implemented LessThanAverage aggregate function:
Select LT_AVG(salary) from employee;

H2 allows users to create their own aggregate functions by implementing the minimal required AggregateFunction interface. To use this feature, the GreaterThanAverage.java and LessThanAverage.java file should implement all the abstract methods defined in the AggregateFunction interface (add, getResult , getType , init methods). The created java file
needs to be added to the org.h2.api package. An arraylist "numbers" is used to store the values of a column. In the implementation of add function we are finding minimum and maximum value of a column. Each value is added to the arraylist and sum is incremented by value. The purpose of storing the value in arraylist is to get the total number of records. This will be used for calculating average. In the implementation of getResult function the average value is calculated using the sum and size of the arraylist. Each value in the arraylist is compared with the calculated average. In LessThanAverage aggregate function count will be incremented if the value is less than or equal to the calculated average. In GreaterThanAverage aggregate function count will be incremented if the value is less than or equal to the calculated average.

![image](https://user-images.githubusercontent.com/46695666/120853539-d282bd00-c549-11eb-9e51-2dbd3bd1b1bd.png)

![image](https://user-images.githubusercontent.com/46695666/120853588-e9c1aa80-c549-11eb-966d-240fa49bb80a.png)

![image](https://user-images.githubusercontent.com/46695666/120854268-e8dd4880-c54a-11eb-93bf-0ece64cb6ca3.png)


# Demo - Task 1

1. Creating a table with custom Phone Number data type
Create a table STUDENT with attributes ID INT primary key, name VARCHAR(15) and phoneNumber PHONE.

![image](https://user-images.githubusercontent.com/46695666/120867107-3f548200-c55f-11eb-915b-5e60b22d88f8.png)

2. Displaying the column information for the created table

![image](https://user-images.githubusercontent.com/46695666/120867154-598e6000-c55f-11eb-8579-d5a726293811.png)

3. Inserting new records in the user info table

![image](https://user-images.githubusercontent.com/46695666/120867174-64e18b80-c55f-11eb-85af-942a0f943b34.png)

4. Displaying records of the user info table

![image](https://user-images.githubusercontent.com/46695666/120867192-6dd25d00-c55f-11eb-9d37-eb15435cb3e5.png)

5. Some examples of invalid inputs
a. Input of length less than 10 [no separators used “ ( “ , “ ) ” , “ - ” ]

![image](https://user-images.githubusercontent.com/46695666/120867236-85114a80-c55f-11eb-860c-36202c9db7e3.png)

b. Input of length greater than 13 [separators used “ ( “ , “ ) ” , “ - ” ]

![image](https://user-images.githubusercontent.com/46695666/120867247-8a6e9500-c55f-11eb-9b82-fed403af2e18.png)

c. Also handles cases when inserting multiple records with valid and invalid records

![image](https://user-images.githubusercontent.com/46695666/120867265-9195a300-c55f-11eb-8ab3-cc628be94cfa.png)

d. Input of length 11

![image](https://user-images.githubusercontent.com/46695666/120867279-98241a80-c55f-11eb-9439-f6e873777ca7.png)

e. Input containing characters

![image](https://user-images.githubusercontent.com/46695666/120867298-9fe3bf00-c55f-11eb-874e-ae7e979d989c.png)

f. Input containing special characters except “ ( “ , “ ) ” , “ - ”

![image](https://user-images.githubusercontent.com/46695666/120867308-a6723680-c55f-11eb-8667-3c271cfbb51f.png)

g. Input containing special characters “ ( “ , “ ) ” , “ - ” at wrong places

![image](https://user-images.githubusercontent.com/46695666/120867318-ae31db00-c55f-11eb-97ae-33d4b079d404.png)

h. First digit 1

![image](https://user-images.githubusercontent.com/46695666/120867333-b4c05280-c55f-11eb-84b1-f582d0e69cd4.png)

6. Deleting entire table

![image](https://user-images.githubusercontent.com/46695666/120867359-c0137e00-c55f-11eb-945b-e23bd0281be2.png)

# Demo - Task 2

For adding the aggregate function in H2 database, we need to execute the following command in the H2 interface.
Create aggregate <function_name> for “org.h2.api.<file_name>”;

Create aggregate GT_AVG for “org.h2.api.GreaterThanAverage”;



Create aggregate LT_AVG for “org.h2.api.LessThanAverage”;


1. Creating a table employee
Create a table employee with attributes ID INT, name VARCHAR2(10) and salary INT.


2. Displaying the column information for the created table


3. Inserting new records in the user info table


4. Displaying records of the user info table



5. Displaying the aggregate function results

a. Less Than Average



b. Greater Than Average

![image](https://user-images.githubusercontent.com/46695666/120868035-1cc36880-c561-11eb-8325-4bd746dd0992.png)

