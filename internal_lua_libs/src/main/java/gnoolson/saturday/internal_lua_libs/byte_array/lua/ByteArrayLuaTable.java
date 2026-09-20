package gnoolson.saturday.internal_lua_libs.byte_array.lua;

import gnoolson.saturday.internal_lua_libs.byte_array.Id;
import gnoolson.saturday.internal_lua_libs.byte_array.lua.function.*;
import gnoolson.saturday.lua_script_executor.lib.InternalServiceLuaTable;
import lombok.Getter;
import org.luaj.vm2.LuaTable;

import static java.nio.ByteOrder.BIG_ENDIAN;
import static java.nio.ByteOrder.LITTLE_ENDIAN;

public class ByteArrayLuaTable extends LuaTable implements InternalServiceLuaTable {

    private static final GetInt8Function GET_INT_8_FUNCTION = new GetInt8Function(); // +
    private static final GetUint8Function GET_UINT_8_FUNCTION = new GetUint8Function();  // +
    private static final GetInt16Function GET_INT_16_BE_FUNCTION = new GetInt16Function(BIG_ENDIAN); // +
    private static final GetInt16Function GET_INT_16_LE_FUNCTION = new GetInt16Function(LITTLE_ENDIAN); // +
    private static final GetUint16Function GET_UINT_16_BE_FUNCTION = new GetUint16Function(BIG_ENDIAN); // +
    private static final GetUint16Function GET_UINT_16_LE_FUNCTION = new GetUint16Function(LITTLE_ENDIAN); // +
    private static final GetInt32Function GET_INT_32_BE_FUNCTION = new GetInt32Function(BIG_ENDIAN); // +
    private static final GetInt32Function GET_INT_32_LE_FUNCTION = new GetInt32Function(LITTLE_ENDIAN); // +
    private static final GetUint32Function GET_UINT_32_BE_FUNCTION = new GetUint32Function(BIG_ENDIAN); // +
    private static final GetUint32Function GET_UINT_32_LE_FUNCTION = new GetUint32Function(LITTLE_ENDIAN); // +
    private static final GetInt64Function GET_INT_64_BE_FUNCTION = new GetInt64Function(BIG_ENDIAN); // +
    private static final GetInt64Function GET_INT_64_LE_FUNCTION = new GetInt64Function(LITTLE_ENDIAN); // +
    private static final GetFloatFunction GET_FLOAT_BE_FUNCTION = new GetFloatFunction(BIG_ENDIAN); // +
    private static final GetFloatFunction GET_FLOAT_LE_FUNCTION = new GetFloatFunction(LITTLE_ENDIAN); // +
    private static final GetDoubleFunction GET_DOUBLE_BE_FUNCTION = new GetDoubleFunction(BIG_ENDIAN); // +
    private static final GetDoubleFunction GET_DOUBLE_LE_FUNCTION = new GetDoubleFunction(LITTLE_ENDIAN); // +
    private static final GetUint8SequenceFunction GET_UINT_8_SEQUENCE_FUNCTION = new GetUint8SequenceFunction(); // +++
    private static final GetInt8SequenceFunction GET_INT_8_SEQUENCE_FUNCTION = new GetInt8SequenceFunction(); // +++
    private static final GetStringFunction GET_STRING_FUNCTION = new GetStringFunction(false); // +
    private static final GetStringFunction GET_SAFE_STRING_FUNCTION = new GetStringFunction(true); // +
    private static final SetInt8Function SET_INT_8_FUNCTION = new SetInt8Function(); // +
    private static final SetUint8Function SET_UINT_8_FUNCTION = new SetUint8Function(); // +
    private static final SetInt16Function SET_INT_16_BE_FUNCTION = new SetInt16Function(BIG_ENDIAN); // +
    private static final SetInt16Function SET_INT_16_LE_FUNCTION = new SetInt16Function(LITTLE_ENDIAN); // +
    private static final SetUint16Function SET_UINT_16_BE_FUNCTION = new SetUint16Function(BIG_ENDIAN); // +
    private static final SetUint16Function SET_UINT_16_LE_FUNCTION = new SetUint16Function(LITTLE_ENDIAN); // +
    private static final SetInt32Function SET_INT_32_BE_FUNCTION = new SetInt32Function(BIG_ENDIAN); // +
    private static final SetInt32Function SET_INT_32_LE_FUNCTION = new SetInt32Function(LITTLE_ENDIAN); // +
    private static final SetUint32Function SET_UINT_32_BE_FUNCTION = new SetUint32Function(BIG_ENDIAN); // +
    private static final SetUint32Function SET_UINT_32_LE_FUNCTION = new SetUint32Function(LITTLE_ENDIAN); // +
    private static final SetInt64Function SET_INT_64_BE_FUNCTION = new SetInt64Function(BIG_ENDIAN); // +
    private static final SetInt64Function SET_INT_64_LE_FUNCTION = new SetInt64Function(LITTLE_ENDIAN); // +
    private static final SetFloatFunction SET_FLOAT_BE_FUNCTION = new SetFloatFunction(BIG_ENDIAN); // +
    private static final SetFloatFunction SET_FLOAT_LE_FUNCTION = new SetFloatFunction(LITTLE_ENDIAN); // +
    private static final SetDoubleFunction SET_DOUBLE_BE_FUNCTION = new SetDoubleFunction(BIG_ENDIAN); // +
    private static final SetDoubleFunction SET_DOUBLE_LE_FUNCTION = new SetDoubleFunction(LITTLE_ENDIAN); // +
    private static final SetStringFunction SET_STRING_FUNCTION = new SetStringFunction(); // + 1
    private static final SetUint8SequenceFunction SET_UINT_8_SEQUENCE_FUNCTION = new SetUint8SequenceFunction(); // +
    private static final SetInt8SequenceFunction SET_INT_8_SEQUENCE_FUNCTION = new SetInt8SequenceFunction(); // +
    private static final SplitFunction SPLIT_FUNCTION = new SplitFunction(); // +
    private static final LengthFunction LENGTH_FUNCTION = new LengthFunction(); // +
    private static final PayloadLengthFunction PAYLOAD_LENGTH_FUNCTION = new PayloadLengthFunction(); // +
    private static final AllocateFunction ALLOCATE_FUNCTION = new AllocateFunction();
    private static final PutFunction PUT_FUNCTION = new PutFunction(); // +
    private static final FromStringFunction FROM_STRING_FUNCTION = new FromStringFunction();
    private static final FromBytesFunction FROM_J_BYTES_FUNCTION = new FromBytesFunction();
    private static final FromHexStringFunction FROM_HEX_STRING_FUNCTION = new FromHexStringFunction();
    private static final FromBase64StringFunction FROM_BASE64_STRING_FUNCTION = new FromBase64StringFunction();
    private static final ToHexStringFunction TO_HEX_STRING_FUNCTION = new ToHexStringFunction();
    private static final TotBase64StringFunction TO_BASE64_STRING_FUNCTION = new TotBase64StringFunction();
    private static final GetBytesFunction GET_J_BYTES_FUNCTION = new GetBytesFunction();
    private static final MergeFunction MERGE_FUNCTION = new MergeFunction();
    private static final CopyFunction COPY_FUNCTION = new CopyFunction();

