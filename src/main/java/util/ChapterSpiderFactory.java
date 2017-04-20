package util;

import impl.SortChapterSpider;
import interfaces.IChapterSpider;
/**
 * 通过给定的URL,给出IChapterSpider的具体实现类
 * @author Li Xian
 *
 */
public final class ChapterSpiderFactory {

	private ChapterSpiderFactory(){};
	public static IChapterSpider getChapterSpider(String url){
		IChapterSpider chapterSpider = null;
		NovelSiteEnum novelsiteenum = NovelSiteEnum.getEnumByUrl(url);
		switch(novelsiteenum){
		case DingDianXiaoShuo:
		case BiQuGe:
		case BiXiaWenXue:
		case KanShuZhong:
			chapterSpider = new SortChapterSpider();
		}
		return chapterSpider;
	}
}
