/**
 * 
 */
package com.digitgroup.getContent;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * @author Moking
 *
 */
public class CollectMovie {
	private static final Logger log = Logger.getLogger(CollectMovie.class);
	private static String[] VIDEOEXT = {"mp4","avi","wmv","mkv","rmvb","rm","MP4","AVI","WMV"};
	/**
	 * 递归复制src文件夹中的视频到dest
	 * @param src
	 * @param dest
	 */
	public static void moveMovie(String src, String dest) {
		File srcDir = new File(src);
		File destDir = new File(dest);
		Iterator<File> fileIter = FileUtils.iterateFiles(srcDir, VIDEOEXT, true);
		int count = 0;
		while (fileIter.hasNext())
		{
			File srcFile = fileIter.next();
			log.debug("begin cut src File " + srcFile.getPath());
			try {
				FileUtils.moveFileToDirectory(srcFile, destDir, false);
				count++;
				log.debug("copy src File end");
			} catch (IOException e) {
				log.error(e.toString());
			}
		}
		log.info("Total cut file count is : " + count);
	}
	
	public static void main (String[] args)
	{
		CollectMovie.moveMovie("D://迅雷下载//", "D://movie//");
	}
}
