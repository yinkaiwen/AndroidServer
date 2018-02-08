package com.example.kevin.androidserver.otherutils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by kevin on 2018/2/8.
 * https://github.com/yinkaiwen
 */

public class StringFactory {
    public static String getString(InputStream inputStream) {
        if (inputStream == null) {
            return "";
        }

        String str = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        int len = -1;
        byte[] b = new byte[1024 * 4];
        try {
            while ((len = inputStream.read(b)) != -1) {
                bos.write(b, 0, len);
            }
            str = bos.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
                bos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return str;
    }

    public static String getString(byte[] bytes) {
        if (bytes == null) {
            return "";
        } else {
            return bytes.toString();
        }
    }
}
