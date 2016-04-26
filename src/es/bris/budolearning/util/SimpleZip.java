package es.bris.budolearning.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import es.bris.budolearning.app.Constants;

public class SimpleZip {

	private final static Logger LOGGER = Logger.getLogger("SimpleZip");

	private static void addDirToArchive(File /* ZipOutputStream */zos,
			File srcFile, final String SOURCE) {
		File[] files = srcFile.listFiles();
		LOGGER.log(Level.INFO, "Adding directory: " + srcFile.getName());
		for (int i = 0; i < files.length; i++) {
			// if the file is directory, use recursion
			if (files[i].isDirectory()) {
				addDirToArchive(zos, files[i], SOURCE);
				continue;
			}
			try {
				LOGGER.log(Level.INFO, "Adding file: " + files[i].getName());
				addFilesToZip(zos, files, SOURCE);

				/*
				 * // create byte buffer byte[] buffer = new byte[1024];
				 * FileInputStream fis = new FileInputStream(files[i]); ZipEntry
				 * zipEntry = new
				 * ZipEntry(files[i].getAbsolutePath().substring(files
				 * [i].getAbsolutePath().indexOf(source)+source.length()+1));
				 * zos.putNextEntry(zipEntry); int length; while ((length =
				 * fis.read(buffer)) > 0) { zos.write(buffer, 0, length); }
				 * zos.closeEntry(); zos.flush(); zos.finish(); // close the
				 * InputStream fis.close();
				 */
			} catch (IOException ioe) {
				LOGGER.log(Level.WARNING, "ERROR", ioe);
			}
		}
	}

	public static void zipItDirectory(final String zipFile,
			final String SOURCE_FOLDER) {

		// try {
		/*
		 * FileOutputStream fos = new FileOutputStream(zipFile, true);
		 * ZipOutputStream zos = new ZipOutputStream(fos);
		 */
		addDirToArchive(new File(zipFile), new File(SOURCE_FOLDER),
				Constants.FILE_PATH + File.separator + "backup");

		/*
		 * zos.closeEntry(); zos.close(); fos.close();
		 */
		// } catch (FileNotFoundException e1) {
		// LOGGER.log(Level.WARNING, "ERROR" , e1);
		// } catch (IOException e) {
		// LOGGER.log(Level.WARNING, "ERROR" , e);
		// }
	}

	public static void addFilesToZip(File zipFile, File[] files, final String SOURCE)
			throws IOException {
		// get a temp file
		if (zipFile.exists()) {
			File tempFile = File.createTempFile(zipFile.getName(), null,
					new File(Constants.FILE_PATH));
			// delete it, otherwise you cannot rename your existing zip to it.
			tempFile.delete();

			boolean renameOk = zipFile.renameTo(tempFile);
			if (!renameOk) {
				throw new RuntimeException("could not rename the file "
						+ zipFile.getAbsolutePath() + " to "
						+ tempFile.getAbsolutePath());
			}
			byte[] buf = new byte[1024];

			ZipInputStream zin = new ZipInputStream(new FileInputStream(
					tempFile));
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
					zipFile));

			ZipEntry entry = zin.getNextEntry();
			while (entry != null) {
				String name = entry.getName();
				boolean notInFiles = true;
				for (File f : files) {
					if (f.getName().equals(name)) {
						notInFiles = false;
						break;
					}
				}
				if (notInFiles) {
					// Add ZIP entry to output stream.
					out.putNextEntry(new ZipEntry(name));
					// Transfer bytes from the ZIP file to the output file
					int len;
					while ((len = zin.read(buf)) > 0) {
						out.write(buf, 0, len);
					}
				}
				entry = zin.getNextEntry();
			}
			// Close the streams
			zin.close();
			// Compress the files
			for (int i = 0; i < files.length; i++) {
				InputStream in = new FileInputStream(files[i]);
				// Add ZIP entry to output stream.
				String zipEntry = files[i].getAbsolutePath().substring(
						files[i].getAbsolutePath().indexOf(SOURCE)+SOURCE.length()+1);
				out.putNextEntry(new ZipEntry(zipEntry));
				// Transfer bytes from the file to the ZIP file
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				// Complete the entry
				out.closeEntry();
				in.close();
			}
			// Complete the ZIP file
			out.close();
			tempFile.delete();
		} else {
			FileOutputStream fos = new FileOutputStream(zipFile, true);
			ZipOutputStream zos = new ZipOutputStream(fos);
			zos.closeEntry(); 
			zos.close(); 
			fos.close();
		}
	}
}