package com.test.start.test.maxFileDownload;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 * 文件下载管理类
 */
@Slf4j
public class DownLoadManager{
	private static final Logger LOGGER = LoggerFactory.getLogger(DownLoadManager.class);
	/**
	 * 每个线程下载的字节数
	 */
	private long unitSize = 1000 * 1024;

	private ExecutorService taskExecutor = Executors.newFixedThreadPool(10);
	
	private CloseableHttpClient httpClient;

	public DownLoadManager() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(100);
		httpClient = HttpClients.custom().setConnectionManager(cm).build();
	}

	public static void main(String[] args) throws IOException {
		new DownLoadManager().doDownload();
	}

	/**
	 * 启动多个线程下载文件
	 */
	public void doDownload() throws IOException {
		//要下载的url
		String remoteFileUrl = "http://94.191.62.87:81/file/OCR.pdf";
		String fileName = "C:\\phpstudy_pro\\WWW\\test.pdf";
		System.out.println("本地文件名称：" + fileName);
		long fileSize = this.getRemoteFileSize(remoteFileUrl);
		this.createFile(fileName, fileSize);
		Long threadCount = (fileSize / unitSize) + (fileSize % unitSize != 0 ? 1 : 0);
		long offset = 0;
		
		CountDownLatch end = new CountDownLatch(threadCount.intValue());
		// 如果远程文件尺寸小于等于unitSize
		if (fileSize <= unitSize) {
			DownloadThreadTask downloadThread = new DownloadThreadTask(remoteFileUrl, fileName, offset, fileSize, end, httpClient);
			taskExecutor.execute(downloadThread);
		} else {// 如果远程文件尺寸大于unitSize
			for (int i = 1; i < threadCount; i++) {
				DownloadThreadTask downloadThread = new DownloadThreadTask(remoteFileUrl,  fileName, offset, unitSize, end, httpClient);
				taskExecutor.execute(downloadThread);
				offset = offset + unitSize;
			}
			if (fileSize % unitSize != 0) {// 如果不能整除，则需要再创建一个线程下载剩余字节
				DownloadThreadTask downloadThread = new DownloadThreadTask(remoteFileUrl,  fileName, offset, fileSize - unitSize * (threadCount - 1), end, httpClient);
				taskExecutor.execute(downloadThread);
			}
		}
		try {
			end.await();
		} catch (InterruptedException e) {
			LOGGER.error("DownLoadManager exception msg:{}", ExceptionUtils.getFullStackTrace(e));
			e.printStackTrace();
		}
		taskExecutor.shutdown();
		LOGGER.debug("下载完成！{} ", fileName);
	}

	/**
	 * 获取远程文件尺寸
	 */
	private long getRemoteFileSize(String remoteFileUrl) throws IOException {
		long fileSize = 0;
		HttpURLConnection httpConnection = (HttpURLConnection) new URL(remoteFileUrl).openConnection();
		//使用HEAD方法
		httpConnection.setRequestMethod("HEAD");
		int responseCode = httpConnection.getResponseCode();
		if (responseCode >= 400) {
			LOGGER.debug("Web服务器响应错误!");
			return 0;
		}
		String sHeader;
		for (int i = 1;; i++) {
			sHeader = httpConnection.getHeaderFieldKey(i);
			if (sHeader != null && sHeader.equals("Content-Length")) {
				fileSize = Long.parseLong(httpConnection.getHeaderField(sHeader));
				log.info("文件大小:"+fileSize);
				break;
			}
		}
		return fileSize;
	}

	/**
	 * 创建指定大小的文件
	 */
	private void createFile(String fileName, long fileSize) throws IOException {
		File newFile = new File(fileName);
		RandomAccessFile raf = new RandomAccessFile(newFile, "rw");
		raf.setLength(fileSize);
		raf.close();
	}
}