package br.univali.ps.ui.editor.formatador;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class ResourceHandle
{
	private static final Logger LOGGER = Logger.getLogger(ResourceHandle.class.getName());
	private static final String CHARSET_PADRAO = "UTF-8";

	public static String readInternalResourceFile(String path) throws IOException
	{
		return read(Thread.currentThread().getContextClassLoader().getResourceAsStream(path), CHARSET_PADRAO);
	}

	public static String readInternalResourceFile(String path, String charset) throws IOException
	{
		return read(Thread.currentThread().getContextClassLoader().getResourceAsStream(path), charset);
	}

	public static String readExternalResourceFile(File file) throws IOException
	{
		return read(new FileInputStream(file), CHARSET_PADRAO);
	}

	public static String readExternalResourceFile(File file, String charset) throws IOException
	{
		return read(new FileInputStream(file), charset);
	}

	private static String read(InputStream inputStream, String charset) throws IOException
	{
		StringBuilder reading = new StringBuilder();

		try (InputStreamReader isr = new InputStreamReader(inputStream, charset); BufferedReader reader = new BufferedReader(isr))
		{
			int read;
			char[] buffer = new char[4096];

			while ((read = reader.read(buffer, 0, buffer.length)) > 0)
			{
				reading.append(buffer, 0, read);
			}
		}
		catch (Exception ex)
		{
			LOGGER.log(Level.SEVERE, null, ex);

			throw ex;
		}

		return reading.toString().replaceAll(System.lineSeparator(), "\n");
	}
}
