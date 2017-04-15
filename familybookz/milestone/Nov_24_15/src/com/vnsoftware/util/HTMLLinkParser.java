package com.vnsoftware.util;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.htmlparser.jericho.HTMLElementName;
import net.htmlparser.jericho.Source;
import net.htmlparser.jericho.StartTag;

import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.SimpleNodeIterator;

public class HTMLLinkParser {
	public static void main(String[] args) throws Exception {
/*		LinkInfo l = HTMLLinkParser.parseLink("http://dantri.com.vn/xa-hoi/nguoi-khong-tham-nhung-co-khi-bi-nguoi-tham-nhung-co-lap-20151028200619614.htm");
		System.out.println(l.getTitle());
		System.out.println(l.getDesc());
		System.out.println(l.getImage());
		System.out.println(l.getWebsite());
		System.out.println(l.getUrl());*/
//		System.out.println(HTMLLinkParser.getHttpLinkInText("http://news.zing.vn/Viet-Nam-ra-tuyen-bo-ve-viec-tau-My-tuan-tra-o-Bien-Dong-post594916.html".toLowerCase()));
		System.out.println(System.getProperty("os.name"));
	}

	public static LinkInfo parseLink(String url) throws Exception, IOException{
		LinkInfo info = new LinkInfo();
		Parser p = new Parser(url);
		 SimpleNodeIterator n = p.parse(new TagNameFilter("meta")).elements();
		StringBuilder content = new StringBuilder();
		while(n.hasMoreNodes()){
			content.append(n.nextNode().toHtml());
		}
		Source s = new Source(content);
		List<StartTag> metas = s.getAllStartTags(HTMLElementName.META);
		String value;
		for(StartTag t : metas){
			value = t.getAttributeValue("property");
			if("og:description".equals(value)){
				info.setDesc(t.getAttributeValue("content"));
			}else if("og:image".equals(value)){
				info.setImage( t.getAttributeValue("content"));
			}else if("og:url".equals(value)){
				info.setUrl(t.getAttributeValue("content"));
			}else if("og:title".equals(value)){
				info.setTitle(t.getAttributeValue("content"));
			}else if("og:site_name".equals(value)){
				info.setWebsite(t.getAttributeValue("content"));
			}
		}
		return info;
	}
	public static String getHttpLinkInText(String text){
		String url = "";
		if(text != null && text.indexOf("https") >= 0){
			int lastIndex = text.indexOf(" ",text.indexOf("https"));
			if(lastIndex <= 0)
				lastIndex = text.length();
			url = text.substring(text.indexOf("https"), lastIndex);
		}else if(text != null && text.indexOf("http") >= 0){
			int lastIndex = text.indexOf(" ",text.indexOf("http"));
			if(lastIndex <= 0)
				lastIndex = text.length();
			url = text.substring(text.indexOf("http"),lastIndex);
		}
		if(url != "" && isUrl(url.toLowerCase()))
			return url;
		else
			return "";
	}
	private static Pattern p = Pattern.compile("^((https{0,1}|ftp|rtsp|mms){0,1}://){0,1}(([0-9a-z_!~\\*'\\(\\)\\.&=\\+\\$%\\-]{1,}:\\ ){0,1}[0-9a-z_!~\\*'\\(\\)\\.&=\\+\\$%\\-]{1,}@){0,1}(([0-9]{1,3}\\.){3,3}[0-9]{1,3}|([0-9a-z_!~\\*'\\(\\)\\-]{1,}\\.){0,}([0-9a-z][0-9a-z\\-]{0,61}){0,1}[0-9a-z]\\.[a-z]{2,6}|localhost)(:[0-9]{1,4}){0,1}((/{0,1})|(/[0-9a-z_!~\\*'\\(\\)\\.;\\?:@&=\\+\\$,%#\\-]{1,}){1,}/{0,1})$");
	private static boolean isUrl(String url) {
		Matcher m = p.matcher(url);
		return m.find();
	}
	class HtmlNode {
		private String text;
		private String link;

		public HtmlNode() {
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getLink() {
			return link;
		}

		public void setLink(String link) {
			this.link = link;
		}
	}
}
