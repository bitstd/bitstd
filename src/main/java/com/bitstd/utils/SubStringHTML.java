package com.bitstd.utils;

public class SubStringHTML {
	public static boolean check(String str) {
		TagsList[] unclosedTags = getUnclosedTags(str);

		if (unclosedTags[0].size() != 0) {
			return false;
		}
		for (int i = 0; i < unclosedTags[1].size(); i++) {
			if (unclosedTags[1].get(i) != null)
				return false;
		}

		return true;
	}

	public static String fix(String str) {
		StringBuilder fixed = new StringBuilder();
		TagsList[] unclosedTags = getUnclosedTags(str);

		for (int i = unclosedTags[0].size() - 1; i > -1; i--) {
			fixed.append("<" + unclosedTags[0].get(i) + ">");
		}

		fixed.append(str);

		for (int i = unclosedTags[1].size() - 1; i > -1; i--) {
			String s = unclosedTags[1].get(i);
			if (s != null && !s.toLowerCase().equals("br") && !s.toLowerCase().equals("img")) {
				fixed.append("</" + s + ">");
			}
		}

		return fixed.toString();
	}

	private static TagsList[] getUnclosedTags(String str) {
		StringBuilder temp = new StringBuilder();
		TagsList[] unclosedTags = new TagsList[2];
		unclosedTags[0] = new TagsList();
		unclosedTags[1] = new TagsList();
		boolean flag = false;
		char currentJump = ' ';

		char current = ' ', last = ' ';

		for (int i = 0; i < str.length();) {
			current = str.charAt(i++);
			if (current == '"' || current == '\'') {
				flag = flag ? false : true;
				currentJump = current;
				if (flag) {
					while (i < str.length() && str.charAt(i++) != currentJump)
						;
					flag = false;
				}
			} else if (current == '<') {
				current = str.charAt(i++);
				if (current == '/') {
					current = str.charAt(i++);

					while (i < str.length() && current != '>') {
						temp.append(current);
						current = str.charAt(i++);
					}

					if (!unclosedTags[1].remove(temp.toString())) {
						unclosedTags[0].add(temp.toString());
					}
					temp.delete(0, temp.length());
				} else {
					last = current;
					while (i < str.length() && current != ' ' && current != ' ' && current != '>') {
						temp.append(current);
						last = current;
						current = str.charAt(i++);
					}

					while (i < str.length() && current != '>') {
						last = current;
						current = str.charAt(i++);
						if (current == '"' || current == '\'') {
							flag = flag ? false : true;
							currentJump = current;
							if (flag) {
								while (i < str.length() && str.charAt(i++) != currentJump)
									;
								current = str.charAt(i++);
								flag = false;
							}
						}
					}
					if (last != '/' && current == '>')
						unclosedTags[1].add(temp.toString());
					temp.delete(0, temp.length());
				}
			}
		}
		return unclosedTags;
	}

}
