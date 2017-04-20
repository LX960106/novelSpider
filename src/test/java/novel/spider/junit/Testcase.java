package novel.spider.junit;

import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import configuration.Configuration;
import entities.Chapter;
import entities.ChapterDetail;
import entities.Novel;
import impl.AbstractNovelDownload;
import impl.BXWXNovelSpider;
import impl.DefaultChapteSpider;
import impl.DefaultChapterDetailSpider;
import impl.DefaultNovelDownload;
import impl.KSZNovelSpider;
import impl.SortChapterSpider;
import interfaces.IChapterDetailSpider;
import interfaces.IChapterSpider;
import interfaces.INovelDownload;
import interfaces.INovelSpider;
import util.ChapterSpiderFactory;
import util.NovelSiteEnum;
import util.NovelSpiderFactory;
import util.NovelSpiderUtil;

public class Testcase {

	@Test
	public void testGetChapters() throws Exception{
		String url = "http://www.kanshuzhong.com/book/103207/";
		IChapterSpider  spider = ChapterSpiderFactory.getChapterSpider(url);
		List<Chapter> chapters = spider.getChapter(url);
		for(Chapter chapter:chapters){
			System.out.println(chapter.toString());
		}
	}   
	
	@Test
	public void testGetSiteContext(){
		System.out.println(NovelSpiderUtil.getContext(NovelSiteEnum.getEnumByUrl("http://www.bxwx9.org/b/62/62724/index.html")));
	}
	
	@Test
	public void testGetChapterDetail() throws Exception{
		IChapterDetailSpider spider = new DefaultChapterDetailSpider();
		ChapterDetail chDe = spider.getChapterDetail("http://www.kanshuzhong.com/book/103207/18478144.html");
		System.out.println(chDe.toString());
	}
	//http://www.bxwx9.org/b/62/62724/11455540.html
	@Test
	public void downloadChapter(){
		INovelDownload noveldownload = new DefaultNovelDownload();
		Configuration config = new Configuration();
		config.setPath("D:/novel");
		config.setSize(50);
		String str = noveldownload.Download("http://www.23us.com/html/42/42377/", config);
		System.out.println("下载完成!\n"+"快去此路径看看吧!"+str);
	}
	
	@Test
	public void testFiles(){
		NovelSpiderUtil.mutilFileMerge("D:/novel", "PerfectWorld.txt",false);
	}
	
	@Test
	public void testNovelList(){
		String url = "http://www.bxwx9.org/binitialA/0/1.htm";
		INovelSpider novelSpider = NovelSpiderFactory.getNovelSpider(url);
		List<Novel> novels = novelSpider.getNovelList(url,3);
		for(Novel novel:novels){
			System.out.println(novel.toString());
		}
	}
	
	@Test
	public void testKSZIterator(){
		String url = "http://www.bxwx9.org/binitial1/0/1.htm";
		INovelSpider novelSpider = NovelSpiderFactory.getNovelSpider(url);
		//List<Novel> novels = novelSpider.getNovelList(url);
		Iterator<List<Novel>> iterator = novelSpider.iterator(url,20);
		while(iterator.hasNext()){
			List<Novel> novels = iterator.next();
			
			//System.out.println(novelSpider.hasNext());
			System.out.println(novelSpider.next());
		}
		
	}
	
}
