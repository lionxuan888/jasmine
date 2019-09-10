package com.tian.algorithm;

import com.google.common.util.concurrent.Uninterruptibles;
import org.junit.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.concurrent.TimeUnit;

/**
 * Created by logan on 2019/9/10.
 */
public class RepayCal {


    public static void main(String[] args) {
        Uninterruptibles.sleepUninterruptibly(3, TimeUnit.SECONDS);
        // 年利率
        BigDecimal yearRate = new BigDecimal("0.0540");
        // 贷款年限
        int year = 30;
        BigDecimal debtAmount = new BigDecimal("300000");
        BigDecimal everyMonthPayAmount = everyMonthPay(yearRate, year, debtAmount);
        System.out.println("贷款总额:" + debtAmount);
        System.out.println("每月还款额度:" + everyMonthPayAmount.setScale(2, RoundingMode.HALF_UP));
        System.out.println("共计还款 : " + everyMonthPayAmount.multiply(new BigDecimal(year).multiply(new BigDecimal(12))).setScale(2, RoundingMode.HALF_UP));
        // 已经还款几年
        int repayYear = 5;
        BigDecimal repayAmount = new BigDecimal("200000");
        System.out.println(String.format("如果第%s年发生了还款, 还款额 %s ", (repayYear+1), repayAmount));


        /*
         * 第4年有30w
         * 1. 如果还款, 总共支出多少,收入多少 (收入部分的本金采用提前还款重新计算之后每月少还的钱, 计算截止到最后还款期限)
         * 2. 如果不还款, 总共支出多少, 收入多少
         */


        // 1. 如果还款
        {
            BigDecimal allPay = BigDecimal.ZERO;
            BigDecimal balanceBenJinAmount = getBalanceDebtAmount(yearRate, debtAmount, everyMonthPayAmount, repayYear * 12);
            BigDecimal balanceMonthPay = everyMonthPay(yearRate, year - repayYear, balanceBenJinAmount.subtract(repayAmount));
            System.out.println("-----------------------第一种方式, 提前还款(每月还款额减少,期限不变) 分割线---------------------");
            System.out.println(String.format("剩余本金%s,还款之后剩余本金%s",
                    balanceBenJinAmount.setScale(2, RoundingMode.HALF_UP),
                    balanceBenJinAmount.subtract(repayAmount).setScale(2, RoundingMode.HALF_UP)));
            System.out.println("剩余岁月每月还款额:" + balanceMonthPay.setScale(2, RoundingMode.HALF_UP));
            System.out.println("剩余岁月总支出(每个月1期,每月还款额乘以剩余期数):" + balanceMonthPay.multiply(new BigDecimal((year - repayYear) * 12)).setScale(2, RoundingMode.HALF_UP));

            BigDecimal shaoHuanAmountEveryMonth = everyMonthPayAmount.subtract(balanceMonthPay).setScale(2, RoundingMode.HALF_UP);
            System.out.println("每个月少还:" + shaoHuanAmountEveryMonth);
            BigDecimal shaoHuanAmountYearInCome = shaoHuanAmountEveryMonth.multiply(new BigDecimal("12"));
            BigDecimal shaoHuanIncomeAllSum = BigDecimal.ZERO;
            for (int i = 0; i < year - repayYear -1; i++) {
                int liCaiYear = (int) year - repayYear - (i+1);
                BigDecimal augend = liCaiIncome(liCaiYear, shaoHuanAmountYearInCome);
//                System.out.println(String.format("第%s年的理财收入(理财年限%s,理财金额%s) :", i +1, liCaiYear, shaoHuanAmountYearInCome) + augend.setScale(2, RoundingMode.HALF_UP));
                shaoHuanIncomeAllSum = shaoHuanIncomeAllSum.add(augend);
            }
            System.out.println(String.format("少还的钱拿来投资获得总收入(每年年底获得%s然后拿来投资):%s" ,shaoHuanAmountYearInCome, shaoHuanIncomeAllSum.setScale(2, RoundingMode.HALF_UP)));

            System.out.println("-----------------------第二种方式, 不提前还款, 分割线---------------------");
            System.out.println("剩余岁月总支出(按照现有方式不变,每月还款额乘以剩余期数):" + everyMonthPayAmount.multiply(new BigDecimal((year - repayYear) * 12)).setScale(2, RoundingMode.HALF_UP));
            int liCaiYear = year - repayYear;
            BigDecimal liCaiIncomeOfRepay = liCaiIncome(liCaiYear, repayAmount);
            System.out.println("剩余岁月总收入(还款额复利计算):" + liCaiIncomeOfRepay.setScale(2, RoundingMode.HALF_UP));
        }



    }

