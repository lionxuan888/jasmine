package com.tian.apply.pdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.junit.Test;

import java.awt.*;
import java.io.File;

/**
 * Created by xiaoxuan.jin on 2017/1/16.
 */
public class PdfBoxTest {

    @Test
    public void testPdfBox() throws Exception {

        PDDocument document = null;
        try {
            document = PDDocument.load(new File("C:\\Users\\Administrator\\Desktop\\电子发票-1.pdf"));

            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdfTextStripper.setSortByPosition(true);


            PDPage firstPage = document.getPage(0);
            pdfTextStripper.processPage(firstPage);

            System.out.println(pdfTextStripper.getText(document));
        } finally {
        }
    }
}
