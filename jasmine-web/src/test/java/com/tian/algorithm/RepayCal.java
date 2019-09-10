package com.tian.algorithm;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by logan on 2019/9/10.
 */
public class RepayCal {


    public static void main(String[] args) {
        // 年利率
        BigDecimal yearRate = new BigDecimal("0.0539");
        // 贷款年限
        double year = 30;
        BigDecimal debtAmount = new BigDecimal("1000000");
        BigDecimal everyMonthPayAmount = everyMonthPay(yearRate, year, debtAmount);
        System.out.println("每月还款额度:" + everyMonthPayAmount);
        System.out.println("共计还款 : " + everyMonthPayAmount.multiply(new BigDecimal(30).multiply(new BigDecimal(12))));

        System.out.println("如果第4年发生了还款, 还款额 30W ");


        /*
         * 第4年有30w
         * 1. 如果还款, 总共支出多少,收入多少 (收入部分的本金采用提前还款重新计算之后每月少还的钱, 计算截止到最后还款期限)
         * 2. 如果不还款, 总共支出多少, 收入多少
         */
        BigDecimal repayAmount = new BigDecimal("300000");
        // 第几年还款
        int repayYear = 5;
        // 1. 如果还款
        {
            BigDecimal allPay = BigDecimal.ZERO;
            BigDecimal balanceBenJinAmount = getBalanceDebtAmount(yearRate, debtAmount, everyMonthPayAmount, (repayYear-1) * 12);
            BigDecimal balanceMonthPay = everyMonthPay(yearRate, year - (repayYear-1), balanceBenJinAmount.subtract(repayAmount));
            System.out.println("剩余岁月每月还款额:" + balanceMonthPay.setScale(2, RoundingMode.HALF_UP));

            System.out.println("剩余岁月总支出:" + balanceMonthPay.multiply(new BigDecimal((year - (repayYear- 1)) * 12)));

            BigDecimal shaoHuanAmountEveryMonth = everyMonthPayAmount.subtract(balanceMonthPay).setScale(2, RoundingMode.HALF_UP);
            System.out.println("每个月少还:" + shaoHuanAmountEveryMonth);
            BigDecimal shaoHuanAmountYearInCome = shaoHuanAmountEveryMonth.multiply(new BigDecimal("12"));
            BigDecimal shaoHuanIncomeAllSum = BigDecimal.ZERO;
            for (int i = 0; i < year - repayYear; i++) {
                BigDecimal augend = liCaiIncome((int) year - repayYear - i, shaoHuanAmountYearInCome);
                System.out.println("第" + (repayYear + i + 1)  + "年的理财收入: " + augend.setScale(2, RoundingMode.HALF_UP));
                shaoHuanIncomeAllSum = shaoHuanIncomeAllSum.add(augend);
            }
            System.out.println("少还的钱总收入:" + shaoHuanIncomeAllSum.setScale(2, RoundingMode.HALF_UP));
//            liCaiIncome(30, new BigDecimal("30"));

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
