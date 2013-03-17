package br.com.etraining.utils.io;

import java.io.File;

public class FileUtils {

	public static File renomearArquivoComSufixo(File arquivo, String sufixo) {
		String caminhoArquivo = arquivo.getAbsolutePath().substring(0,
				arquivo.getAbsolutePath().lastIndexOf(File.separator));
		String nomeArquivoOriginal = arquivo.getName();
		String novoNomeArquivo = nomeArquivoOriginal.substring(0,
				nomeArquivoOriginal.lastIndexOf(".")) + sufixo;

		File arquivoRenomeado = new File(caminhoArquivo, novoNomeArquivo);
		if (arquivo.renameTo(arquivoRenomeado))
			return arquivoRenomeado;
		return arquivo;
	}
}
