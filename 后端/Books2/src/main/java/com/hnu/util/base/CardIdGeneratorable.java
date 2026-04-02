package com.hnu.util.base;

import com.hnu.util.IdUtils;

import java.util.Random;

/**
 * @Author hj
 * @Date 2023/12/8 9:07
 * @PackageName:com.hnu.util.base
 * @ClassName: CardIdGeneratorable
 * @Description: create a class
 * @Version 1.0
 */
public interface CardIdGeneratorable {
    Random RANDOM = new Random();

    /**
     * 校验验证码
     *
     * @param id             ID
     * @param startTimeStamp 起始时间戳
     * @param timeBits       时间bit位数
     * @param timeOffset     时间偏移位数
     * @param validationBits 验证码bit位数
     * @param maxCode        最大校验码
     * @return 验证结果，合法返回true，反之false
     */
    default boolean validateCode(long id, long startTimeStamp, int timeBits, int timeOffset, int validationBits, int maxCode) {
        String bitString = Long.toBinaryString(id);
        int bitLength = bitString.length();
        String codeBitString = bitString.substring(bitLength - validationBits);
        int validationCode = Integer.parseInt(codeBitString, 2);
        long originId = id - validationCode;
        if (validationCode != IdUtils.getValidationCode(originId, maxCode)) {
            return false;
        }

        long timestamp = Long.parseLong(bitString.substring(bitLength - timeOffset - timeBits, bitLength - timeOffset), 2);
        long currentStamp = System.currentTimeMillis() / 1000 - startTimeStamp;
        long timeDelta = currentStamp - timestamp;
        return timeDelta > -3600;
    }

    /**
     * 生成一个随机数作为sequence的起始数
     *
     * @return sequence起始数
     */
    default long randomSequence() {
        return RANDOM.nextInt(10);
    }
}

