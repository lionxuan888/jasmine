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
    private static String invoiceTitleRegex = "[校]\\s[验]\\s[码]:\\s(\\d+?\\s?)+?[名]\\s+?[称]:\\s+(.*?)\\s+?";
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
            System.out.println(text);
            Pattern localBillingPattern = Pattern.compile(localBillingRegex);
            Pattern invoiceTitleRegexPattern = Pattern.compile(invoiceTitleRegex);
            System.out.println("==========================\n\t\t\t结果\n=========================");
            find(invoiceTitleRegexPattern, text, "发票抬头", 4);
            find(localBillingPattern, text, "开票地区", 2);

            String mydata = "some string with 'the data i want' inside";
            Pattern pattern = Pattern.compile("'(.*?)'");
            Matcher matcher = pattern.matcher(mydata);
            if (matcher.find())
            {
                System.out.println(matcher.group(1));
            }

        } finally {
        }

    }

    private void find(Pattern localBillingPattern, String text, String name, int group) {
        Matcher matcher = localBillingPattern.matcher(text);
        while (matcher.find()) {
            System.out.println(name + "：" + matcher.group(group));
        }
    }
}
