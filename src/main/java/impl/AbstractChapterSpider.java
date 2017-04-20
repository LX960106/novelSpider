package impl;

import java.util.ArrayList;
import java.util.List;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entities.Chapter;
import interfaces.IChapterSpider;
import util.NovelSiteEnum;
import util.NovelSpiderUtil;

public abstract class AbstractChapterSpider extends AbstractSpider implements IChapterSpider {
	
	private String charset;
	private String selectors;
	
	
	
	
	/**
	 * 获取章节列表
	 * @param url
	 * @return
	 * 
	 */
	public List<Chapter> getChapter(String url) {
		// TODO Auto-generated method stub
		charset = NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl(url)).get("charset");
		selectors = NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl(url)).get("chapter-list");
	    //System.out.println(charset+","+selectors);
		try {
			String result = crawl(url,charset);
			Document doc = Jsoup.parse(result);
			doc.setBaseUri(url);//设置基础路径
			//System.out.println(result);
			Elements as = doc.select(selectors);
			List<Chapter> chapters = new ArrayList<Chapter>();
			for(Element a:as){
				Chapter chapter = new Chapter();
				chapter.setTitle(a.text());
				chapter.setUrl(a.absUrl("href"));
				chapters.add(chapter);
				//System.out.println(a);
			}
			return chapters;
	    } catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			throw new RuntimeException(e);
		}
		
		//return null;
	}
	

}
