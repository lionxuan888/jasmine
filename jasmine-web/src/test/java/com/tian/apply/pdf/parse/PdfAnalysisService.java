package com.tian.apply.pdf.parse;

import com.google.zxing.*;
import com.google.zxing.Reader;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSDictionary;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDTransparencyGroup;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.text.PDFTextStripper;
import org.junit.Test;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.*;

import org.apache.pdfbox.text.PDFTextStripperByArea;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PdfAnalysisService {

    public static Logger LOGGER = LoggerFactory.getLogger(PdfAnalysisService.class);

    @Test
    public void testPdf() throws Exception {
        File fileInput = new File("C:\\Users\\Administrator\\Desktop\\pdfElement\\1487301251998-滴滴电子发票.pdf");
        FileInputStream input = new FileInputStream(fileInput);
        PDDocument document = PDDocument.load(input);
        PDFTextStripper textStripper = new PDFTextStripper();
        PDPage page = document.getPage(0);
        textStripper.processPage(page);
        String text = textStripper.getText(document);
        System.out.println(text);
        PDResources resources = page.getResources();

        COSDictionary cosObject = resources.getCOSObject();
        Set<Map.Entry<COSName, COSBase>> entries = cosObject.entrySet();
        for (Map.Entry<COSName, COSBase> entry : entries) {
            COSName key = entry.getKey();
            COSBase value = entry.getValue();
            System.out.println(key.getName());

            COSBase cosObject1 = value.getCOSObject();
            System.out.println(cosObject1.getCOSObject());
        }

    }

    @Test
    public void analysisPdf() {
        try {
            Map<String, String> resultMap = new HashMap<String, String>();
            File fileInput = new File("C:\\Users\\Administrator\\Desktop\\pdfElement\\1487301251998-滴滴电子发票.pdf");
            FileInputStream input = new FileInputStream(fileInput);
            //inputsteam重用
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len;
            while ((len = input.read(buffer)) > -1) {
                baos.write(buffer, 0, len);
            }
            baos.flush();

            //解析
            InputStream pdfInput = new ByteArrayInputStream(baos.toByteArray());
            String QRCODE_PATH = "d:\\";
            Map<String, String> map = pdfParse(pdfInput, QRCODE_PATH);
            System.out.println("##########解析二维码开始");
            String file = map.get("path") + "/" + map.get("imgName");
            resultMap = decode(map.get("path"), map.get("imgName"));
            String amount = map.get("amount");
            amount = amount.substring(amount.indexOf("¥") + 1, amount.indexOf(".") + 3);
            resultMap.put("amount", amount);

            LOGGER.info("analysisPdf result=" + resultMap);
            LOGGER.info("Exiting analysisPdf ... ");

        } catch (Exception e) {
            e.printStackTrace();
            LOGGER.error("error", e);

        }

    }

    /**
     * 解析pdf文档信息
     *
     * @param pdfPath pdf文档路径
     * @param rootDit 二维码图片存放路径
     */
    public Map<String, String> pdfParse(InputStream input, String rootDir) {
        Map<String, String> resultMap = new HashMap<String, String>();
        PDDocument document = null;
        try {
            LOGGER.info("Entering pdfParse ... ");
            LOGGER.info("pdfParse param =" + rootDir);
            //input = new FileInputStream(pdfFile);
            // 加载 pdf 文档
            document = PDDocument.load(input);
            // 读取图片信息
            StringBuffer imgName = new StringBuffer();
            PDPageTree list = document.getPages();
            for (PDPage page : list) {
                PDResources pdResources = page.getResources();
                for (COSName c : pdResources.getXObjectNames()) {
                    PDXObject o = pdResources.getXObject(c);
                    if (o instanceof PDImageXObject) {
                        String fileName = System.nanoTime() + ".png";
                        imgName.append(fileName + ",");
                        String filePath = createDir(rootDir);
                        File file = new File(filePath + "/" + fileName);
                        LOGGER.info("QRCode file=" + file);
                        ImageIO.write(((PDImageXObject) o).getImage(), "png",
                                file);
                        resultMap.put("path", filePath);

                        LOGGER.info("读取图片信息，二维码图片存放路劲：" + filePath + "###" + fileName);
                        //break;
                    }
                }
            }

            if ("".equals(imgName.toString()) || imgName.toString() == null) {
                for (PDPage page : list) {
                    PDResources pdResources = page.getResources();
                    imgName = Recurcepdf(resultMap, pdResources, rootDir);
                }
            }
            resultMap.put("imgName", imgName.toString());

            //获取开票金额
            /*PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition( true );
            Rectangle rect = new Rectangle( 400, 280, 275, 60 );
            stripper.addRegion( "class1", rect );
            PDPage firstPage = (PDPage)list.get( 0 );
            stripper.extractRegions( firstPage );*/
            PDFTextStripperByArea stripper = new PDFTextStripperByArea();
            stripper.setSortByPosition(true);
            Rectangle rect = new Rectangle(300, 180, 675, 360);
            stripper.addRegion("class1", rect);
            PDPage firstPage = (PDPage) list.get(0);
            stripper.extractRegions(firstPage);
            String s = stripper.getTextForRegion("class1");
            //确定小写的位置
            int index = s.indexOf("(小写)");
            int moneyIndex = -1;
            if (index == -1) {
                index = s.indexOf("（小写）");
                //哎呀呀..还是没找到
                if (index == -1) {
                    //取最后出现的那个带￥的数字
                    moneyIndex = s.lastIndexOf("¥");
                    if (moneyIndex == -1) {
                        moneyIndex = s.lastIndexOf("￥");
                    }
                    index = moneyIndex;
                }
            }
            String subStr = s.substring(index);
            String str[] = subStr.split("\r\n");
            String amount = null;
            if (moneyIndex != -1) {
                amount = str[0].trim();
            } else {
                amount = str[0].substring(4).trim();
            }
            if (amount.indexOf("¥") != -1 || amount.indexOf("￥") != -1) {
                amount = amount.substring(1);
            }
            resultMap.put("amount", amount);

            LOGGER.info("pdfParse result=" + resultMap);
            LOGGER.info("Exiting pdfParse ... ");
        } catch (FileNotFoundException e) {
            LOGGER.error("FileNotFoundException", e);
            e.printStackTrace();
        } catch (IOException e1) {
            LOGGER.error("IOException", e1);
            e1.printStackTrace();
        } finally {
            if (null != input)
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (null != document)
                try {
                    document.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return resultMap;
    }

    private StringBuffer imgName = new StringBuffer();

    StringBuffer Recurcepdf(Map<String, String> resultMap, PDResources pdResources, String rootDir) throws IOException {

        //Map<String,String> resultMap = new HashMap<String, String>();
        for (COSName c : pdResources.getXObjectNames()) {
            PDXObject o = pdResources.getXObject(c);

            if (o instanceof PDImageXObject) {
                String fileName = System.nanoTime() + ".png";
                imgName.append(fileName + ",");
                String filePath = createDir(rootDir);
                File file = new File(filePath + "/" + fileName);
                LOGGER.info("QRCode file=" + file);
                ImageIO.write(((PDImageXObject) o).getImage(), "png",
                        file);
                resultMap.put("path", filePath);

                LOGGER.info("读取图片信息，二维码图片存放路劲：" + filePath + "###" + fileName);

            }

            if (o instanceof PDTransparencyGroup) {
                PDResources resources = ((PDTransparencyGroup) o).getResources();

                Recurcepdf(resultMap, resources, rootDir);
            }

        }
        //resultMap.put("imgName", imgName.toString());
        return imgName;

    }

    // 解析图片二维码
    public static Map<String, String> decode(String imgPath, String imgNames) throws Exception {
        Map<String, String> resultMap = new HashMap<String, String>();
        LOGGER.info("Entering decode 解析二维码开始。。。 ");
        LOGGER.info("decode param imgPath=" + imgPath + "### imgNames=" + imgNames);
        String imgNameStr = imgNames.substring(0, imgNames.length() - 1);
        String[] imageNames = imgNameStr.split(",");
        //String sheetCode = null;
        for (String name : imageNames) {
            try {
                Reader reader = new MultiFormatReader();
                File file = new File(imgPath + "/" + name);
                BufferedImage image;
                image = ImageIO.read(file);
                if (image == null) {
                    LOGGER.info("Could not decode image, image is null");
                }
                LuminanceSource source = new BufferedImageLuminanceSource(image);
                BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
                Result result;
                Hashtable hints = new Hashtable();
                hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
                // 解码设置编码方式为：utf-8，
                result = new MultiFormatReader().decode(bitmap, hints);
                String resultStr = result.getText();
                LOGGER.info("解析后内容：" + resultStr);
                String s[] = resultStr.split(",");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
                Date d = sdf2.parse(s[5]);
                String invoiceDate = sdf.format(d);

                resultMap.put("invoiceNum", s[3]);
                resultMap.put("invoiceCode", s[2]);
                resultMap.put("invoiceDate", invoiceDate);
                resultMap.put("checkCode", s[6]);
                //sheetCode = s[2]+s[3];
                LOGGER.info("decode result=" + resultMap);
                LOGGER.info("Exiting decode 解析二维码结束。。。 ");

            } catch (Exception e) {
            }
        }
        return resultMap;
    }

    public static String createDir(String rootDir) {
        Calendar date = Calendar.getInstance();
        String destDirName = rootDir + File.separator + date.get(Calendar.YEAR)
                + File.separator + (date.get(Calendar.MONTH) + 1) + File.separator
                + date.get(Calendar.DAY_OF_MONTH);
        File dir = new File(destDirName);
        //System.out.println(dir.getAbsolutePath());
        //File dir = new File(destDirName);  
        if (dir.exists()) {
            LOGGER.info("创建目录" + destDirName + "失败，目标目录已经存在");
            System.out.println("创建目录" + destDirName + "失败，目标目录已经存在");
            return destDirName;
        }
        if (!destDirName.endsWith(File.separator)) {
            destDirName = destDirName + File.separator;
        }
        //创建目录  
        if (dir.mkdirs()) {
            LOGGER.info("创建目录" + destDirName + "成功！");
            System.out.println("创建目录" + destDirName + "成功！");
            return destDirName;
        } else {
            LOGGER.info("创建目录" + destDirName + "失败！");
            System.out.println("创建目录" + destDirName + "失败！");
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*String date = "20160503";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd"); 
		Date d = sdf2.parse(date);
		String s = sdf.format(d);
		System.out.println(s);*/

        Reader reader = new MultiFormatReader();
        File file = new File("E:/60E9C26A.PNG");
        BufferedImage image;
        image = ImageIO.read(file);
        if (image == null) {
            LOGGER.info("Could not decode image, image is null");
        }
        LuminanceSource source = new BufferedImageLuminanceSource(image);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Result result;
        Hashtable hints = new Hashtable();
        hints.put(DecodeHintType.CHARACTER_SET, "utf-8");
        // 解码设置编码方式为：utf-8，
        result = new MultiFormatReader().decode(bitmap, hints);
        String resultStr = result.getText();
        System.out.println("解析后内容：" + resultStr);

        String s = URLDecoder.decode(URLDecoder.decode(resultStr));
        System.out.println(s);
    }

}
