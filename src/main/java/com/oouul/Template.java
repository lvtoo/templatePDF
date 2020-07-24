package com.oouul;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.*;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Map;

public class Template {
    PdfReader reader;
    ByteArrayOutputStream bos = new ByteArrayOutputStream();
    PdfStamper stamper;
    AcroFields form;
//    Document doc = new Document();
//    PdfWriter writer;
//    ByteArrayOutputStream mar = new ByteArrayOutputStream();

    public Template(String pathname) throws IOException, DocumentException {
        this.reader = new PdfReader(pathname);
        stamper = new PdfStamper(reader, bos);
        form = stamper.getAcroFields();
//        writer = PdfWriter.getInstance(doc, mar);
    }


    public void output(FileOutputStream outputStream) {
        stamper.setFormFlattening(true);// 如果为false那么生成的PDF文件还能编辑，一定要设为true
        try {

            stamper.close();
            Document doc = new Document();
            PdfCopy copy = new PdfCopy(doc, outputStream);
            doc.open();
            PdfImportedPage importPage = copy.getImportedPage(new PdfReader(bos.toByteArray()), 1);
            copy.addPage(importPage);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    public void save(String pathname) {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(pathname);
            output(fileOutputStream);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void setProperty(String key, String value) {
        try {
            form.setField(key, value);
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }

    public void setProperties(Map<String, String> map) {
        map.forEach(this::setProperty);
    }

//    public void addMar(String content) {
//        try {
//            writer = PdfWriter.getInstance(doc, mar);
//            this.open();
//            PdfContentByte waterMar = writer.getDirectContentUnder();
//            // 开始设置水印
//            waterMar.beginText();
//            // 设置水印透明度
//            PdfGState gs = new PdfGState();
//            // 设置填充字体不透明度为0.4f
//            gs.setFillOpacity(0.4f);
//            try {
//                // 设置水印字体参数及大小                                  (字体参数，字体编码格式，是否将字体信息嵌入到pdf中（一般不需要嵌入），字体大小)
//                waterMar.setFontAndSize(BaseFont.createFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED), 60);
//                // 设置透明度
//                waterMar.setGState(gs);
//                // 设置水印对齐方式 水印内容 X坐标 Y坐标 旋转角度
//                waterMar.showTextAligned(Element.ALIGN_RIGHT, content, 500, 430, 45);
//                // 设置水印颜色
//                waterMar.setColorFill(BaseColor.GRAY);
//                //结束设置
//                waterMar.endText();
//                waterMar.stroke();
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                waterMar = null;
//                gs = null;
//            }
//        } catch (DocumentException e) {
//            e.printStackTrace();
//        }
//
//    }

    public void close() {

        try {
//            doc.close();
            bos.close();
            stamper.close();
            reader.close();
            System.gc();
        } catch (IOException | DocumentException e) {
            e.printStackTrace();
        }
    }
}
