package br.univali.portugol.nucleo.compilador;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.json.JSONObject;

import br.univali.portugol.nucleo.analise.ResultadoAnalise;
import br.univali.portugol.nucleo.mensagens.AvisoAnalise;
import br.univali.portugol.nucleo.mensagens.ErroAnalise;
import br.univali.portugol.nucleo.mensagens.ErroExecucao;
import br.univali.portugol.nucleo.programa.Programa;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONArray;

public class LogManager {

	private static File fileLog;
	private static String URL;
	private static Object lock = new Object();
	private Compilation comp;

	public LogManager(Programa programa, String codigoFonte, int numLinhas, String url, ErroExecucao erroExecucao) {		
		Thread thread = new Thread(){
            public void run(){
            //Executa em nova thread para não travar a compilação/execução, mas multiplos logs não devem ser executadro em paralelo	
            	synchronized (lock) {
                StartLog(programa, codigoFonte, numLinhas, url, erroExecucao);
            	}
            }
        };                      
        thread.start();		
	}
	
	private void StartLog(Programa programa, String codigoFonte, int numLinhas, String url, ErroExecucao erroExecucao) {

		if (codigoFonte == null || codigoFonte.equals(""))
			return;
		if (URL == null)
			URL = url;

		try {
			ResultadoAnalise resultadoAnalise = programa.getResultadoAnalise();

			comp = new Compilation();

			if (resultadoAnalise.contemErros())
				comp.successfulCompilation = false;
			else
				comp.successfulCompilation = true;

			DateFormat df = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
			Date today = Calendar.getInstance().getTime();
			comp.compileDate = df.format(today);

			comp.number_characters = codigoFonte.length();
			comp.numberOfFunctions = resultadoAnalise.getPrograma().getFuncoes().size();
			comp.number_lines = numLinhas;
			comp.compilation_errors = setCompileErrorsJson(resultadoAnalise);
			comp.warnings = setCompileWarningsJson(resultadoAnalise);

			// Caso tenha erros aqui pode salvar, porque não é chamada a função
			// execucaoEncerrada que pode ter outros erros
			/*
			 * if (!comp.successfulCompilation) { WriteLog(); }
			 */
			if (erroExecucao == null) {
				WriteLog();
			} else {
				WriteLog(erroExecucao);
			}
		} catch (Exception e) {
			System.out.println("Erro ao iniciar log");
		}

	}

	private void WriteLog() {
		if (comp == null)
			return;
		try {
			String filePathString = System.getProperty("user.dir") + "\\log.txt";
			JSONArray jsonArray = new JSONArray();

			fileLog = new File(filePathString);
			if (fileLog.exists() && !fileLog.isDirectory()) {
				jsonArray = new JSONArray(Read(fileLog));
			}

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("successful_compilation", comp.successfulCompilation);
			jsonObject.put("compile_date", comp.compileDate);
			jsonObject.put("numberOfFunctions", comp.numberOfFunctions);
			jsonObject.put("number_characters", comp.number_characters);
			jsonObject.put("number_lines", comp.number_lines);
			jsonObject.put("compilation_errors", comp.compilation_errors);
			jsonObject.put("warnings", comp.warnings);
			jsonObject.put("execution_error", new JSONObject());

			jsonArray.put(jsonObject);

			Save(jsonArray.toString());
			comp = null;
			SendToServer();
		} catch (Exception e) {
			System.out.println("Erro ao gravar log no arquivo de logs");
		}

	}

	private void WriteLog(ErroExecucao erroExecucao) {
		if (comp == null)
			return;
		try {
			String filePathString = System.getProperty("user.dir") + "\\log.txt";
			JSONArray jsonArray = new JSONArray();

			fileLog = new File(filePathString);
			if (fileLog.exists() && !fileLog.isDirectory()) {
				jsonArray = new JSONArray(Read(fileLog));
			}

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("successful_compilation", false);
			jsonObject.put("compile_date", comp.compileDate);
			jsonObject.put("numberOfFunctions", comp.numberOfFunctions);
			jsonObject.put("number_characters", comp.number_characters);
			jsonObject.put("number_lines", comp.number_lines);
			jsonObject.put("compilation_errors", comp.compilation_errors);
			jsonObject.put("warnings", comp.warnings);

			JSONObject jsonErroExecucao = new JSONObject();
			jsonErroExecucao.put("message", erroExecucao.getMensagem());
			jsonErroExecucao.put("line", erroExecucao.getLinha());
			jsonErroExecucao.put("column", erroExecucao.getColuna());

			jsonObject.put("execution_error", jsonErroExecucao);

			jsonArray.put(jsonObject);

			Save(jsonArray.toString());

			SendToServer();
		} catch (Exception e) {
			System.out.println("Erro ao gravar log no arquivo de logs");
		}

	}

