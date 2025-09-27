package xela.chris.barbearia.Repositorios;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RepositorioJson<T> {
    private final Class<T> tipo;
    private final File arquivo;
    private final ObjectMapper mapper = new ObjectMapper();

    public RepositorioJson(Class<T> tipo, String nomeArquivo) {
        String caminhoPasta = "src" + File.separator + "main" + File.separator + "java" + File.separator +
                "xela" + File.separator + "chris" + File.separator + "barbearia" + File.separator + "jsons";

        File pasta = new File(caminhoPasta);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
        this.tipo = tipo;
        this.arquivo = new File(pasta, nomeArquivo);
    }

    public synchronized List<T> buscarTodos() {
        try {
            if (!this.arquivo.exists() || arquivo.length() == 0) {
                return new ArrayList<>();
            }

            CollectionType listType = mapper.getTypeFactory()
                    .constructCollectionType(List.class, tipo);

            return mapper.readValue(arquivo, listType);

        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public synchronized void salvarTodos(List<T> dados) {
        try {
            mapper.writeValue(arquivo, dados);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCaminhoArquivo() {
        return this.arquivo.getAbsolutePath();
    }
}