package com.example.msikeyvault.test;

import java.io.File;
import java.io.IOException;
import java.net.URL;

public class toGetPath {

    public static void main(String[] args) {
        toGetPath fileTest1 = new toGetPath();
        try {
            fileTest1.getUrl();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void getUrl() throws IOException {
        String path = "";

        //第一种：获取类加载的根路径
        path = this.getClass().getResource("/").getPath();
        System.out.println(path);

        //获取当前类的所在工程路径,不加“/”  获取当前类的加载目录
        path = this.getClass().getResource("").getPath();
        System.out.println(path);

        // 第二种：获取项目路径
        File file = new File("");
        path = file.getCanonicalPath();
        System.out.println(path);

        // 第三种：
        URL path1 = this.getClass().getResource("");
        System.out.println(path1);

        // 第四种：!!!!!!!!!!!
        path = System.getProperty("user.dir");
        System.out.println(path);


        // 第五种：  获取项目路径
        path = Thread.currentThread().getContextClassLoader().getResource("").getPath();
        System.out.println(path);

        //第六种  表示到项目的根目录下, 要是想到目录下的子文件夹,修改"/"即可
        //request.getSession().getServletContext().getRealPath("/"));
    }


}
