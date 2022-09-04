package ysomap.core.serializer.shiro;

import ysomap.core.serializer.BaseSerializer;

public class ShiroSerializer extends BaseSerializer<String> {

    public String OUTPUT = "console";

    @Override
    public String serialize(Object obj) throws Exception {
        return (String) obj;
    }

    @Override
    public Object deserialize(String obj) throws Exception {
        //TODO
        return null;
    }

    @Override
    public String getOutputType() {
        return OUTPUT;
    }

    @Override
    public void setOutputType(String output) {
        OUTPUT = output;
    }
}
