package interfaces;

import entities.ChapterDetail;

public interface IChapterDetailSpider {

	/**
	 * 给我一个url我就返回章节详细内容
	 * @param url
	 * @return
	 */
	public ChapterDetail getChapterDetail(String url);
}
