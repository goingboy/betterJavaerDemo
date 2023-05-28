package top.codingmore;

import cn.hutool.cache.Cache;
import cn.hutool.cache.CacheUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.Month;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.map.BiMap;
import cn.hutool.core.util.IdcardUtil;
import cn.hutool.core.util.ZipUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.AES;
import cn.hutool.extra.qrcode.QrCodeUtil;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.util.HashMap;

public class TestHutool {

    public static void main(String[] args) {
        //1.星座
        String zodiac = DateUtil.getZodiac(Month.JUNE.getValue(), 20);
        System.out.println(zodiac);
        //属相
        String chineseZodiac = DateUtil.getChineseZodiac(1994);
        System.out.println(chineseZodiac);

        //2.文件操作
        BufferedInputStream in = FileUtil.getInputStream("D:\\JetBrains 2023.x永久激活插件\\jetbra\\激活码.txt");
        BufferedOutputStream out = FileUtil.getOutputStream("E:\\test\\myTestFiles");
        long copySize = IoUtil.copy(in, out, IoUtil.DEFAULT_BUFFER_SIZE);
        System.out.println(copySize);

        //3.hutool 压缩、解压缩
        ZipUtil.zip("testHutool", "hutool.zip");
        File unzip = ZipUtil.unzip("hutool.zip", "hutoolzip");

        //4.身份证校验
        String ID_18 = "321083197812162119";
        String ID_15 = "150102880730303";
        boolean valid = IdcardUtil.isValidCard(ID_18);
        boolean valid15 = IdcardUtil.isValidCard(ID_15);
        System.out.println(valid +  ", " + valid15);

        //5.扩展hashMap
        Dict dict = Dict.create()
                .set("age", 18)
                .set("name", "沉默王二")
                .set("birthday", DateTime.now());

        int age = dict.getInt("age");
        String name = dict.getStr("name");

        // 6.打印字符串
        Console.log("沉默王二，一枚有趣的程序员");
        // 打印字符串模板
        Console.log("洛阳是{}朝古都",13);
        int [] ints = {1,2,3,4};
        // 打印数组
        Console.log(ints);//等价于 System.out.println(Arrays.toString(ints));

        //7.验证邮箱、电话号码、ipv4\ipv6
        Validator.isEmail("沉默王二");
        Validator.isMobile("itwanger.com");

        //8.双向查找 Map
        BiMap<String, String> biMap = new BiMap<>(new HashMap<>());
        biMap.put("wanger", "沉默王二");
        biMap.put("wangsan", "沉默王三");
        // get value by key
        biMap.get("wanger");
        biMap.get("wangsan");
        // get key by value
        biMap.getKey("沉默王二");
        biMap.getKey("沉默王三");

        //9.ImgUtil 可以对图片进行缩放、裁剪、转为黑白、加水印等操作。
        //缩放图片：
        ImgUtil.scale(
                FileUtil.file("testHutool/wangsan.jpg"),
                FileUtil.file("testHutool/wangsan_small.jpg"),
                0.5f
        );
        //裁剪图片：
        ImgUtil.cut(
                FileUtil.file("testHutool/wangsan.jpg"),
                FileUtil.file("testHutool/wangsan_cut.jpg"),
                new Rectangle(500, 500, 2500, 2500)
        );
        //添加水印：
        ImgUtil.pressText(//
                FileUtil.file("testHutool/wangsan.jpg"),
                FileUtil.file("testHutool/wangsan_logo.jpg"),
                "沉默王二", Color.blue,
                new Font("黑体", Font.BOLD, 200),
                0,
                0,
                0.8f
        );
        //黑白
        ImgUtil.binary(
                FileUtil.file("testHutool/wangsan.jpg"),
                FileUtil.file("testHutool/wangsan_binary.jpg")
        );



        //10.缓存工具
        //FIFOCache：先入先出，元素不停的加入缓存直到缓存满为止，当缓存满时，清理过期缓存对象，清理后依旧满则删除先入的缓存。
        Cache<String, String> fifoCache = CacheUtil.newFIFOCache(3);
        fifoCache.put("key1", "沉默王一");
        fifoCache.put("key2", "沉默王二");
        fifoCache.put("key3", "沉默王三");
        fifoCache.put("key4", "沉默王四");
        // 大小为 3，所以 key3 放入后 key1 被清除
        String value1 = fifoCache.get("key1");

        //LFUCache，最少使用，根据使用次数来判定对象是否被持续缓存，当缓存满时清理过期对象，清理后依旧满的情况下清除最少访问的对象并将其他对象的访问数减去这个最少访问数，以便新对象进入后可以公平计数。
        Cache<String, String> lfuCache = CacheUtil.newLFUCache(3);
        lfuCache.put("key1", "沉默王一");
        // 使用次数+1
        lfuCache.get("key1");
        lfuCache.put("key2", "沉默王二");
        lfuCache.put("key3", "沉默王三");
        lfuCache.put("key4", "沉默王四");
        // 由于缓存容量只有 3，当加入第 4 个元素的时候，最少使用的将被移除（2,3被移除）
        String value2 = lfuCache.get("key2");
        String value3 = lfuCache.get("key3");

        //LRUCache，最近最久未使用，根据使用时间来判定对象是否被持续缓存，当对象被访问时放入缓存，当缓存满了，最久未被使用的对象将被移除。
        Cache<String, String> lruCache = CacheUtil.newLRUCache(3);

        lruCache.put("key1", "沉默王一");
        lruCache.put("key2", "沉默王二");
        lruCache.put("key3", "沉默王三");
        // 使用时间近了
        lruCache.get("key1");
        lruCache.put("key4", "沉默王四");

        // 由于缓存容量只有 3，当加入第 4 个元素的时候，最久使用的将被移除（2）
        String value_2 = lruCache.get("key2");
        System.out.println(value_2);


        //11.加解密
        AES aes = SecureUtil.aes();
        String encry = aes.encryptHex("沉默王二", "utf8");
        System.out.println(encry);
        String oo = aes.decryptStr(encry);
        System.out.println(oo);

        //二维码工具
        BufferedImage image = QrCodeUtil.generate("www.baidu.com", 500, 500);
        ImgUtil.write(image,"jpg", ImgUtil.getImageOutputStream(FileUtil.file("hutoolImg.jpg")));
    }
}
