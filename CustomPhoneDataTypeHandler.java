// Custom Phone Handler Implementation

package org.h2.engine;
import java.security.NoSuchAlgorithmException;
import java.sql.Types;
import java.util.Locale;
import org.h2.message.DbException;
import org.h2.store.DataHandler;
import org.h2.util.JdbcUtils;
import org.h2.value.*;
import org.h2.api.*;

public class CustomPhoneDataTypeHandler implements CustomDataTypesHandler {

    public final static String PHONE_DATA_TYPE_NAME = "phone";
    public final static int PHONE_DATA_TYPE_ID = Value.PHONE;
    public final static int PHONE_DATA_TYPE_ORDER = 100_000;
    public final DataType phoneDataType;

    /** 
        Constructor 
    */

    public CustomPhoneDataTypeHandler() {
        DataType dt = new DataType();
        dt.type = PHONE_DATA_TYPE_ID;
        dt.name = PHONE_DATA_TYPE_NAME;
        dt.sqlType = Types.JAVA_OBJECT;
        phoneDataType = dt;
    }

    /**
     * Retrieving phone data type basis of name.
     *
     * @param name datatype name
     * @return custom data type
     */

    @Override
    public DataType getDataTypeByName(String name) {
        if (name.toLowerCase(Locale.ENGLISH).equals(PHONE_DATA_TYPE_NAME)) {
            return phoneDataType;
        }
        return null;
    }

    /**
     * Retrieving phone data type based on integer id
     *
     * @param type data type identifier
     * @return custom data type
     */

    @Override
    public DataType getDataTypeById(int type) {
        if (type == PHONE_DATA_TYPE_ID) {
            return phoneDataType;
        }
        return null;
    }

    /**
     * Get type info for the given data type identity.
     *
     * @param type identifier of a data type
     * @param precision precision
     * @param scale scale
     * @param extTypeInfo the extended type information, or null
     * @return type information
     */
    @Override
    public TypeInfo getTypeInfoById(int type, long precision, int scale, ExtTypeInfo extTypeInfo) {
         return new TypeInfo(type, 0, 0, ValueDouble.DISPLAY_SIZE * 2 + 1, null);
    }

    /**
     * Get custom data type class name given its integer id
     *
     * @param type identifier of a data type
     * @return class name
     */
    @Override
    public String getDataTypeClassName(int type) {
        if (type == PHONE_DATA_TYPE_ID) {
            return Phone.class.getName();
        }
        throw DbException.get(
                ErrorCode.UNKNOWN_DATA_TYPE_1, "type:" + type);
    }

    /**
     * Get custom data type identifier given corresponding Java class
     * @param cls Java class object
     * @return type identifier
     */
    @Override
    public int getTypeIdFromClass(Class<?> cls) {
        if (cls == Phone.class) {
            return PHONE_DATA_TYPE_ID;
        }
        return Value.JAVA_OBJECT;
    }
    
     @Override
    public Value convert(Value source, int targetType) {
        if (source.getValueType() == targetType) {
            return source;
        }
        if (targetType == PHONE_DATA_TYPE_ID) {
            switch (source.getValueType()) {
                case Value.JAVA_OBJECT: {
                    assert source instanceof ValueJavaObject;
                    return ValuePhone.get((Phone)
                            JdbcUtils.deserialize(source.getBytesNoCopy(), null));
                }
                case Value.STRING: {
                    assert source instanceof  ValueString;
                    return ValuePhone.get(Phone.validatePhoneNumber(source.getString()));
                }
            }

            throw DbException.get(
                    ErrorCode.DATA_CONVERSION_ERROR_1, source.getString());
        } else {
            return source.convertTo(targetType);
        }
    }
    


    /**
     * Retreive order for phone datatype based on its integer id
     *
     * @param type identifier of a data type
     * @return order associated with custom data type
     */

    @Override
    public int getDataTypeOrder(int type) {
        if (type == PHONE_DATA_TYPE_ID) {
            return PHONE_DATA_TYPE_ORDER;
        }
        throw DbException.get(
                ErrorCode.UNKNOWN_DATA_TYPE_1, "type:" + type);
    }

    /**
     * Get {@link org.h2.value.Value} object
     * corresponding to given data type identifier and data.
     *
     * @param type custom data type identifier
     * @param data underlying data type value
     * @param dataHandler data handler object
     * @return Value object
     */

    @Override
    public Value getValue(int type, Object data, DataHandler dataHandler) {
        if (type == PHONE_DATA_TYPE_ID) {
            assert data instanceof Phone;
            return ValuePhone.get((Phone)data);
        }
        return ValueJavaObject.getNoCopy(data, null, dataHandler);
    }

    /**
     * Converts {@link org.h2.value.Value} object
     * to the specified class.
     *
     * @param value the value to convert
     * @param cls the target class
     * @return result
     */

    @Override
    public Object getObject(Value value, Class<?> cls) {
        if (cls.equals(Phone.class)) {
            if (value.getValueType() == PHONE_DATA_TYPE_ID) {
                return value.getObject();
            }
            return convert(value, PHONE_DATA_TYPE_ID).getObject();
        }
        throw DbException.get(
                ErrorCode.UNKNOWN_DATA_TYPE_1, "type:" + value.getValueType());
    }

    /**
     * Checks if type supports add operation
     *
     * @param type custom data type identifier
     * @return True, if custom data type supports add operation
     */

    @Override
    public boolean supportsAdd(int type) {
        return false;
    }

    /**
     * Get compatible type identifier that would not overflow
     * after many add operations.
     *
     * @param type identifier of a type
     * @return resulting type identifier
     */

    @Override
    public int getAddProofType(int type) {
        if (type == PHONE_DATA_TYPE_ID) {
            return type;
        }
        throw DbException.get(
                ErrorCode.UNKNOWN_DATA_TYPE_1, "type:" + type);
    }
}
