package impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entities.Novel;
import util.NovelSiteEnum;
import util.NovelSpiderUtil;

/**
 * 笔下文学网站的图书列表爬取
 * @author Administrator
 *
 */
public class BXWXNovelSpider extends AbstractNovelSpider {

	@Override
	public List<Novel> getNovelList(String url,int tryTimes) {
		// TODO Auto-generated method stub
		List<Novel> novels = new ArrayList<>();
		try {
			Elements trs = super.getTr(url,tryTimes);
			for(int i=1,size=trs.size();i<size;i++){
				Element tr = trs.get(i);
				Elements tds = tr.getElementsByTag("td");
				Novel novel = new Novel();
				novel.setName(tds.first().text());
				novel.setUrl(tds.first().getElementsByTag("a").first().absUrl("href")
						.replace(".htm", "/").replace("binfo", "b"));
				novel.setNewestChapter(tds.get(1).text());
				novel.setNewestChapterUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
				novel.setAuthor(tds.get(2).text());
				novel.setUpdateTime(NovelSpiderUtil.getDate(tds.get(4).text(), "yy-MM-dd"));
				novel.setStatus(NovelSpiderUtil.getNovelStatus(tds.get(5).text()));
				novel.setplatformId(NovelSiteEnum.getEnumByUrl(url).getId());
				novels.add(novel);
			}
			
			return novels;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

}
