//package com.luoyx.hauyne.admin.test;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.junit.Assert;
//import org.junit.Test;
//
//import java.util.concurrent.TimeUnit;
//
//public class UserContext {
//
//    public UserContext() {
//
//    }
//
//    private static InheritableThreadLocal<User> userHolder = new InheritableThreadLocal<>();
//
//    public static User getUser() {
//        return userHolder.get();
//    }
//
//    public static void setUser(User user) {
//        userHolder.set(user);
//    }
//
//    public static void clean() {
//        userHolder.remove();
//    }
//
//    @Test
//    public void test3() throws InterruptedException {
//        User user = new User();
//        user.setUsername("mzt");
//        UserContext.setUser(user);
//
//        User user1 = UserContext.getUser();
//
//        Assert.assertNotNull(user1);
//        Assert.assertEquals(user1.getUsername(), "mzt");
//        System.out.println("当前线程：" + Thread.currentThread().getName() + "，用户名：" + user1.getUsername());
//
//        new Thread(() -> {
//            final User user2 = UserContext.getUser();
//            Assert.assertNotNull(user2);
//            Assert.assertEquals(user2.getUsername(), "mzt");
//            System.out.println("当前线程：" + Thread.currentThread().getName() + "，用户名：" + user2.getUsername());
//        }).start();
//
////        TimeUnit.MINUTES.sleep(1);
//    }
//
//
//}
