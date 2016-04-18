package com.abc;

import java.util.Calendar;
import java.util.Date;

public final class DateProvider {
	
	//easiest way to create a Singleton
    private static volatile DateProvider instance = new DateProvider();
	
	private DateProvider() {}
	
    public static DateProvider getInstance() {
        return instance;
    }

    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
