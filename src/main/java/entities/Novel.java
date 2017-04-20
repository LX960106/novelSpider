package entities;

import java.io.Serializable;
import java.util.Date;

public class Novel implements Serializable {
	private static final long serialVersionUID = 6805081031622185857L;
	/** ���� */
	private String name;
	/** ���� */
	private String author;
	/** ���� */
	private String url;
	/** ��� */
	private String type;
	/** �����½� */
	private String newestChapter;
	/** �����½����� */
	private String newestChapterUrl;
	/** ����ʱ�� */
	private Date updateTime;
	/** ״̬ 1:���� 2:��� 3:��β */
	private int status;
	/** ��������ĸ */
	private char fistLetter;
	
	/** С˵ƽ̨id */
	private int platformId;
	
	/** �ı�С˵�洢���������ݿ��ʱ�� */
	private Date addTime;
	public Date getAddTime() {
		return addTime;
	}
	public void setAddTime(Date addTime) {
		this.addTime = addTime;
	}
	public int getplatformId() {
		return platformId;
	}
	public void setplatformId(int platformId) {
		this.platformId = platformId;
	}
	public char getFistLetter() {
		return fistLetter;
	}
	public void setFistLetter(char fistLetter) {
		this.fistLetter = fistLetter;
	}
	public Novel(){
		
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getNewestChapter() {
		return newestChapter;
	}
	public void setNewestChapter(String newestChapter) {
		this.newestChapter = newestChapter;
	}
	public String getNewestChapterUrl() {
		return newestChapterUrl;
	}
	public void setNewestChapterUrl(String newestChapterUrl) {
		this.newestChapterUrl = newestChapterUrl;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	@Override
	public String toString() {
		return "Novel [name=" + name + ", author=" + author + ", url=" + url + ", type=" + type + ", newestChapter="
				+ newestChapter + ", newestChapterUrl=" + newestChapterUrl + ", updateTime=" + updateTime + ", status="
				+ status + ", fistLetter=" + fistLetter + ", platformId=" + platformId + ", addTime=" + addTime + "]";
	}
	
}
