package com.sharom.wrm.utils;

import com.github.f4b6a3.tsid.TsidCreator;

public class TsidGenerator {

    private TsidGenerator() {}

    public static String next() {
        return TsidCreator.getTsid().toString();
    }
}
