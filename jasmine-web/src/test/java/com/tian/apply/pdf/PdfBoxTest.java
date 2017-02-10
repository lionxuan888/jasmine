package com.tian.apply.pdf;

import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.junit.Test;

import java.awt.*;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaoxuan.jin on 2017/1/16.
 */
public class PdfBoxTest {

    @Test
    public void testPdfBox() throws Exception {

        PDDocument document = null;
        try {
//            document = PDDocument.load(new File("C:\\Users\\Administrator\\Desktop\\电子发票-1.pdf"));
//            PDFTextStripper pdfTextStripper = new PDFTextStripper();
//            pdfTextStripper.setSortByPosition(true);
//            PDPage firstPage = document.getPage(0);
//            pdfTextStripper.processPage(firstPage);
//            String lineSeparator = pdfTextStripper.getLineSeparator();
//            System.out.println("line:" + lineSeparator);
//            String text = pdfTextStripper.getText(document);
//            System.out.println(text);

            Pattern patter = Pattern.compile("\\b");
            String reg = "\\b";
            Matcher matcher = patter.matcher("345");

            int i = matcher.groupCount();
            System.out.println(i);

        } finally {
        }

    }
}
