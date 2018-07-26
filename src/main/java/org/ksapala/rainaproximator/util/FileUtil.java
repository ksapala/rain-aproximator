/**
 * 
 */
package org.ksapala.rainaproximator.util;

import org.ksapala.rainaproximator.settings.Property;
import org.ksapala.rainaproximator.settings.Settings;

import javax.imageio.ImageIO;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author krzysztof
 *
 */
public class FileUtil {

	/**
	 * 
	 */
	public FileUtil() {
	}
	
	/**
	 * @param fileName
	 * @param content
	 * @throws IOException
	 */
	public static void writeToFile(String fileName, RenderedImage content) throws IOException {
		writeToFile(fileName, new RenderedImageContent(content));
	}
	
	/**
	 * @param fileName
	 * @param content
	 * @throws IOException
	 */
	public static void writeToFile(String fileName, Content content) throws IOException {
		File file = new File(fileName);
		file.createNewFile();
		content.writeToFile(file);
	}
	
	/**
	 * @param fileName
	 * @param tempFileName
	 * @param content
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	public static void writeToFileWithTemp(String fileName, String tempFileName, String content) 
			throws IOException, FileNotFoundException {

		FileUtil.writeToFileWithTemp(fileName, tempFileName, new StringContent(content));
	}

	/**
	 * @param fileName
	 * @param tempFileName
	 * @param content
	 * @throws IOException
	 */
	public static void writeToFileWithTemp(String fileName, String tempFileName, RenderedImage content) 
			throws IOException {
		
		FileUtil.writeToFileWithTemp(fileName, tempFileName, new RenderedImageContent(content));
	}

	/**
	 * @param fileName
	 * @param tempFileName
	 * @param content
	 * @throws IOException
	 */
	public static void writeToFileWithTemp(String fileName, String tempFileName, Content content)
			throws IOException {
		
		writeToFile(tempFileName, content);
		renameFile(tempFileName, fileName);
	}

	/**
	 * @param tempFileName
	 * @param fileName
	 */
	public static void renameFile(String tempFileName, String fileName) {
		File file = new File(fileName);
		File tempFile = new File(tempFileName);
		tempFile.renameTo(file);
	}

	public interface Content {
		public void writeToFile(File file) throws IOException;
	}

	public static class StringContent implements Content {

		private String string;

		public StringContent(String string) {
			this.string = string;
		}

		@Override
		public void writeToFile(File file) throws FileNotFoundException {
			PrintWriter writer = new PrintWriter(file);
			writer.println(this.string);
			writer.close();
		}
	}

	public static  class RenderedImageContent implements Content {

		private RenderedImage renderedImage;

		public RenderedImageContent(RenderedImage renderedImage) {
			this.renderedImage = renderedImage;
		}

		@Override
		public void writeToFile(File file) throws IOException {
			ImageIO.write(this.renderedImage, Settings.get(Property.RADAR_MAP_FILE_TYPE), file);
		}
	}

}