    /**
     * 按照复利计算理财总收入
     * @param liCaiYear
     * @param liCaiAmount
     */
    private static BigDecimal liCaiIncome(int liCaiYear, BigDecimal liCaiAmount) {
        BigDecimal liCaiLiLv = new BigDecimal("0.05");
        BigDecimal sumRate = BigDecimal.ZERO;
        sumRate = sumRate.add(new BigDecimal(Math.pow(liCaiLiLv.doubleValue() + 1 ,  liCaiYear)));
//        System.out.println("理财利率:" + sumRate);
//        System.out.println("理财总收入:" + liCaiAmount.multiply(sumRate));
        return liCaiAmount.multiply(sumRate);
        /*
         *  第一年  amount + amount * 0.05  本金 + 利息
         *
         *  第二年  (amount + amount * 0.05 ) +  (amount + amount * 0.05 ) * 0.05  = amount (1+0.05)^2
         *
         */

    }

    @Test
    public void testIncome() {
        System.out.println(1700*25*12);
        BigDecimal repayAmount = new BigDecimal("150000");
        BigDecimal liCaiIncome = liCaiIncome(25, repayAmount);
        BigDecimal monthAmount = new BigDecimal("1000");
        BigDecimal yearAmount = monthAmount.multiply(new BigDecimal(12));
        BigDecimal allSumAmount = BigDecimal.ZERO;
        for (int i = 0; i < 25; i++) {
            int liCaiYear = 25 - i;
            BigDecimal augend = liCaiIncome(liCaiYear, yearAmount);
//                System.out.println(String.format("第%s年的理财收入(理财年限%s,理财金额%s) :", i +1, liCaiYear, shaoHuanAmountYearInCome) + augend.setScale(2, RoundingMode.HALF_UP));
            allSumAmount = allSumAmount.add(augend);
        }
        System.out.println("全部理财结果:" + liCaiIncome.setScale(2, RoundingMode.HALF_UP));
        System.out.println("迭代理财结果:" + allSumAmount.setScale(2, RoundingMode.HALF_UP));
    }

    /**
     * 获取剩余本金
     * @param yearRate
     * @param debtAmount
     * @param everyMonthPayAmount
     * @param monthCount
     * @return
     */
    private static BigDecimal getBalanceDebtAmount(BigDecimal yearRate, BigDecimal debtAmount, BigDecimal everyMonthPayAmount, int monthCount) {
        BigDecimal monthRate= yearRate.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);
        BigDecimal sumBenJinAmount = BigDecimal.ZERO;
        for (int i = 1; i <= monthCount; i++) {
            BigDecimal interestInYears = debtAmount.subtract(sumBenJinAmount).multiply(monthRate);
//            System.out.print("第"+i+"个月利息:" + interestInYears.setScale(2, RoundingMode.HALF_UP));
            BigDecimal thisMonthBenJinAmount = everyMonthPayAmount.subtract(interestInYears);
//            System.out.println(" 本金:" + thisMonthBenJinAmount.setScale(2, RoundingMode.HALF_UP));
            sumBenJinAmount = sumBenJinAmount.add(thisMonthBenJinAmount);
        }
        BigDecimal balance = debtAmount.subtract(sumBenJinAmount).setScale(2, RoundingMode.HALF_UP);
//        System.out.println("剩余本金:" + balance);
        return balance;
    }

    private static BigDecimal everyMonthPay(BigDecimal yearRate, double year, BigDecimal debtAmount) {
        // 月利率
        BigDecimal monthRate= yearRate.divide(new BigDecimal("12"), 10, RoundingMode.HALF_UP);

        BigDecimal onePlusRate = new BigDecimal("1").add(monthRate);
        BigDecimal pow = new BigDecimal(Math.pow(onePlusRate.doubleValue(), year * 12));
        BigDecimal divide = debtAmount
                .multiply(monthRate)
                .multiply(pow)
                .divide(pow.subtract(new BigDecimal(1)), 10, RoundingMode.HALF_UP);
        ;
        return divide;
    }

}
