package com.post;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.post.controller.HomeController;

public class AppTest {

	@Test
    public void testApp() {
		HomeController hc = new HomeController();
		String result = hc.home();
        assertEquals( result, "Welcome to user post!!!!!!!!" );
	}
}
