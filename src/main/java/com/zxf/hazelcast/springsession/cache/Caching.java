package com.zxf.hazelcast.springsession.cache;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Caching {
    String map() default "default-cache";

    String keyTemplate();

    long ttl();
}
