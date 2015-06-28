package com.herokuapp.obscurespire6277.photor.util.time;

import java.time.ZoneId;

import static java.time.ZoneId.SHORT_IDS;

public class TimeZones {

    public static final ZoneId ET = ZoneId.of(SHORT_IDS.get("EST"));
    public static final ZoneId PT = ZoneId.of(SHORT_IDS.get("PST"));

}
