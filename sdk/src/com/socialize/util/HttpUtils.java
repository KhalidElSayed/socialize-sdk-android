/*
 * Copyright (c) 2011 Socialize Inc.
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.socialize.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import android.util.Log;

/**
 * @author Jason Polites
 *
 */
public final class HttpUtils {
	
	static final Map<Integer, String> httpStatusCodes = new HashMap<Integer, String>();
	
	static {
		InputStream in = null;
		try {
			in = Thread.currentThread().getContextClassLoader().getResourceAsStream("errors.properties");
			Properties props = new Properties();
			props.load(in);
			
			Set<Object> keys = props.keySet();
			
			for (Object object : keys) {
				try {
					int code = Integer.parseInt(object.toString());
					String msg = props.getProperty(object.toString());
					httpStatusCodes.put(code, msg);
				}
				catch (NumberFormatException e) {
					Log.w(HttpUtils.class.getSimpleName(), object.toString() + " is not an integer");
				}
			}
		}
		catch (Exception e) {
			Log.e(HttpUtils.class.getSimpleName(), "Failed to load error codes", e);
		}
		finally {
			if(in != null) {
				try {
					in.close();
				}
				catch (IOException ignore) {}
			}
		}
		
	}

	public static final boolean isHttpError(int code) {
		return (code < 200 || code >= 300);
	}
	
	public static final String getMessageFor(int code) {
		return httpStatusCodes.get(code);
	}
}