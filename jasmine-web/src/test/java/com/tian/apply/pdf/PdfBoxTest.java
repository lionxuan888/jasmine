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
    private static String invoiceCodeRegex = "发票代码:[\\s\\u3000]*(\\d+)";
    public static Pattern invoiceCodePattern = Pattern.compile(invoiceCodeRegex);

    // 发票号码
    private static String invoiceNumberRegex = "发票号码:[\\s\\u3000]*(\\d+)";
    public static Pattern invoiceNumberPattern = Pattern.compile(invoiceNumberRegex);

    // 开票日期
    private static String invoiceDateRegex = "开票日期:[\\s\\u3000]*(\\d+)[\\s\\u3000]*[年][\\s\\u3000]*(\\d+)[\\s\\u3000]*[月][\\s\\u3000]*(\\d+)[\\s\\u3000]*[日]";
    public static Pattern invoiceDatePattern = Pattern.compile(invoiceDateRegex);

    // 校验码
    private static String invoiceCheckCodeRegex = "[校][\\s\\u3000]*[验][\\s\\u3000]*[码]:[\\s\\u3000]*((\\d+[\\s\\u3000]*)+)";
    public static Pattern invoiceCheckCodePattern = Pattern.compile(invoiceCheckCodeRegex);

    // 发票金额
    private static String invoiceAmountRegex = "价税合计.*?(\\d+\\.?\\d+)";
    public static Pattern invoiceAmountPattern = Pattern.compile(invoiceAmountRegex);

    // 发票抬头
    //"[校]\\s*[验]\\s*[码]:\\s*(\\d+\\s*)+?[名]\\s*[称]:\\s*(\\S*)";
    private static String invoiceTitleRegex = "[校][\\s\\u3000]*[验][\\s\\u3000]*[码]:[\\s\\u3000]*(\\d+[\\s\\u3000]*)+?[名][\\s\\u3000]*[称]:[\\s\\u3000]*(\\S*)";
    public static Pattern invoiceTitlePattern = Pattern.compile(invoiceTitleRegex);

    // 销售方名称
    private static String sellerNameRegex = "价税合计.*?[\\s\\u3000]*[名][\\s\\u3000]*[称]:[\\s\\u3000]*(\\S*)";
    public static Pattern sellerNamePattern = Pattern.compile(sellerNameRegex);

    // 开票地区
    private static String localBillingRegex = "(.*?[\\s\\u3000]*)([\\u4E00-\\u9FA5]+增值]税电子普通发票)(.*)";
    public static Pattern localBillingPattern = Pattern.compile(localBillingRegex);

    // 开票内容
    private static String invoiceContentRegex = "[税][\\s\\u3000]*[额][\\s\\u3000]*((\\S*)[\\s\\u3000]*.*?[\\s\\u3000]*)[合]";
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
        String aaa = "我的动感地带";
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


    @Test
    public void findInvoiceTitle() {
        String reg = "[名][\\s\\u3000]*?[称]";

        String input1 = "名　　　　称: 北京趣拿软件科技有限公司";
        Matcher matcher = invoiceTitlePattern.matcher(text);
        while (matcher.find()) {
            System.out.println("发票抬头：" + matcher.group(2).trim());
        }


    }

    public static String stringToUnicode(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            if (ch > 255)
                str += s.charAt(i) + ": " + "\\u" + Integer.toHexString(ch)
                        + "\n";
            else
                str += s.charAt(i) + ": " + "\\u00" + Integer.toHexString(ch)
                        + "\n";
        }
        return str;
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
            // 1487301251998-滴滴电子发票
            document = PDDocument.load(new File("C:\\Users\\Administrator\\Desktop\\1487301251998-滴滴电子发票.pdf"));
            //document = PDDocument.load(new File("C:\\Users\\Administrator\\Desktop\\D45467838784.pdf"));
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdfTextStripper.setSortByPosition(true);
            PDPage firstPage = document.getPage(0);

            pdfTextStripper.processPage(firstPage);
            text = pdfTextStripper.getText(document);
            System.out.println(text);

            System.out.println("==========================\n\t\t\t发票代码\n=========================");

            PDFTextStripperByArea pdfTextStripperByArea = new PDFTextStripperByArea();
            pdfTextStripperByArea.setSortByPosition(true);
            // Rectangle rect = new Rectangle(5, 170, 170, 99);
            Rectangle rect = new Rectangle(170, 50, 100, 10);
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
