package jorotests.ProgressTests;

import javax.swing.*;
import java.io.*;

/**
 * Created by gogo on 16.1.2018 г..
 */
public class CopyApp {
static boolean flag = false;
static String currentFileName = null;
    static JFrame frame;
    static ProgressBar progress = new ProgressBar();
    public static void main(String[] args) throws IOException {

        progress.setVisible(true);

        File folder1 = new File("D:\\trash\\folder1");

         frame = new JFrame("newFrame");

        File[] listOfFiles = folder1.listFiles();
        Thread loadingThread =  new Thread() {
            @Override
            public void run() {
                System.out.print("Coping");
                while (true) {
                    for (int i = 0; i < 3; i++) {
                        System.out.print(".");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    System.out.print("\b\b\b");
                    if (flag) {
                        return;
                    }
                }
            }
        };
loadingThread.start();

        for (int i = 0; i<listOfFiles.length; i++){
            progress.setPercentage(i);
            File oldFile = listOfFiles[i];
            File newFile = new File(String.format("D:\\trash\\folder2\\%s",i));
            progress.setInfo(String.format("D:\\trash\\folder2\\%s",i));
            copyFileUsingStream(oldFile,newFile);
            System.out.println(newFile.getName());

        }
        flag = true;

    }
    private static void copyFileUsingStream(File source, File dest) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(source);

            os = new FileOutputStream(dest);


            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
}
