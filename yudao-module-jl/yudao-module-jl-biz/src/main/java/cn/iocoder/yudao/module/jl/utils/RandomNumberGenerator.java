package cn.iocoder.yudao.module.jl.utils;

import java.util.Random;

public class RandomNumberGenerator {

    public static String generateRandomNumber(int numDigits) {
        if (numDigits <= 0) {
            throw new IllegalArgumentException("位数必须大于0");
        }

        Random random = new Random();
        StringBuilder sb = new StringBuilder(numDigits);

        for (int i = 0; i < numDigits; i++) {
            // 生成一个随机的数字（0到9之间）
            int digit = random.nextInt(10);
            // 确保第一位不是0
            if (i == 0 && digit == 0) {
                digit = 1 + random.nextInt(9); // 随机生成1到9之间的数字
            }
            sb.append(digit);
        }

        return sb.toString();
    }

    public static void main(String[] args) {
        int numDigits = 6; // 指定位数
        String randomNumber = generateRandomNumber(numDigits);
        System.out.println("随机数：" + randomNumber);
    }
}