package cn.iocoder.yudao.module.jl.utils;

public class StringGenerator {

    public static char convertToLetter(int number) {
        // 'A' corresponds to integer value 65 in ASCII
        char uppercaseLetter = (char) (number + 'A' - 1);
        return uppercaseLetter;
    }

    public static String convertToAX(int colIndex,int rowIndex){
        return String.format("%s%d",convertToLetter(colIndex),rowIndex);
    }
}