    @Getter
    private final byte[] bytes;

    public ByteArrayLuaTable(byte[] bytes) {
        this.bytes = bytes;

        {
            set("getInt8", GET_INT_8_FUNCTION);
            set("getUint8", GET_UINT_8_FUNCTION);

            set("getInt16Be", GET_INT_16_BE_FUNCTION);
            set("getInt16Le", GET_INT_16_LE_FUNCTION);

            set("getUint16Be", GET_UINT_16_BE_FUNCTION);
            set("getUint16Be", GET_UINT_16_LE_FUNCTION);

            set("getInt32Be", GET_INT_32_BE_FUNCTION);
            set("getInt32Le", GET_INT_32_LE_FUNCTION);

            set("getUint32Be", GET_UINT_32_BE_FUNCTION);
            set("getUint32Le", GET_UINT_32_LE_FUNCTION);

            set("getInt64Be", GET_INT_64_BE_FUNCTION);
            set("getInt64Le", GET_INT_64_LE_FUNCTION);

            set("getFloatBe", GET_FLOAT_BE_FUNCTION);
            set("getFloatLe", GET_FLOAT_LE_FUNCTION);

            set("getDoubleBe", GET_DOUBLE_BE_FUNCTION);
            set("getDoubleLe", GET_DOUBLE_LE_FUNCTION);

            set("getUint8Sequence", GET_UINT_8_SEQUENCE_FUNCTION);
            set("getInt8Sequence", GET_INT_8_SEQUENCE_FUNCTION);

            set("getString", GET_STRING_FUNCTION);
            set("getSafeString", GET_SAFE_STRING_FUNCTION);
        }

        {
            set("allocate", ALLOCATE_FUNCTION);
            set("fromString", FROM_STRING_FUNCTION);
            set("fromHexString", FROM_HEX_STRING_FUNCTION);
            set("fromBase64String", FROM_BASE64_STRING_FUNCTION);
            set("fromJBytes", FROM_J_BYTES_FUNCTION);
        }

        {
            set("merge", MERGE_FUNCTION);
            set("toHexString", TO_HEX_STRING_FUNCTION);
            set("toBase64String", TO_BASE64_STRING_FUNCTION);
            set("getJBytes", GET_J_BYTES_FUNCTION);
            set("put", PUT_FUNCTION);
            set("copy", COPY_FUNCTION);
            set("split", SPLIT_FUNCTION);
            set("length", LENGTH_FUNCTION);
            set("payloadLength", PAYLOAD_LENGTH_FUNCTION);
        }

        {
            set("setInt8", SET_INT_8_FUNCTION);
            set("setUint8", SET_UINT_8_FUNCTION);

            set("setInt16Be", SET_INT_16_BE_FUNCTION);
            set("setInt16Le", SET_INT_16_LE_FUNCTION);

            set("setUint16Be", SET_UINT_16_BE_FUNCTION);
            set("setUint16Le", SET_UINT_16_LE_FUNCTION);

            set("setInt32Be", SET_INT_32_BE_FUNCTION);
            set("setInt32Le", SET_INT_32_LE_FUNCTION);

            set("setUint32Be", SET_UINT_32_BE_FUNCTION);
            set("setUint32Le", SET_UINT_32_LE_FUNCTION);

            set("setInt64Be", SET_INT_64_BE_FUNCTION);
            set("setInt64Le", SET_INT_64_LE_FUNCTION);

            set("setFloatBe", SET_FLOAT_BE_FUNCTION);
            set("setFloatLe", SET_FLOAT_LE_FUNCTION);

            set("setDoubleBe", SET_DOUBLE_BE_FUNCTION);
            set("setDoubleLe", SET_DOUBLE_LE_FUNCTION);

            set("setUint8Sequence", SET_UINT_8_SEQUENCE_FUNCTION);
            set("setInt8Sequence", SET_INT_8_SEQUENCE_FUNCTION);

            set("setString", SET_STRING_FUNCTION);
        }

    }

    @Override
    public LuaTable getInstance() {
        return this;
    }

    @Override
    public String name() {
        return "ByteArray";
    }

    @Override
    public String getId() {
        return Id.VALUE;
    }

    @Override
    public void updateFunctionality(Object functionality) {

    }

    @Override
    public void release() {

    }


}
