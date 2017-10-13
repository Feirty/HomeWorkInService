
package com.hzu.feirty.utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import com.hzu.feirty.dao.StudentDaoImpl;
import com.hzu.feirty.entity.Student;

/**

 */
public class IOUtil {
    private static final String PWD="teacher_pwd";

    /**

     */
    public String stream2file(InputStream source, String targetPath) {
        File target = new File(targetPath);
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            if (!target.exists()) {
                String dir = targetPath.substring(0, targetPath.lastIndexOf("/"));
                new File(dir).mkdirs();
                try {
                    target.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            inBuff = new BufferedInputStream(source);
            outBuff = new BufferedOutputStream(new FileOutputStream(target));
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            outBuff.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inBuff != null) {
                    inBuff.close();
                }
                if (outBuff != null) {
                    outBuff.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (target.length() > 0) {
            return target.getAbsolutePath();
        } else {
            target.delete();
            return null;
        }
    }

    /**

     */
    public InputStream Byte2InputStream(byte[] data) {
        ByteArrayInputStream bais = new ByteArrayInputStream(data);
        return bais;
    }

    /**

     */
    public byte[] InputStream2Bytes(InputStream is) {
        String str = "";
        byte[] readByte = new byte[1024];
        try {
            while (is.read(readByte, 0, 1024) != -1) {
                str += new String(readByte).trim();
            }
            return str.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] getFileBytes(File file) throws IOException {
        BufferedInputStream bis = null;
        try {
            bis = new BufferedInputStream(new FileInputStream(file));
            int bytes = (int) file.length();
            byte[] buffer = new byte[bytes];
            int readBytes = bis.read(buffer);
            if (readBytes != buffer.length) {
                throw new IOException("Entire file not read");
            }
            return buffer;
        } finally {
            if (bis != null) {
                bis.close();
            }
        }
    }
    public static int Txt(String path,String course,String teachername) throws SQLException {
        try {
            String str = "";
            String str1 = "";
            int numbers=0;
            FileInputStream fis = new FileInputStream(path);// FileInputStream
            // 从文件系统中的某个文件中获取字节
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while ((str = br.readLine()) != null) {
                str1 = str;
                Student student = new Student();
                student.setNumber(str1);
                student.setCourse(course);
                student.setTeacher(teachername);
                new StudentDaoImpl().Save(student);
                numbers++;
                System.out.println(str+"添加成功");
            }
            fis.close();
            isr.close();
            br.close();
            return numbers;
        } catch (FileNotFoundException e) {
        	return 0;
        } catch (IOException e) {
        	return 0;
        }
    }
    
    public static void Txt2(String path) {
        try {
            String str = "";
            String str1 = "";
            FileInputStream fis = new FileInputStream(path);// FileInputStream
            // 从文件系统中的某个文件中获取字节
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            while ((str = br.readLine()) != null) {
                str1 = str;
             //   PreferencesUtil.setSharedStringData(MailApplication.getmContext(),PWD,str1);
            }
            fis.close();
            isr.close();
            br.close();

        } catch (FileNotFoundException e) {

        } catch (IOException e) {
        }
    }
}
