package interfaces;

import java.util.Iterator;
import java.util.List;

import entities.Novel;

/**
 * 爬去某个站点的小说列表
 * @author Li Xian
 *
 */
public interface INovelSpider {

	public static final int MAX_TRY_TIMES = 3;
	/**
	 * 给我一个URL,我就给你一堆的小说实体
	 * @param url
	 * @param tryTimes 允许失败重试的次数
	 * @return
	 */
	public List<Novel> getNovelList(String url,int tryTimes);
	
	public boolean hasNext();
	public String next();
	/**
	 * 迭代器，迭代所有书籍列表
	 * @param firstPage
	 * @param tryTimes 尝试失败的次数
	 * @return
	 */
	public Iterator<List<Novel>> iterator(String firstPage,int tryTimes);
}
