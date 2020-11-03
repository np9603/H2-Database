package org.h2.api;
import java.util.*;

/**
 *  AggregateFunction implementing class
 */
public class LessThanAverage implements AggregateFunction {

    private int count = 0; //Stores number of rows

    private Integer result; //Stores  the final result

    private Integer sum; //Stores sum of values

    private Object max, min; //Stores Max and Min value of column

    private ArrayList<Integer> numbers = new ArrayList<>(); //Stores the numbers to perform average

   /**
     * This method is called when the aggregate function is used.
     * A new object is created for each invocation.
     *
     * @param conn a connection to the database
     */
    @Override
    public void init(java.sql.Connection cnctn) throws java.sql.SQLException {
    }

    /**
     * This method must return the SQL type of the method, given the SQL type of
     * the input data. The method should check here if the number of parameters
     * passed is correct, and if not it should throw an exception.
     *
     * @param inputTypes the SQL type of the parameters, {@link java.sql.Types}
     * @return the SQL type of the result
     */
    @Override
    public int getType(int[] ints) throws java.sql.SQLException {
        return ints[0];
    }

    /**
     * This method is called once for each row.
     * If the aggregate function is called with multiple parameters,
     * those are passed as array.
     *
     * @param value the value(s) for this row
     */
    @Override
    public void add(Object o) throws java.sql.SQLException {
        Object value = o;

        if (count == 0) {
            //Initialize all the variable on processing the first row
            sum = (Integer)value; 
            max = value;
            min = value;
            numbers.add((Integer)value);
        }
        else
        {
            
            if ((Integer)value < (Integer)min) {
                min = value; //Stores the min value
            } else if ((Integer)value > (Integer)max) {
                max = value; //Stores the max value
            }
            sum += (Integer) value;  //Add  to the  sum
            numbers.add((Integer)value);  //Add number to the list
        }
        count++;
    }

    /**
     * This method returns the computed aggregate value. This method must
     * preserve previously added values and must be able to reevaluate result if
     * more values were added since its previous invocation.
     *
     * @return the aggregated value
     */
    @Override
    public Object getResult() throws java.sql.SQLException {
        
        Integer avg = sum/numbers.size(); //Average  of values

        int count = 0; //Number of values less than and equal to  average

        for(int i = 0;i < numbers.size();i++){
            if(numbers.get(i) <= avg)
                count++;
        }
        return count;
    }

}
