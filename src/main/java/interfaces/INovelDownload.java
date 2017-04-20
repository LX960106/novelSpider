package interfaces;

import configuration.Configuration;

public interface INovelDownload {

	/**
	 * 下载小说方法，下载到D:/Lixian/Novel/
	 * @param url 此url是指某本小说的章节列表页面
	 * @return
	 */
	public String Download(String url,Configuration config);
}
