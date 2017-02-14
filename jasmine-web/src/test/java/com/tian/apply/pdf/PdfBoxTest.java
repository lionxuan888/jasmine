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
    // 发票代码
    private static String invoiceCodeRegex = "(.*?[发][票][代][码]:.*?)(\\d+)(.*)";
    // 发票号码
    private static String invoiceNumberRegex = "(.*?[发][票][号][码]:.*?)(\\d+)(.*)";
    // 开票日期
    private static String invoiceDateRegex = "(.*?[开][票][日][期]:.*?)(\\d+)(.*)";
    // 校验码
    private static String invoiceCheckCodeRegex = "(.*?[校][验][码]:.*?)(\\d+)(.*)";
    // 发票金额
    private static String invoiceAmountRegex = "(.*?[价][税][合][计].*?)(\\d+)(.*)";
    // 发票抬头
    private static String invoiceTitleRegex = "(.*?[价][税][合][计].*?)(\\d+)(.*)";
    // 开票地区
    private static String localBillingRegex = "(.*?\\s+?)([\\u4E00-\\u9FA5]+[增][值][税][电][子][普][通][发][票])(.*)";
    @Test
    public void testPdfBox() throws Exception {

        PDDocument document = null;
        try {
            document = PDDocument.load(new File("C:\\Users\\Administrator\\Desktop\\电子发票-1.pdf"));
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdfTextStripper.setSortByPosition(true);
            PDPage firstPage = document.getPage(0);

            pdfTextStripper.processPage(firstPage);
            String text = pdfTextStripper.getText(document);

            Pattern patter = Pattern.compile(localBillingRegex);
            Matcher matcher = patter.matcher(text);
            System.out.println(text);
            System.out.println("==========================\n\t\t\t结果\n=========================");
            while (matcher.find()) {
                System.out.println("开票地区：" + matcher.group(2));
            }





        } finally {
        }

    }
}
