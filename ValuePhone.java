/**
 * PHONE DATA TYPE IMPLEMENTATION FILE
 **/
package org.h2.value;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;
import org.h2.engine.CustomPhoneDataTypeHandler;
import org.h2.api.ErrorCode;
import org.h2.engine.CastDataProvider;
import org.h2.engine.Mode;
import org.h2.message.DbException;
import org.h2.util.JdbcUtils;

public class ValuePhone extends Value {

    Phone phoneValue;

    /**
     * @param currentValue phone
     */
    public ValuePhone(Phone currentValue) {
        if (currentValue != null)
            this.phoneValue = currentValue;
    }

    /**
     * @param currentValue phone
     */
    public ValuePhone(String currentValue) {
        this.phoneValue =  new Phone(currentValue);
    }

    /**
     * Get method which returns the instance of given val.
     *
     * @param currentValue Phone
     * @return Phone instance
     */
    public static ValuePhone get(Phone currentValue) {
        return new ValuePhone(currentValue);
    }

    /**
     * SQL String for the phone data type.
     * @param builder String Builder
     * @return String with phone value 
     */
    @Override
    public StringBuilder getSQL(StringBuilder builder) {
        return builder.append(phoneValue.toString());
    }

    /**
     * Returns the TypeID for the phone datatype
     * @return TypeInfo Object of Phone
     */
    @Override
    public TypeInfo getType() {
         return TypeInfo.getTypeInfo(CustomPhoneDataTypeHandler.PHONE_DATA_TYPE_ID);
    }

    /**
    * Returns phone ID
    *  @return ID  of  the phone data type
    */
     @Override
    public int getValueType() {
        return CustomPhoneDataTypeHandler.PHONE_DATA_TYPE_ID;
    }

    @Override
    public String getString() {
        return phoneValue.toString();
    }

    @Override
    public Object getObject() {
        return phoneValue;
    }


    @Override
    public void set(PreparedStatement prep, int parameterIndex) throws SQLException {
       prep.setObject(parameterIndex, JdbcUtils.deserialize(getBytesNoCopy(), getDataHandler()), 
        Types.JAVA_OBJECT);
    }

    @Override
    public int compareTypeSafe(Value v, CompareMode mode, CastDataProvider c) {
        return 0;
    }

    @Override
    public int hashCode() {
        return 0;
    }


    @Override
    public boolean equals(Object other) {
        return other instanceof ValuePhone && phoneValue.equals(((ValuePhone) other).phoneValue);
    }

    public static ValuePhone get(String s) {
        return (ValuePhone) Value.cache(new ValuePhone(s));
    }

    protected Value convertTo(int targetType, Mode mode, Object column, ExtTypeInfo extTypeInfo) {
        
        if (getValueType() == targetType) {
            return this;
        }

        if (targetType==Value.BYTES)
            return ValueBytes.getNoCopy(JdbcUtils.serialize(phoneValue, null));
        else if (targetType==Value.STRING)
            return ValueString.get(phoneValue.toString());
        else if (targetType==Value.JAVA_OBJECT)
            return ValueJavaObject.getNoCopy(JdbcUtils.serialize(phoneValue, null));
        throw DbException.get(
                ErrorCode.DATA_CONVERSION_ERROR_1, getString());
    }
}
