package impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entities.Novel;
import util.NovelSiteEnum;
import util.NovelSpiderUtil;

public class KSZNovelSpider extends AbstractNovelSpider {

	@Override
	public List<Novel> getNovelList(String url,int tryTimes) {
		// TODO Auto-generated method stub
		List<Novel> novels = new ArrayList<>();
		try {
			Elements trs = super.getTr(url,tryTimes);
			int size=trs.size()-1;
			for(int i=1;i<size;i++){
				Element tr = trs.get(i);
				Elements tds = tr.getElementsByTag("td");
				Novel novel = new Novel();
				novel.setType(tds.first().text());
				novel.setName(tds.get(1).text());
				novel.setUrl(tds.get(1).getElementsByTag("a").first().absUrl("href"));
				novel.setNewestChapter(tds.get(2).text());
				novel.setNewestChapterUrl(tds.get(2).getElementsByTag("a").first().absUrl("href"));
				novel.setAuthor(tds.get(3).text());
				novel.setUpdateTime(NovelSpiderUtil.getDate(tds.get(4).text(), "MM-dd"));
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
