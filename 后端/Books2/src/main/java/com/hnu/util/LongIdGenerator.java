package com.hnu.util;

import java.util.Random;

/**
 * @Author hj
 * @Date 2023/12/4 15:24
 * @PackageName:com.hnu.util
 * @ClassName: LongIdGenerator
 * @Description: create a class
 * @Version 1.0
 */
@SuppressWarnings("FieldCanBeLocal")
public class LongIdGenerator {

    /**
     * 起始的时间戳, 2014-01-01 00:00:00，为了统一为19位，起始时间戳不能太近，否则ID就会为18位
     */
    private final long startStamp = IdUtils.getTimeStampMill("2014-01-01 00:00:00");

    /**
     * 序列号占用的位数
     */
    private final long sequenceBit = 12;
    /**
     * 机器标识占用的位数
     */
    private final long machineBit = 10;

    /**
     * 每一部分的最大值
     */
    private final long maxSequence = ~(-1L << sequenceBit);

    /**
     * 每一部分向左的位移
     */
    private final long machineLeft = sequenceBit;
    private final long timestampLeft = sequenceBit + machineBit;

    /**
     * 机器标识
     */
    private final long machineId;

    /**
     * 序列号,12 bit序列号支持1毫秒产生4096个自增序列id
     */
    private long sequence = 0L;

    /**
     * 上一次时间戳
     */
    private long lastStamp = -1L;

    private final Random random = new Random();

    /**
     * 最小ID 19位
     */
    private static final long MIN_ID = 1000000000000000000L;

    public LongIdGenerator(Long machineId) {
        this.machineId = machineId;
    }

    /**
     * 生成ID
     *
     * @return 返回Long ID
     */
    public synchronized Long generate() {
        long curStamp = getCurrentMill();
        if (curStamp < lastStamp) {
            throw new IllegalArgumentException("Clock moved backwards. Refusing to generate id");
        }

        if (curStamp == lastStamp) {
            //相同毫秒内，序列号自增
            sequence = (sequence + 1) & maxSequence;
            //同一毫秒的序列数已经达到最大
            if (sequence == 0L) {
                curStamp = getNextMill();
            }
        } else {
            //不同毫秒内，序列号置为16以内的随机数，方便根据尾号hash
            sequence = random.nextInt(16);
        }

        lastStamp = curStamp;
        return (curStamp - startStamp) << timestampLeft | machineId << machineLeft | sequence;
    }

    /**
     * 解析id
     *
     * @param id long类型ID
     * @return 解析结果依次是时间戳、机器编码、序列号
     */
    public Long[] parse(long id) {
        if (id < MIN_ID) {
            return null;
        }

        long timestamp = id >> timestampLeft;
        String bitString = Long.toBinaryString(id >> machineLeft);
        long machineId = Long.parseLong(bitString.substring(bitString.length() - 10), 2);
        bitString = Long.toBinaryString(id);
        long sequence = Long.parseLong(bitString.substring(53), 2);
        return new Long[]{startStamp + timestamp, machineId, sequence};
    }

    /**
     * 获取下一毫秒
     *
     * @return 下一毫秒
     */
    private long getNextMill() {
        long mill = getCurrentMill();
        while (mill <= lastStamp) {
            mill = getCurrentMill();
        }
        return mill;
    }

    /**
     * 获取当前时间戳
     *
     * @return 时间戳
     */
    private long getCurrentMill() {
        return System.currentTimeMillis();
    }
}
