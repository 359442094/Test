package com.test.start.test.bloom;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;

/**
 * 布隆过滤器示例
 */
public class SimpleBloomFilter {
    private static final int DEFAULT_SIZE = 2 << 24;    // 布隆过滤器的比特长度
    private static final int[] seeds = new int[]{7, 11, 13, 31, 37, 61};    // 这里要选取质数，能很好的降低错误率

    private BitSet bits = new BitSet(DEFAULT_SIZE);
    private SimpleHash[] func = new SimpleHash[seeds.length];

    public static void main(String[] args) {
        BloomFilter<String> filter = BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()), 10000000, 0.01);
        List<String> texts = Arrays.asList("bloomFilter", "test", "ee");
        texts.stream().forEach(text -> {
            filter.put(text);
        });

        boolean mightContain1 = filter.mightContain("135");
        boolean mightContain2 = filter.mightContain("bloomFilter");

        System.out.println("mightContain1:" + mightContain1 + "\tmightContain2:" + mightContain2);

        /*String value = "crankzcool@gmail.com";
        SimpleBloomFilter filter = new SimpleBloomFilter();
        System.out.println(filter.contains(value));
        filter.add(value);
        System.out.println(filter.contains(value));*/
    }

    public SimpleBloomFilter() {
        for (int i = 0; i < seeds.length; i++) {
            func[i] = new SimpleHash(DEFAULT_SIZE, seeds[i]);
        }
    }

    public void add(String value) {
        for (SimpleHash f : func) {
            bits.set(f.hash(value), true);
        }
    }

    public boolean contains(String value) {
        if (value == null) {
            return false;
        }
        boolean ret = true;
        for (SimpleHash f : func) {
            ret = ret && bits.get(f.hash(value));
        }
        return ret;
    }

    public static class SimpleHash {
        private int cap;
        private int seed;

        public SimpleHash(int cap, int seed) {
            this.cap = cap;
            this.seed = seed;
        }

        public int hash(String value) {
            int result = 0;
            int len = value.length();
            for (int i = 0; i < len; i++) {
                result = seed * result + value.charAt(i);
            }
            return (cap - 1) & result;
        }
    }
}