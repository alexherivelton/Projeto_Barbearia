package xela.chris.barbearia.Gerenciadores;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Repositório genérico para persistência de objetos em arquivos JSON.
 *
 * <p>
 * Permite salvar e recuperar listas de objetos de qualquer tipo usando
 * a biblioteca Jackson. Os dados são armazenados em uma pasta específica
 * dentro do projeto. Agora com JSON formatado (pretty-print) para melhor leitura.
 * </p>
 *
 * @param <T> Tipo de objeto que será persistido no arquivo JSON.
 */
public class RepositorioJson<T> {

    private final Class<T> tipo;
    private final File arquivo;
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Construtor do repositório JSON.
     *
     * <p>
     * Cria a pasta de armazenamento caso não exista e define o arquivo
     * onde os objetos serão salvos.
     * </p>
     *
     * @param tipo        Classe do tipo de objeto que será persistido.
     * @param nomeArquivo Nome do arquivo JSON onde os dados serão armazenados.
     */
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

    /**
     * Busca todos os objetos salvos no arquivo JSON.
     *
     * <p>
     * Se o arquivo não existir ou estiver vazio, retorna uma lista vazia.
     * </p>
     *
     * @return Lista de objetos do tipo {@code T} encontrados no arquivo JSON.
     */
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

    /**
     * Retorna uma lista de todos os objetos armazenados.
     *
     * <p>
     * Este método é um alias para {@link #buscarTodos()}, criado
     * para simplificar chamadas em outras classes, como o {@code GerenciadorLogin}.
     * </p>
     *
     * @return Lista de objetos do tipo {@code T} atualmente salvos no repositório.
     */
    public synchronized List<T> listar() {
        return buscarTodos();
    }

    /**
     * Salva todos os objetos fornecidos no arquivo JSON, sobrescrevendo os dados existentes.
     *
     * <p>
     * Agora o JSON é salvo com formatação legível (pretty-print).
     * </p>
     *
     * @param dados Lista de objetos do tipo {@code T} a ser salva no arquivo JSON.
     */
    public synchronized void salvarTodos(List<T> dados) {
        try {
            // Escreve JSON formatado para melhor leitura
            mapper.writerWithDefaultPrettyPrinter().writeValue(arquivo, dados);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Retorna o caminho absoluto do arquivo JSON onde os dados estão sendo armazenados.
     *
     * @return Caminho absoluto do arquivo JSON.
     */
    public String getCaminhoArquivo() {
        return this.arquivo.getAbsolutePath();
    }
}
