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
 *  ���ʵ�ֶ��߳�����������վ��С˵
	1.�õ�����վ��ĳ��С˵�������½ڣ��½��б�
	2.ͨ�����㣬����Щ�½ڷ����ָ���������̣߳�������ȥ������Ȼ�󱣴浽�ı��ļ���
	3.֪ͨ���̣߳�����Щ��ɢ��С�ļ��ϲ���һ�����ļ��������Щ��Ƭ��С�ļ�ɾ������
 * @author Li Xian
 *
 */
public abstract class AbstractNovelDownload implements INovelDownload {

	@Override
	public String Download(String url,Configuration config) {
		// TODO Auto-generated method stub
		//��ȡʵ����
		IChapterSpider chapterSpider = ChapterSpiderFactory.getChapterSpider(url);
		List<Chapter> chapters = chapterSpider.getChapter(url);
		//������߳����غ��ˣ���ø������̡߳�
		//�����߳����غ��ˣ��ϲ���
		int size = config.getSize();
		int maxThreadSize = (int) Math.ceil(chapters.size()*1.0/size);
		Map<String,List<Chapter>> downloadTaskAlloc = new HashMap<>();
		for(int i=0;i<maxThreadSize;i++){
			int firstIndex = i*config.getSize();
			int lastIndex = (i==maxThreadSize-1)?chapters.size():(i+1)*config.getSize();
			downloadTaskAlloc.put(firstIndex+"-"+lastIndex, chapters.subList(firstIndex, lastIndex));
		}
		//�����̳߳�ִ��
		ExecutorService service = Executors.newFixedThreadPool(maxThreadSize);
		//Set<String> keys = downloadTaskAlloc.keySet();//����Ҳ���Ա���
		List<Future<String>> tasks = new ArrayList<>();
		
		//ͨ�������δ��룬�Ϳ��Դ���ȷʵ��·������С˵����ڶ�Ӧ��Ŀ¼�� //NovelSiteEnum.getEnumByUrl(url).getUrl()
		String savePath = config.getPath() +"/"+url;
		new File(savePath).mkdir();
		
		for(String key:downloadTaskAlloc.keySet()){
			tasks.add(service.submit(new DownloadCallable(savePath+"/"+key+".txt",downloadTaskAlloc.get(key),config.getTryTimes())));
		}
		service.shutdown();//�ȴ��߳�ִ����Ϻ���ȥ�ر��߳�
		for(Future<String> task:tasks){
			try {
				//task.get()��ȡ��ֵΪDownloadCallable��call��������ֵ
				System.out.println(task.get()+"-������ɣ�");
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
						System.err.println("���Ե�"+(i+1)+"������"+path+"ʧ��");
					}
				}
				
						
				
			}
			
		}catch(IOException e){
			throw new RuntimeException(e);
		}
		
		return path;
	}
	
}
