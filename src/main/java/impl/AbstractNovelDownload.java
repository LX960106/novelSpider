package impl;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import configuration.Configuration;
import entities.Chapter;
import entities.ChapterDetail;
import interfaces.IChapterDetailSpider;
import interfaces.IChapterSpider;
import interfaces.INovelDownload;
import util.ChapterDetailSpiderFactory;
import util.ChapterSpiderFactory;
import util.NovelSiteEnum;
import util.NovelSpiderUtil;

/**
 *  如何实现多线程下载任意网站的小说
	1.拿到该网站的某本小说的所有章节：章节列表
	2.通过计算，将这些章节分配给指定数量的线程，让他们去解析，然后保存到文本文件中
	3.通知主线程，将这些零散的小文件合并成一个大文件。最后将那些分片的小文件删除掉。
 * @author Li Xian
 *
 */
public abstract class AbstractNovelDownload implements INovelDownload {

	@Override
	public String Download(String url,Configuration config) {
		// TODO Auto-generated method stub
		//获取实现类
		IChapterSpider chapterSpider = ChapterSpiderFactory.getChapterSpider(url);
		List<Chapter> chapters = chapterSpider.getChapter(url);
		//当多个线程下载好了，你得告诉主线程。
		//所有线程下载好了，合并。
		int size = config.getSize();
		int maxThreadSize = (int) Math.ceil(chapters.size()*1.0/size);
		Map<String,List<Chapter>> downloadTaskAlloc = new HashMap<>();
		for(int i=0;i<maxThreadSize;i++){
			int firstIndex = i*config.getSize();
			int lastIndex = (i==maxThreadSize-1)?chapters.size():(i+1)*config.getSize();
			downloadTaskAlloc.put(firstIndex+"-"+lastIndex, chapters.subList(firstIndex, lastIndex));
		}
		//定义线程池执行
		ExecutorService service = Executors.newFixedThreadPool(maxThreadSize);
		//Set<String> keys = downloadTaskAlloc.keySet();//这样也可以遍历
		List<Future<String>> tasks = new ArrayList<>();
		
		//通过这两段代码，就可以创建确实的路径，将小说存放在对应的目录下 //NovelSiteEnum.getEnumByUrl(url).getUrl()
		String savePath = config.getPath() +"/"+url;
		new File(savePath).mkdir();
		
		for(String key:downloadTaskAlloc.keySet()){
			tasks.add(service.submit(new DownloadCallable(savePath+"/"+key+".txt",downloadTaskAlloc.get(key),config.getTryTimes())));
		}
		service.shutdown();//等待线程执行完毕后，再去关闭线程
		for(Future<String> task:tasks){
			try {
				//task.get()获取的值为DownloadCallable的call方法返回值
				System.out.println(task.get()+"-下载完成！");
			} catch (InterruptedException | ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		NovelSpiderUtil.mutilFileMerge(savePath, "lixian.txt", true);
		return savePath;
	}

}
class DownloadCallable implements Callable<String>{

	public List<Chapter> chapters;
	public String path;
	public int tryTimes;
	public DownloadCallable(String path,List<Chapter> ch,int tryTimes){
		this.chapters = ch;
		this.path = path;
		this.tryTimes = tryTimes;
	}
	@Override
	public String call() throws Exception {
		// TODO Auto-generated method stub
		try(
				PrintWriter printout = new PrintWriter(new File(path));
				){
			for(Chapter chapter:chapters){
				IChapterDetailSpider chapterDetailSpider = ChapterDetailSpiderFactory.getChapterDetailSpider(chapter.getUrl());
				ChapterDetail chapterDetail = null;
				for(int i =0;i<tryTimes;i++){
					try{
						chapterDetail = chapterDetailSpider.getChapterDetail(chapter.getUrl());
						printout.println(chapterDetail.getTitle());
						printout.println(chapterDetail.getContent());
						break;
					}catch(RuntimeException e){
						System.err.println("尝试第"+(i+1)+"次下载"+path+"失败");
					}
				}
				
						
				
			}
			
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		
		return path;
	}
	
}
