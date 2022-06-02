package com.zxf.hazelcast.springsession.cache;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.map.IMap;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.expression.MapAccessor;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Aspect
@Component
public class CachingAspect {
    @Autowired
    private HazelcastInstance hazelcastInstance;

    @Around("@annotation(com.zxf.hazelcast.springsession.cache.Caching)")
    public Object around(ProceedingJoinPoint joinPoint) {
        try {
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Caching caching = methodSignature.getMethod().getAnnotation(Caching.class);
            IMap<String, Object> cacheMap = hazelcastInstance.getMap(caching.map());
            String cacheKey = getCacheKey(caching.keyTemplate(), methodSignature.getMethod(), joinPoint.getArgs());
            if (cacheMap.containsKey(cacheKey)) {
                return cacheMap.get(cacheKey);
            }
            Object result = joinPoint.proceed();
            cacheMap.put(cacheKey, result, caching.ttl(), TimeUnit.SECONDS);
            return result;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    private String getCacheKey(String keyTemplate, Method method, Object[] args) {
        Map<String, Object> parameters = new HashMap<>();
        for (int i = 0; i < method.getParameterCount(); i++) {
            parameters.put(method.getParameters()[i].getName(), args[i]);
        }
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.addPropertyAccessor(new MapAccessor());
        context.setRootObject(parameters);
        return new SpelExpressionParser().parseExpression(keyTemplate, ParserContext.TEMPLATE_EXPRESSION)
                .getValue(context, String.class);
    }
}
