package impl;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import entities.Novel;
import interfaces.INovelSpider;
import util.NovelSiteEnum;
import util.NovelSpiderUtil;

/**
 * 一个抽象的小说列表抓取，已经实现了解析tr元素的方法，暂时没有实现接口中的方法
 * @author Administrator
 *
 */
public abstract class AbstractNovelSpider extends AbstractSpider implements INovelSpider {

	/** 字符集 */
	private String charset;
	/** 主要选择器 */
	private String selector;
	/** 下一页元素 */
	protected Element nextPageElement;
	/** 下一页的URL*/
	protected String nextPage;
	/**
	 * 默认的抓取方法，默认会抓取3此，尝试三次
	 * @param url
	 * @return
	 * @throws Exception
	 */
	protected Elements getTr(String url) throws Exception{
		Elements trs = getTr(url,null);
		return trs;
	}
	
	/**
	 * 以制定次数去解析网页，如果为null,默认3次
	 * @param url
	 * @param maxTimes
	 * @return
	 * @throws Exception
	 */
	protected Elements getTr(String url,Integer maxTimes) throws Exception{
		maxTimes = maxTimes== null?INovelSpider.MAX_TRY_TIMES:maxTimes;
		for(int i=0;i<maxTimes;i++){
			try{
				Map<String,String> context = NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl(url));
				charset = context.get("charset");
				selector = context.get("novel-selector");
				
				if(selector==null){
					throw new RuntimeException(NovelSiteEnum.getEnumByUrl(url).getUrl()+"目前不支持该网站");
				}
				String result = super.crawl(url, charset);
				Document doc = Jsoup.parse(result);
				
				//判断有没有下一页，有则给下一页赋值url
				String nextPageSelector = context.get("novel-nextPage-selector");
				if(nextPageSelector!=null){
					Elements nextPageElements = doc.select(nextPageSelector);
					nextPageElement = nextPageElements==null?null:nextPageElements.first();
					if(nextPageElement !=null){
						nextPage = nextPageElement.absUrl("href");
					}else{
						nextPage = "";
					}
				}
				doc.setBaseUri(url);
				Elements trs = doc.select(selector);
				return trs;
			}catch(Exception e){
				
			}
		}
		throw new RuntimeException(url+"尝试"+maxTimes+"次失败");
	}
	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		return ! nextPage.isEmpty();
	}

	@Override
	public String next() {
		// TODO Auto-generated method stub
		return nextPage;
	}
	@Override
	public Iterator<List<Novel>> iterator(String firstPage,int tryTimes) {
		// TODO Auto-generated method stub
		nextPage = firstPage;
		return new NovelIterator(tryTimes);
	}
	
	/**
	 * 一个迭代器，专门用于对小说网站列表的抓取
	 * @author Administrator
	 *
	 */
	private class NovelIterator implements Iterator<List<Novel>>{
		private int tryTimes;
		public NovelIterator(int times){
			this.tryTimes = times;
		}
		@Override
		public boolean hasNext() {
			// TODO Auto-generated method stub
			return AbstractNovelSpider.this.hasNext();
		}

		@Override
		public List<Novel> next() {
			// TODO Auto-generated method stub
			List<Novel> novel = getNovelList(nextPage,tryTimes);
			return novel;
		}
		
	}


}
