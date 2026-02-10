//package com.luoyx.hauyne.admin.caffeine;
//
//import com.github.benmanes.caffeine.cache.Cache;
//import com.github.benmanes.caffeine.cache.Caffeine;
//import org.junit.Test;
//
//import java.time.Duration;
//
//public class CaffeineTest {
//
//    @Test
//    public void testBasicOps() {
//
//        // 构建cache对象
//        Cache<String, String> cache = Caffeine.newBuilder().build();
//
//        // 存数据
//        cache.put("gf", "赵丽颖");
//
//        String gf = cache.getIfPresent("gf");
//        System.out.println("gf = " + gf);
//
//        String defaultGf = cache.get("defaultGf", key -> {
//
//            // 根据key去数据库查询数据
//            return "柳岩";
//        });
//        System.out.println("defaultGf = " + defaultGf);
//    }
//
//    /**
//     * 基于大小设置驱逐策略
//     *
//     * @throws InterruptedException
//     */
//    @Test
//    public void testEvictByNum() throws InterruptedException {
//
//        // 创建缓存对象
//        Cache<String, String> cache = Caffeine.newBuilder()
//
//                // 设置缓存大小上限为1
//                .maximumSize(1)
//                .build();
//
//        // 存数据
//        cache.put("gf1","柳岩");
//        cache.put("gf2","范冰冰");
//        cache.put("gf3","迪丽热巴");
//
//        // 延迟10ms，给清理线程一点时间
//        Thread.sleep(10L);
//
//        // 获取数据
//        System.out.println("gf1: " + cache.getIfPresent("gf1"));
//        System.out.println("gf2: " + cache.getIfPresent("gf2"));
//        System.out.println("gf3: " + cache.getIfPresent("gf3"));
//    }
//
//    @Test
//    public void testEvictByTime() throws InterruptedException{
//
//        // 创建缓存对象
//        Cache<String, String> cache = Caffeine.newBuilder()
//
//                // 设置缓存有效期为1秒
//                .expireAfterWrite(Duration.ofSeconds(1))
//                .build();
//
//        // 存数据
//        cache.put("gf", "柳岩");
//
//        // 获取数据
//        System.out.println("gf: " + cache.getIfPresent("gf"));
//
//        // 休眠一会儿
//        Thread.sleep(1200L);
//        System.out.println("gf: " + cache.getIfPresent("gf"));
//    }
//}
