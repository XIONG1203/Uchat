package com.example.xiong.uchat.Util;

import android.os.Environment;

import java.io.File;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by lzw on 2015/3/27.
 */
public class FileUtil {

    private static String cacheDirName = "/Android/data/org.dreamfly.health_heart";


    private static File cacheDir;

    public static String getCacheDirName() {
        return cacheDir.getAbsolutePath() + "/";
    }

    //图片缓存路径
    public static File imgCache;


    /**
     * 打开一个缓存文件的静态方法,
     *
     * @param fileName 文件的名称，包括后缀,是否存在由由调用类判定
     * @return
     */

    public static File openCacheFile(String fileName) {
        File openfile = null;

        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File sdDir = Environment.getExternalStorageDirectory();
            cacheDir = new File(sdDir, cacheDirName);
            if (!cacheDir.exists()) {
                cacheDir.mkdirs();
            }
            openfile = new File(cacheDir, fileName);
            return (openfile);
        } else {
            return (null);
        }
    }

    /**
     * 清空图片缓存
     */
    public static void cleanImgCache() {
        Queue<File> queue = new LinkedList<>();
        queue.offer(imgCache);
        File file;
        while (!queue.isEmpty()) {
            file = queue.poll();
            if (file.isFile() || file.listFiles().length == 0) {
                file.delete();
            } else {
                for (File inFile : file.listFiles()) {
                    queue.offer(inFile);
                }
                queue.offer(file);
            }
        }
    }

    //静态代码块，加载类的时候会被执行，初始化我们的imgCache文件夹
    static {
        imgCache = openCacheFile("imgCache");
        if (!imgCache.exists()) {
            imgCache.mkdirs();
        }
    }

}
