package util;

import impl.DefaultChapterDetailSpider;
import impl.SortChapterSpider;
import interfaces.IChapterDetailSpider;
import interfaces.IChapterSpider;
/**
 * 通过给定的URL,给出IChapterDetailSpider的具体实现类
 * @author Li Xian
 *
 */
public final class ChapterDetailSpiderFactory {
	private ChapterDetailSpiderFactory(){};
	public static IChapterDetailSpider getChapterDetailSpider(String url){
		IChapterDetailSpider chapterDetailSpider = null;
		NovelSiteEnum novelsiteenum = NovelSiteEnum.getEnumByUrl(url);
		switch(novelsiteenum){
		case DingDianXiaoShuo:
		case BiQuGe:
		case BiXiaWenXue:
		case KanShuZhong:
			chapterDetailSpider = new DefaultChapterDetailSpider();
		}
		return chapterDetailSpider;
	}
}
