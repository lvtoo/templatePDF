package com.oouul;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class Main {
    public static void main(String[] args) {
        try {
            Template template =new Template(Main.class.getClassLoader().getResource("template0.pdf").getPath());


            template.setProperty("1", "lvtoo");
//            template.addMar("周鹏飞");

            template.save("D:\\document\\templatePDF\\result1.pdf");
            template.close();


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
