package com.bitstd.utils;

import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class FarseerRegexer {

	private final Log log = LogFactory.getLog(FarseerRegexer.class);

	private String regStr;

	private String regExp;

	private String regFormat;

	private static String REGEX_PREFIX = "reg:/";

	Vector results = null;

	public FarseerRegexer(String regStr) {
		this.regStr = regStr;
		results = new Vector();
	}

	public static boolean isRegex(String regStr) {
		if (regStr == null)
			return false;

		if (!regStr.toLowerCase().trim().startsWith(REGEX_PREFIX))
			return false;

		return true;
	}

	public boolean regex(byte[] content) {
		boolean ret = false;
		if (content == null)
			return false;

		try {

			ret = regex(new String(content, "utf-8"));
		} catch (Throwable t) {
			ret = false;
		}
		return ret;

	}

	public boolean regex(String matchstr) throws Throwable {

		if (matchstr == null)
			return false;

		if (!isRegex(regStr)) {
			log.info("regStr fomrat invalid:" + regStr);
			return false;
		}

		StringBuffer str = new StringBuffer();
		char[] arrays = matchstr.toCharArray();
		for (int i = 0; i < arrays.length; i++) {
			if (arrays[i] != '\r' && arrays[i] != '\n')
				str.append(arrays[i]);
		}

		matchstr = str.toString();

		if (!initinput()) {
			log.info("int regstr error.");
			return false;
		}

		try {

			Pattern pattern = Pattern.compile(regExp, Pattern.CASE_INSENSITIVE);
			int readn = 0;
			int len = matchstr.length();

			while (len > 0) {
				Matcher matcher = pattern.matcher(matchstr);

				if (!matcher.find())
					break;

				if (matcher.groupCount() == 0) {
					break;
				}

				String result = format(matcher);
				results.add(result);
				readn = matcher.end();
				len -= readn;
				matchstr = matchstr.substring(readn);
			}

		} catch (Throwable e) {
			throw e;

		}

		return true;
	}

	public String[] getRegexResult() {
		int size = 0;

		if (results.toArray() != null) {
			size = results.size();
		}
		String[] resStrs = new String[size];

		for (int i = 0; i < results.size(); i++) {
			String res = (String) results.get(i);
			if (Tools.Html2Text(res) != null) {
				resStrs[i] = Tools.Html2Text(res);
			} else {
				return null;
			}

		}
		return resStrs;
	}

	public String[] getmoreRegexResult() {
		int size = 0;

		if (results.toArray() != null) {
			size = results.size();
		}
		String[] resStrs = new String[size];

		for (int i = 0; i < results.size(); i++) {
			String res = (String) results.get(i);
			if (Tools.Html2Text(res) != null) {
				resStrs[i] = Tools.Html2Text(res);
			} else {
				resStrs[i] = " ";
			}

		}
		return resStrs;
	}

	public String[] getRegexLabel() {
		int size = 0;
		if (results.toArray() != null) {
			size = results.size();
		}
		String[] resStrs = new String[size];
		for (int i = 0; i < results.size(); i++) {
			String res = (String) results.get(i);
			try {
				res = Tools.filterBadCode(SubStringHTML.fix(res));
				if (res != null) {
					resStrs[i] = res;
				} else {
					break;
				}
			} catch (Exception ex) {
				ex.getMessage();
			}

		}
		return resStrs;
	}

	public String[] getRegexLabelnofix() {
		int size = 0;
		if (results.toArray() != null) {
			size = results.size();
		}
		String[] resStrs = new String[size];
		for (int i = 0; i < results.size(); i++) {
			String res = (String) results.get(i);
			if (res != null) {
				resStrs[i] = res;
			} else {
				return null;
			}
		}
		return resStrs;
	}

	public String[] getRegexLabel(String sitename) {
		if (sitename.equalsIgnoreCase("www.1gou.com")) {
			sitename = "www.zhongyu.com";
		}
		int size = 0;
		if (results.toArray() != null) {
			size = results.size();
		}
		String[] resStrs = new String[size];
		for (int i = 0; i < results.size(); i++) {
			String res = (String) results.get(i);
			try {
				res = Tools.filterBadCode(SubStringHTML.fix(res));
			} catch (Exception ex) {
				ex.getMessage();
				return null;
			}
			if (res != null) {
				res = Tools.replaceallString(res, "<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>",
						"");
				res = Tools.replaceallString(res, "<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>", "");
				res = Tools.replaceallString(res, "<[\\s]*?iframe[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?iframe[\\s]*?>",
						"");
				res = Tools.replaceallString(res, "<[\\s]*?script[^>]*?>", "");
				res = Tools.replaceallString(res, "<[\\s]*?style[^>]*?>", "");
				res = Tools.replaceallString(res, "<[\\s]*?iframe[^>]*?>", "");
				res = Tools.replaceallString(res, "<[\\s]*?SCRIPT[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?SCRIPT[\\s]*?>",
						"");
				res = Tools.replaceallString(res, "<[\\s]*?STYLE[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?STYLE[\\s]*?>", "");
				res = Tools.replaceallString(res, "<[\\s]*?IFRAME[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?IFRAME[\\s]*?>",
						"");
				res = Tools.replaceallString(res, "<[\\s]*?SCRIPT[^>]*?>", "");
				res = Tools.replaceallString(res, "<[\\s]*?STYLE[^>]*?>", "");
				res = Tools.replaceallString(res, "<[\\s]*?IFRAME[^>]*?>", "");
				res = Tools.replaceallString(res, "<div.*?>", "");
				res = Tools.replaceallString(res, "<\\/div>", "");
				res = Tools.replaceallString(res, "<DIV.*?>", "");
				res = Tools.replaceallString(res, "<\\/DIV>", "");
				res = Tools.replaceallString(res, "<a.*?>", "");
				res = Tools.replaceallString(res, "<\\/a>", "");
				res = Tools.replaceallString(res, "<A.*?>", "");
				res = Tools.replaceallString(res, "<\\/A>", "");
				res = Tools.replaceallString(res, "<table.*?>", "<table>");
				res = Tools.replaceallString(res, "<\\/table>", "</table>");
				res = Tools.replaceallString(res, "<TABLE.*?>", "<TABLE>");
				res = Tools.replaceallString(res, "<\\/TABLE>", "</TABLE>");
				res = Tools.replaceallString(res, "style=\".*?\"", "");
				res = Tools.replaceallString(res, "style=\\w+", "");
				res = Tools.replaceallString(res, "STYLE=\".*?\"", "");
				res = Tools.replaceallString(res, "STYLE=\\w+", "");
				res = Tools.replaceallString(res, "border=\".*?\"", "");
				res = Tools.replaceallString(res, "border=\\w+", "");
				res = Tools.replaceallString(res, "BORDER=\".*?\"", "");
				res = Tools.replaceallString(res, "BORDER=\\w+", "");
				res = Tools.replaceallString(res, "width=\".*?\"", "");
				res = Tools.replaceallString(res, "width=\\w+", "");
				res = Tools.replaceallString(res, "WIDTH=\".*?\"", "");
				res = Tools.replaceallString(res, "WIDTH=\\w+", "");
				res = Tools.replaceallString(res, "height=\".*?\"", "");
				res = Tools.replaceallString(res, "height=\\w+", "");
				res = Tools.replaceallString(res, "HEIGHT=\".*?\"", "");
				res = Tools.replaceallString(res, "HEIGHT=\\w+", "");
				res = Tools.replaceallString(res, "class=\".*?\"", "");
				res = Tools.replaceallString(res, "class=\\w+", "");
				res = Tools.replaceallString(res, "CLASS=\".*?\"", "");
				res = Tools.replaceallString(res, "CLASS=\\w+", "");
				res = Tools.replaceallString(res, "valign=\".*?\"", "");
				res = Tools.replaceallString(res, "valign=\\w+", "");
				res = Tools.replaceallString(res, "VALIGN=\".*?\"", "");
				res = Tools.replaceallString(res, "VALIGN=\\w+", "");
				res = Tools.replaceallString(res, "align=\".*?\"", "");
				res = Tools.replaceallString(res, "align=\\w+", "");
				res = Tools.replaceallString(res, "ALIGN=\".*?\"", "");
				res = Tools.replaceallString(res, "ALIGN=\\w+", "");
				res = Tools.replaceallString(res, "hspace=\".*?\"", "");
				res = Tools.replaceallString(res, "hspace=\\w+", "");
				res = Tools.replaceallString(res, "HSPACE=\".*?\"", "");
				res = Tools.replaceallString(res, "HSPACE=\\w+", "");
				res = Tools.replaceallString(res, "onload=\".*?\"", "");
				res = Tools.replaceallString(res, "onload=\\w+", "");
				res = Tools.replaceallString(res, "ONLOAD=\".*?\"", "");
				res = Tools.replaceallString(res, "ONLOAD=\\w+", "");
				res = Tools.replaceallString(res, "lazyload", "src");
				res = Tools.replaceallString(res, "LAZYLOAD", "src");
				res = Tools.replaceallString(res, "<img", "<img class=\"descimgsize\" ");
				res = Tools.replaceallString(res, "<IMG", "<img class=\"descimgsize\" ");
				res = Tools.replaceallString(res, "src\\d+", "src");
				res = Tools.replaceallString(res, "SRC\\d+", "src");
				res = Tools.replaceallString(res, "data-src", "src");
				res = Tools.replaceallString(res, "DATA-SRC", "src");
				res = Tools.replaceallString(res, "togj_src", "src");
				res = Tools.replaceallString(res, "TOGJ_SRC", "src");
				boolean flag = Tools.isHttpPath(res);
				if (flag == false) {
					res = res.replaceAll("src=\"http://(.*?)\\/", "src=\"/");
					res = res.replaceAll("src=\'http://(.*?)\\/", "src=\'/");
					res = res.replaceAll("src=\"(.*?)\\/", "src=\"http://" + sitename + "/");
					res = res.replaceAll("src=\'(.*?)\\/", "src=\'http://" + sitename + "/");
					res = res.replaceAll("SRC=\"http://(.*?)\\/", "src=\"/");
					res = res.replaceAll("SRC=\'http://(.*?)\\/", "src=\'/");
					res = res.replaceAll("SRC=\"(.*?)\\/", "src=\"http://" + sitename + "/");
					res = res.replaceAll("SRC=\'(.*?)\\/", "src=\'http://" + sitename + "/");
				}
				resStrs[i] = res;
			} else {
				return null;
			}
		}
		return resStrs;
	}

	private boolean initinput() {
		regStr = regStr.trim();

		int start = regStr.indexOf(REGEX_PREFIX) + REGEX_PREFIX.length();
		if (start > regStr.length())
			return false;

		char[] chars = regStr.toCharArray();
		int end = 0;

		for (int i = start; i < chars.length; i++) {
			if (chars[i] == '/' && (i - 1) >= 0) {
				if (chars[i - 1] != '\\') {
					end = i;
					break;
				}
			}
		}

		if (end != 0) {
			regExp = regStr.substring(start, end);
			regFormat = regStr.substring(end + 1);
		} else {
			regExp = regStr.substring(start);
			regFormat = "";
		}

		return true;
	}

	public String getRegExp() {
		return this.regExp;
	}

	public String getRegFormat() {
		return this.regFormat;
	}

	private String format(Matcher matcher) {

		int groupCount = matcher.groupCount();

		if (groupCount == 0)
			return "";

		if (regFormat == null || regFormat.trim().length() == 0) {
			String result = matcher.group(0);
			return result;
		} else {
			String result = regFormat;
			for (int i = groupCount; i >= 0; i--) {
				String key = "$" + i;
				result = Tools.replaceString(result, key, matcher.group(i));

			}
			return result;
		}

	}

}
