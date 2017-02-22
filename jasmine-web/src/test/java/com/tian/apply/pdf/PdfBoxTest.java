package com.tian.apply.pdf;

import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;

import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.junit.Test;


import java.awt.*;
import java.io.*;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xiaoxuan.jin on 2017/1/16.
 */
public class PdfBoxTest {
    // 发票代码
    private static String invoiceCodeRegex = "[发][票][代][码]:\\s*(\\d+)";
    public static Pattern invoiceCodePattern = Pattern.compile(invoiceCodeRegex);

    // 发票号码
    private static String invoiceNumberRegex = "[发][票][号][码]:\\s*(\\d+)";
    public static Pattern invoiceNumberPattern = Pattern.compile(invoiceNumberRegex);

    // 开票日期
    private static String invoiceDateRegex = "[开][票][日][期]:\\s*(\\d+)\\s*[年]\\s*(\\d+)\\s*[月]\\s*(\\d+)\\s*[日]";
    public static Pattern invoiceDatePattern = Pattern.compile(invoiceDateRegex);

    // 校验码
    private static String invoiceCheckCodeRegex = "[校]\\s*[验]\\s*[码]:\\s*((\\d+\\s*)+)";
    public static Pattern invoiceCheckCodePattern = Pattern.compile(invoiceCheckCodeRegex);

    // 发票金额
    private static String invoiceAmountRegex = "[价][税][合][计].*?(\\d+\\.?\\d+)";
    public static Pattern invoiceAmountPattern = Pattern.compile(invoiceAmountRegex);

    // 发票抬头
    private static String invoiceTitleRegex = "[校]\\s*[验]\\s*[码]:\\s*(\\d+\\s*)+?[名]\\s*[称]:\\s*(\\S*)";
    public static Pattern invoiceTitlePattern = Pattern.compile(invoiceTitleRegex);

    // 销售方名称
    private static String sellerNameRegex = "[价][税][合][计].*?\\s*[名]\\s*[称]:\\s*(\\S*)";
    public static Pattern sellerNamePattern = Pattern.compile(sellerNameRegex);

    // 开票地区
    private static String localBillingRegex = "(.*?\\s*)([\\u4E00-\\u9FA5]+[增][值][税][电][子][普][通][发][票])(.*)";
    public static Pattern localBillingPattern = Pattern.compile(localBillingRegex);

    // 开票内容
    private static String invoiceContentRegex = "[税]\\s*[额]\\s*((\\S*)\\s*.*?\\s*)[合]";
    public static Pattern invoiceContentPattern = Pattern.compile(invoiceContentRegex);

    @Test
    public void testParser() throws Exception {

        PDDocument document = PDDocument.load(new File("C:\\Users\\Administrator\\Desktop\\电子发票-4.pdf"));
        PDFTextStripper pdfTextStripper = new PDFTextStripper();
        pdfTextStripper.setSortByPosition(true);
        PDPage firstPage = document.getPage(0);
        PDFStreamParser parser = new PDFStreamParser(firstPage);
        parser.parse();
        List<Object> tokens = parser.getTokens();
        System.out.println(tokens);
    }

    @Test
    public void testString() throws Exception {
        String aaa= "我的动感地带";
        System.out.println(aaa.length());
        System.out.println(aaa.substring(0, 5));
    }

    @Test
    public void testPdfBox() throws Exception {
        findInvoiceCode();
        findInvoiceNumber();
        findInvoiceDate();
        findInvoiceCheckCode();
        findInvoiceAmount();
        findInvoiceTitle();
        findLocalBilling();
        findSellerName();
        findInvoiceContent();
    }

    private void findInvoiceContent() {
        Matcher matcher = invoiceContentPattern.matcher(text);
        while (matcher.find()) {
            System.out.println("开票内容group2：" + matcher.group(2));
        }
    }


    private void findSellerName() {
        Matcher matcher = sellerNamePattern.matcher(text);
        while (matcher.find()) {
            System.out.println("销售方名称：" + matcher.group(1));
        }
    }


    private void findInvoiceCode() {
        Matcher matcher = invoiceCodePattern.matcher(text);
        while (matcher.find()) {
            System.out.println("发票代码：" + matcher.group(1));
        }
    }

    private void findInvoiceNumber() {
        Matcher matcher = invoiceNumberPattern.matcher(text);
        while (matcher.find()) {
            System.out.println("发票号码：" + matcher.group(1));
        }
    }

    private void findInvoiceDate() {
        Matcher matcher = invoiceDatePattern.matcher(text);
        while (matcher.find()) {
            System.out.println("开票日期：" + matcher.group(1) + matcher.group(2) + matcher.group(3));
        }
    }

    private void findInvoiceCheckCode() {
        Matcher matcher = invoiceCheckCodePattern.matcher(text);
        while (matcher.find()) {
            System.out.println("校验码：" + matcher.group(1).trim());
        }
    }

    private void findInvoiceAmount() {
        Matcher matcher = invoiceAmountPattern.matcher(text);
        while (matcher.find()) {
            System.out.println("发票金额(价税合计)：" + matcher.group(1).trim());
        }
    }


    private void findInvoiceTitle() {
        Matcher matcher = invoiceTitlePattern.matcher(text);
        while (matcher.find()) {
            System.out.println("发票抬头：" + matcher.group(2).trim());
        }
    }


    private void findLocalBilling() {
        Matcher matcher = localBillingPattern.matcher(text);
        while (matcher.find()) {
            System.out.println("开票地区：" + matcher.group(2).trim());
        }
    }

    private String text = "";

    {
        PDDocument document = null;
        try {
            document = PDDocument.load(new File("C:\\Users\\Administrator\\Desktop\\滴滴电子发票.pdf"));
            //document = PDDocument.load(new File("C:\\Users\\Administrator\\Desktop\\D45467838784.pdf"));
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdfTextStripper.setSortByPosition(true);
            PDPage firstPage = document.getPage(0);

            pdfTextStripper.processPage(firstPage);
            text = pdfTextStripper.getText(document);
            System.out.println(text);

            System.out.println("==========================\n\t\t\t开票内容\n=========================");

            PDFTextStripperByArea pdfTextStripperByArea = new PDFTextStripperByArea();
            pdfTextStripperByArea.setSortByPosition(true);
            // Rectangle rect = new Rectangle(5, 170, 170, 99);
            Rectangle rect = new Rectangle(5, 160, 170, 99);
            pdfTextStripperByArea.addRegion("class1", rect);
            pdfTextStripperByArea.extractRegions(firstPage);
            System.out.println("Text in the area:" + rect);
            System.out.println(pdfTextStripperByArea.getTextForRegion("class1"));

            System.out.println("==========================\n\t\t\t结果\n=========================");


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }
}
