package com.tian.apply.pdf;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.exception.ExceptionUtils;
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
    private static String invoiceCodeRegex = "[发][票][代][码]:\\s*?(\\d+)";
    private Pattern invoiceCodePattern = Pattern.compile(invoiceCodeRegex);

    // 发票号码
    private static String invoiceNumberRegex = "[发][票][号][码]:\\s*?(\\d+)";
    private Pattern invoiceNumberPattern = Pattern.compile(invoiceNumberRegex);

    // 开票日期
    private static String invoiceDateRegex = "[开][票][日][期]:\\s*?(\\d+)\\s*?[年]\\s*?(\\d+)\\s*?[月]\\s*?(\\d+)\\s*?[日]";
    private Pattern invoiceDatePattern = Pattern.compile(invoiceDateRegex);

    // 校验码
    private static String invoiceCheckCodeRegex = "[校]\\s*?[验]\\s*?[码]:\\s*?((\\d+\\s*)+)";
    private Pattern invoiceCheckCodePattern = Pattern.compile(invoiceCheckCodeRegex);

    // 发票金额
    private static String invoiceAmountRegex = "[价][税][合][计].*?(\\d+\\.?\\d+)";
    private Pattern invoiceAmountPattern = Pattern.compile(invoiceAmountRegex);

    // 发票抬头
    private static String invoiceTitleRegex = "[校]\\s*?[验]\\s*?[码]:\\s*?(\\d+\\s*)+?[名]\\s+?[称]:\\s+?(.*?)\\s+?";
    private Pattern invoiceTitlePattern = Pattern.compile(invoiceTitleRegex);

    // 开票地区
    private static String localBillingRegex = "(.*?\\s+?)([\\u4E00-\\u9FA5]+[增][值][税][电][子][普][通][发][票])(.*)";
    private Pattern localBillingPattern = Pattern.compile(localBillingRegex);

    @Test
    public void testPdfBox() throws Exception {
        findInvoiceCode();
        findInvoiceNumber();
        findInvoiceDate();
        findInvoiceCheckCode();
        findInvoiceAmount();
        findInvoiceTitle();
        findLocalBilling();
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
            System.out.println("发票金额：" + matcher.group(1).trim());
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

    private String text  = "";
    {
        PDDocument document = null;
        try {
            document = PDDocument.load(new File("C:\\Users\\Administrator\\Desktop\\电子发票-1.pdf"));
            PDFTextStripper pdfTextStripper = new PDFTextStripper();
            pdfTextStripper.setSortByPosition(true);
            PDPage firstPage = document.getPage(0);

            pdfTextStripper.processPage(firstPage);
            text = pdfTextStripper.getText(document);
            System.out.println(text);
            System.out.println("==========================\n\t\t\t结果\n=========================");

        } catch (Exception e) {
            e.printStackTrace();
        } finally{
        }
    }
}