	private JSONArray setCompileErrorsJson(ResultadoAnalise resultadoAnalise) {
		JSONArray jsonArray = new JSONArray();
		if (resultadoAnalise.contemErros()) {
			for (ErroAnalise erro : resultadoAnalise.getErros()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("code", erro.getCodigo());
				jsonObject.put("line", String.valueOf(erro.getLinha()));
				jsonObject.put("column", String.valueOf(erro.getColuna()));
				jsonArray.put(jsonObject);
			}
		}
		return jsonArray;
	}

	private JSONArray setCompileWarningsJson(ResultadoAnalise resultadoAnalise) {
		JSONArray jsonArray = new JSONArray();
		if (resultadoAnalise.contemAvisos()) {
			for (AvisoAnalise aviso : resultadoAnalise.getAvisos()) {
				JSONObject jsonObject = new JSONObject();
				jsonObject.put("message", aviso.getMensagem());
				jsonObject.put("line", String.valueOf(aviso.getLinha()));
				jsonObject.put("column", String.valueOf(aviso.getColuna()));
				jsonArray.put(jsonObject);
			}
		}
		return jsonArray;
	}

	private void Save(String textToSave) {
		String temp_filePathString = System.getProperty("user.dir") + "\\temp_log.txt";

		File temp_file = new File(temp_filePathString);
		temp_file.delete();
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(temp_file), 32768);
			out.write(textToSave);
			out.close();
		} catch (IOException e) {
			System.out.println("Erro na gravação do arquivo de log");
			return;
		}

		String final_filePathString = System.getProperty("user.dir") + "\\log.txt";
		File final_file = new File(final_filePathString);
		final_file.delete();
		temp_file.renameTo(final_file);
	}

	private String Read(File fileLog) {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(fileLog));

			StringBuilder builder = new StringBuilder();
			String aux = "";

			while ((aux = bf.readLine()) != null) {
				builder.append(aux);
			}

			bf.close();
			return builder.toString();

		} catch (Exception e) {
			System.out.println("Erro na leitura do arquivo de log");
		}
		return "";
	}

	private void SendToServer() {
		try {
			// Tendo um arquivo sending_to_server.txt exclui
			String temp_filePathString = System.getProperty("user.dir") + "\\sending_to_server.txt";
			File temp_file = new File(temp_filePathString);
			temp_file.delete();

			// renomeia log.txt para sending_to_server.txt
			String log_filePathString = System.getProperty("user.dir") + "\\log.txt";
			File log_file = new File(log_filePathString);
			log_file.renameTo(temp_file);

			// tenta enviar para o servidor
			if (insere_compilacao_servidor(Read(temp_file))) {
				temp_file.delete(); // Deu certo? apaga o arquivo
			} else {
				// Deu erro? retorna para o arquivo principal de log

				JSONArray jsonArrayBase = new JSONArray(Read(temp_file));

				String filePathString = System.getProperty("user.dir") + "\\log.txt";
				fileLog = new File(filePathString);
				if (fileLog.exists() && !fileLog.isDirectory()) {
					JSONArray jsonArray = new JSONArray(Read(fileLog));
					for (Object object : jsonArray) {
						jsonArrayBase.put(object);
					}
				} else {
					temp_file.renameTo(log_file);
				}
			}

		} catch (Exception e) {
			System.out.println("Erro ao gerenciar arquivos de log antes de enviar para o servidor");
		}

	}

	private boolean insere_compilacao_servidor(String infoJson) {
		try {

			String id = getMacAdress();

			if (id == null || id.equals("")) {
				return false;
			}

			HttpClient httpclient = HttpClients.createDefault();
			HttpPut httpput = new HttpPut(URL + "/api/users/compilation/" + id);
			RequestConfig timeout = RequestConfig.custom().setConnectTimeout(2500).setSocketTimeout(2500).build();
			httpput.setConfig(timeout);

			StringEntity objetoJson = new StringEntity(infoJson);
			objetoJson.setContentType(ContentType.APPLICATION_JSON.getMimeType());

			httpput.setEntity(objetoJson);

			// Execute and get the response.
			HttpResponse response = httpclient.execute(httpput);
			HttpEntity entity = response.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				try {
					// do something useful
				} finally {
					instream.close();
				}
			}
		} catch (Exception ex) {
			System.out.println("Erro no envio de logs ao servidor");
			return false;
		}

		return true;
	}

	private String getMacAdress() {
		try {
			InetAddress ip = InetAddress.getLocalHost();
			NetworkInterface network = NetworkInterface.getByInetAddress(ip);
			byte[] mac = network.getHardwareAddress();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < mac.length; i++) {
				sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
			}
			return sb.toString();
		} catch (Exception e) {
			return "";
		}

	}

}
