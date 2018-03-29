package com.douyu.supermap.shakingmap.common.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class FileUploadUtils {
    private static final Logger logger = LoggerFactory.getLogger(FileUploadUtils.class);

    public static boolean isLegalFmt(File file, String[] allowFormats) {
        return isLegalFmt(file.getName(), allowFormats);
    }

    public static boolean isLegalFmt(String fileName, String[] allowFormats) {
        if (ArrayUtils.isEmpty(allowFormats)) {
            return false;
        }

        String ext = StringUtils.lowerCase(FilenameUtils.getExtension(fileName));
        return ArrayUtils.contains(allowFormats, ext);
    }

    public static String md5(File file) throws IOException {
        long startTime = System.currentTimeMillis();
        try (FileInputStream in = new FileInputStream(file)) {
            String md5 = DigestUtils.md5Hex(in);
            long endTime = System.currentTimeMillis();
            logger.info("文件{}计算md5耗时{}毫秒", file, endTime - startTime);
            return md5;
        }
    }

    public static void mkDir(File dir) {
        if (!dir.exists()) {
            boolean success = dir.mkdir();
            if (!success) {
                throw new IllegalStateException("创建工作目录失败: " + dir);
            }
        }
    }

    public static boolean isXXTypeFile(File file, String typeSuffix) {
        if (file == null || !file.exists() || !file.isFile()) {//必须存在，且为文件
            return false;
        }
        String fileName = file.getName();
        String suffix = FilenameUtils.getExtension(fileName);
        return suffix.equals(typeSuffix);
    }

    public static String transformLongToFileSize(long file) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (file < 1024) {
            fileSizeString = df.format((double) file) + "B";
        } else if (file < 1048576) {
            fileSizeString = df.format((double) file / FileUtils.ONE_KB) + "Kb";
        } else if (file < 1073741824) {
            fileSizeString = df.format((double) file / FileUtils.ONE_MB) + "Mb";
        } else {
            fileSizeString = df.format((double) file / FileUtils.ONE_GB) + "Gb";
        }
        return fileSizeString;
    }

    /**
     * 递归删除目录下的所有文件及子目录下所有文件
     *
     * @param dir 将要删除的文件目录
     * @return 目录下所有的文件删除成功，则返回true
     * 一旦一个文件删除失败，立刻返回false
     */
    public static boolean deleteDirFiles(File dir) {
        if (null == dir) {
            return false;
        }
        if (dir.isDirectory()) {
            String[] children = dir.list();
            //递归删除目录中的子目录下
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDirFiles(new File(dir, children[i]));
                    if (!success) {
                        logger.info("文件{}删除失败！", dir + children[i]);
                        return false;
                    }
                }
            }
        }
        // 目录此时为空，可以删除(递归如果是文件，也直接删除)
        return dir.delete();
    }

    public static List<File> listDirFiles(File directory) {
        List<File> res = new ArrayList<>();
        if (!directory.isDirectory()) {//不是文件夹，直接返回自身
            res.add(directory);
            return res;
        }
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                collectFile(file, res);
            }
        }
        return res;
    }
    private static void collectFile(File file, List<File> fileList) {
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File thisFile : files) {
                    collectFile(thisFile, fileList);
                }
            }
        } else {
            fileList.add(file);
        }
    }
}
