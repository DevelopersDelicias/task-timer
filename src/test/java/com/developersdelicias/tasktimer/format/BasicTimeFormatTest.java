package com.developersdelicias.tasktimer.format;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

@RunWith(JUnitParamsRunner.class)
public class BasicTimeFormatTest {

    private BasicTimeFormat format;

    @Before
    public void setUp() {
        format = new BasicTimeFormat();
    }

    @Test
    @Parameters({"1000, 00:00:01", "2000, 00:00:02", "60000, 00:01:00", "3600000, 01:00:00"})
    public void should_format_using_hh_mm_ss_format(int milliseconds, String expected) {
        assertThat(format.format(milliseconds), is(expected));
    }
}
