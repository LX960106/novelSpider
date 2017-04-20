package impl;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import entities.ChapterDetail;
import interfaces.IChapterDetailSpider;
import util.NovelSiteEnum;
import util.NovelSpiderUtil;

public abstract class AbstractChapterDetailSpider extends AbstractSpider implements IChapterDetailSpider {

	private String charset;
	private String title;
	private String content;
	private String pre;
	private String next;
	@Override
	public ChapterDetail getChapterDetail(String url) {
		// TODO Auto-generated method stub
		
		try {
			Map<String,String> context = NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl(url));
			ChapterDetail chapterDetail = new ChapterDetail();
			String splits[] = new String[2];
			charset = context.get("charset");
			title = context.get("chapter-detail-title");
			content = context.get("chapter-detail-content");
			pre = context.get("chapter-detail-pre");
			next = context.get("chapter-detail-next");
			
			String result = super.crawl(url, charset);//抓取调用，需要异常处理
			
			result = result.replace("&nbsp;","  ").replace("<br/>", "${li}").replace("<br />", "${li}");
			//System.out.println(result);
			Document doc = Jsoup.parse(result);
			doc.setBaseUri(url);//设置基础路径
			//标题
			splits = parseSelectors(title);
			chapterDetail.setTitle(doc.select(splits[0]).get(Integer.parseInt(splits[1])).text());
			//内容
			splits = parseSelectors(content);
			//System.out.println(splits[0]+","+splits[1]);
			chapterDetail.setContent(doc.select(splits[0])
					.get(Integer.parseInt(splits[1])).text()
					.replace("${li}", "\n"));
			
			//上一页
			splits = parseSelectors(pre);
			chapterDetail.setPre(doc.select(splits[0]).get(Integer.parseInt(splits[1])).absUrl("href"));
			//下一页
			splits = parseSelectors(next);
			//System.out.println(splits[0]+","+splits[1]);
			chapterDetail.setNext(doc.select(splits[0]).get(Integer.parseInt(splits[1])).absUrl("href"));
			return chapterDetail;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * 返回选择器与下表索引
	 * @param selector
	 * @return
	 */
	public String[] parseSelectors(String selector){
		String split[] = selector.split("\\,");
		String newsplit[] = new String[2];
		if(split.length==1){
			newsplit[0] = split[0];
			newsplit[1] = "0";
			return newsplit;
		}else{
			return split;
		}
		
	}
}
