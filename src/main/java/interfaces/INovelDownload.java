package interfaces;

import configuration.Configuration;

public interface INovelDownload {

	/**
	 * ����С˵���������ص�D:/Lixian/Novel/
	 * @param url ��url��ָĳ��С˵���½��б�ҳ��
	 * @return
	 */
	public String Download(String url,Configuration config);
}
