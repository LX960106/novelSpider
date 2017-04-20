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
 * һ�������С˵�б�ץȡ���Ѿ�ʵ���˽���trԪ�صķ�������ʱû��ʵ�ֽӿ��еķ���
 * @author Administrator
 *
 */
public abstract class AbstractNovelSpider extends AbstractSpider implements INovelSpider {

	/** �ַ��� */
	private String charset;
	/** ��Ҫѡ���� */
	private String selector;
	/** ��һҳԪ�� */
	protected Element nextPageElement;
	/** ��һҳ��URL*/
	protected String nextPage;
	/**
	 * Ĭ�ϵ�ץȡ������Ĭ�ϻ�ץȡ3�ˣ���������
	 * @param url
	 * @return
	 * @throws Exception
	 */
	protected Elements getTr(String url) throws Exception{
		Elements trs = getTr(url,null);
		return trs;
	}
	
	/**
	 * ���ƶ�����ȥ������ҳ�����Ϊnull,Ĭ��3��
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
					throw new RuntimeException(NovelSiteEnum.getEnumByUrl(url).getUrl()+"Ŀǰ��֧�ָ���վ");
				}
				String result = super.crawl(url, charset);
				Document doc = Jsoup.parse(result);
				
				//�ж���û����һҳ���������һҳ��ֵurl
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
		throw new RuntimeException(url+"����"+maxTimes+"��ʧ��");
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
	 * һ����������ר�����ڶ�С˵��վ�б��ץȡ
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
