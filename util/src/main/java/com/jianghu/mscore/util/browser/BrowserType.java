/*
* Copyright (c) 2008-2018, Harald Walker (bitwalker.eu) and contributing developers  
* All rights reserved.
* 
* Redistribution and use in source and binary forms, with or
* without modification, are permitted provided that the
* following conditions are met:
* 
* * Redistributions of source code must retain the above
* copyright notice, this list of conditions and the following
* disclaimer.
* 
* * Redistributions in binary form must reproduce the above
* copyright notice, this list of conditions and the following
* disclaimer in the documentation and/or other materials
* provided with the distribution.
* 
* * Neither the name of bitwalker nor the names of its
* contributors may be used to endorse or promote products
* derived from this software without specific prior written
* permission.
* 
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND
* CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES,
* INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
* MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
* NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
* HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
* CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR
* OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
* SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/

package com.jianghu.mscore.util.browser;

import com.jianghu.mscore.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Enum constants classifying the different types of browsers which are common in user-agent strings
 * @author harald
 *
 */
public enum BrowserType {

	/**
	 * Standard web-browser
	 */
	WEB_BROWSER("电脑"),
	/**
	 * Special web-browser for mobile devices
	 */
	MOBILE_BROWSER("手机"),
//	/**
//	 * Text only browser like the good old Lynx
//	 */
//	TEXT_BROWSER("Browser (text only)"),
//	/**
//	 * Email client like Thunderbird
//	 */
//	EMAIL_CLIENT("Email Client"),
	/**
	 * Search robot, spider, crawler,...
	 */
	ROBOT("机器人"),
//	/**
//	 * Downloading tools
//	 */
//	TOOL("Downloading tool"),
//	/**
//	 * Application
//	 */
//	APP("Application"),
	UNKNOWN("未知");
	
	private String name;

	private static List<BrowserType> typeList;
	
	private BrowserType(String name) {
		this.name = name;
		addTypeList(this);
	}

	private static void addTypeList(BrowserType type) {
		if (typeList == null) {
			typeList = new ArrayList<>();
		}
		typeList.add(type);
	}

	public String getName() {
		return name;
	}

	public static String getTypeName(int id) {
		for (BrowserType type : typeList) {
			if (type.ordinal() == id) return type.getName();
		}
		return StringUtil.EMPTY;
	}

	public static List<BrowserType> getTypeList() {
		return typeList;
	}
}
