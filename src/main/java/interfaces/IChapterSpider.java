package interfaces;


import java.util.List;

import entities.Chapter;
public interface IChapterSpider {

	/**
	 * 给我们一个完整的url,我们就给你返回一个章节列表
	 * @param url
	 * @return
	 */
	public List<Chapter> getChapter(String url);
	
}
