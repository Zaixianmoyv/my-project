package com.hnu.util;

import com.hnu.util.base.CardIdGeneratorable;

/**
 * @Author hj
 * @Date 2023/12/8 9:03
 * @PackageName:com.hnu.util
 * @ClassName: ShortCardIdGenerator
 * @Description: 短号生成器
 * @Version 1.0
 * 卡号固定为 13 位，全数字，43bit。格式为：”1290903816253“。
 * +=====================================================
 * | 3bit 机器编号 | 29bit 时间戳 | 8bit 序号 | 3bit 卡号校验位 |
 * +=====================================================
 * 29 bit 的秒时间戳支持 17 年
 * 8 bit 序号支持 256 个序号（起始序号是 20 以内的随机数）
 * 3 bit 机器编号支持 7 台负载（负载编号从 1-7）
 * 即卡号生成最大支持 7 台负载；每台负载每秒钟可以生成最少 236，最多 256 个卡号。
 */

public class ShortCardIdGenerator implements CardIdGeneratorable {

    /**
     * 时间bit数，时间的单位为秒，29 bit位时间可以表示17年
     */
    private final int timeBits = 29;

    /**
     * 机器编码bit数
     */
    @SuppressWarnings("FieldCanBeLocal")
    private final int machineBits = 3;

    /**
     * 每秒序列bit数
     */
    private final int sequenceBits = 8;

    /**
     * 校验bit位数
     */
    private final int validationBits = 3;

    /**
     * 上一次时间戳
     */
    private long lastStamp = -1L;

    /**
     * 序列
     */
    private long sequence = randomSequence();

    /**
     * 机器编号
     */
    @SuppressWarnings("UnusedAssignment")
    private long machineId = 1L;

    /**
     * 时间左移bit数
     */
    private int timeOffset = 0;

    /**
     * 机器编码左移bit数
     */
    private int machineOffset = 0;

    /**
     * 序列左移bit数
     */
    private int sequenceOffset = 0;

    /**
     * 最大序列号
     */
    private long maxSequence = 0L;

    /**
     * 最大校验码
     */
    private int maxCode = 0;

    /**
     * 开始时间，默认为2019-01-01
     */
    private final String startTimeString = "2019-01-01 00:00:00";

    private static final long MAX_ID = 9999999999999L;

    private static final long MIN_ID = 1000000000000L;

    /**
     * 起始时间戳
     */
    private long startTimeStamp = 0L;

    public ShortCardIdGenerator() {
        this(1);
    }

    public ShortCardIdGenerator(int machineId) {
        int maxMachineId = ~(-1 << machineBits);
        if (machineId > maxMachineId || machineId < 1) {
            throw new IllegalArgumentException("Machine id should be between 1 and " + maxMachineId);
        }

        this.machineId = machineId;
        init();
    }

    /**
     * 根据给定的系统编号生成卡号
     *
     * @return 13位卡号
     */
    public synchronized long generate() {
        long curStamp = getCurrentSecond();
        if (curStamp < lastStamp) {
            throw new IllegalArgumentException("Clock moved backwards. Refusing to generate id");
        }

        if (curStamp == lastStamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0L) {
                curStamp = getNextSecond();
            }
        } else {
            sequence = randomSequence();
        }
        lastStamp = curStamp;
        long originId = machineId << machineOffset
                | (curStamp - startTimeStamp) << timeOffset
                | sequence << sequenceOffset;

        int validationCode = IdUtils.getValidationCode(originId, maxCode);
        return originId + validationCode;
    }

    /**
     * 校验卡号是否合法
     *
     * @param id 卡号
     * @return boolean 合法返回true，反之false
     */
    public boolean validate(long id) {
        if (id > MAX_ID || id < MIN_ID) {
            return false;
        }

        return validateCode(id, startTimeStamp, timeBits, timeOffset, validationBits, maxCode);
    }

    /**
     * 解析卡号
     *
     * @param id 卡号
     * @return 解析结果依次是时间戳、机器编码、序列号
     */
    public Long[] parse(long id) {
        if (!validate(id)) {
            return null;
        }

        String bitString = Long.toBinaryString(id);
        int bitLength = bitString.length();
        long timestamp = Long.parseLong(bitString.substring(bitLength - timeOffset - timeBits, bitLength - timeOffset),
                2);
        long machineId = Long.parseLong(bitString.substring(0, bitLength - machineOffset), 2);
        long sequence = Long.parseLong(bitString.substring(bitLength - sequenceOffset - sequenceBits,
                bitLength - sequenceOffset), 2);
        return new Long[]{(timestamp + startTimeStamp) * 1000, machineId, sequence};
    }

    /**
     * 数据初始化
     */
    private void init() {
        sequenceOffset = validationBits;
        timeOffset = sequenceOffset + sequenceBits;
        machineOffset = timeOffset + timeBits;
        maxSequence = ~(-1L << sequenceBits);
        startTimeStamp = IdUtils.getTimeStampSecond(startTimeString);
        maxCode = ~(-1 << validationBits);
    }

    /**
     * 获取当前时间戳 单位秒
     *
     * @return 时间戳（秒）
     */
    private long getCurrentSecond() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 获取下一秒钟
     *
     * @return 时间戳（秒）
     */
    private long getNextSecond() {
        long second = getCurrentSecond();
        while (second <= lastStamp) {
            IdUtils.sleep(20);
            second = getCurrentSecond();
        }
        return second;
    }

}
