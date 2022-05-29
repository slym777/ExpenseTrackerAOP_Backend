package com.example.expensetracker.aspect;

import com.example.expensetracker.annotations.FlushCache;
import com.example.expensetracker.annotations.UseCache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.ehcache.core.Ehcache;
import org.ehcache.core.EhcacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

@Aspect
@Component
public class CacheAspect {
    private static Logger logger = Logger.getLogger(LoggingAspect.class.getName());
    @Autowired
    private Ehcache ehcache;

//    @Autowired
//    public CacheAspect(Ehcache ehcache) {
//        this.ehcache = ehcache;
//    }

    @Around("@annotation(useCache)")
    public Object cache(ProceedingJoinPoint pjp, UseCache useCache) throws Throwable {
        var cacheKey = new CacheKey(pjp);

        if (ehcache.containsKey(cacheKey)) {
            logger.log(Level.INFO, "[CACHE] HIT! Getting value for key '" + cacheKey.hashCode() + "' from cache.");
            return ehcache.get(cacheKey);
        }

        Object returnedValue = pjp.proceed();
        if (returnedValue != null) {
            logger.log(Level.INFO, "[CACHE] MISSED! Saving value for key '" + cacheKey.hashCode() + "' in cache.");
            ehcache.put(cacheKey, returnedValue);
        }

        return returnedValue;
    }

    @Around("@annotation(flushCache)")
    public Object flush(ProceedingJoinPoint pjp, FlushCache flushCache) throws Throwable {
        var cacheKey = new CacheKey(pjp);

        if (ehcache.containsKey(cacheKey)) {
            logger.log(Level.INFO,"[CACHE] Removing value for key '" + cacheKey.hashCode() + "' from cache.");
            ehcache.remove(cacheKey);
        }
        return pjp.proceed();
    }

    private static class CacheKey {
        private final String signature;
        private final Object[] args;

        public CacheKey(ProceedingJoinPoint pjp) {
            signature = pjp.getSignature().toString();
            args = pjp.getArgs();
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CacheKey cacheKey = (CacheKey) o;
            return Objects.equals(signature, cacheKey.signature) && Arrays.equals(args, cacheKey.args);
        }

        @Override
        public int hashCode() {
            int result = Objects.hash(signature);
            result = 31 * result + Arrays.hashCode(args);
            return result;
        }
    }
}
