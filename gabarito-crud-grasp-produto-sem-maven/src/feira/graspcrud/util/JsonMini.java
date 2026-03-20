package feira.graspcrud.util;

import feira.graspcrud.domain.Produto;
import feira.graspcrud.domain.TipoProduto;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utilitario simples para leitura/escrita JSON sem bibliotecas externas.
 */
public class JsonMini {
    private static final Pattern OBJ_PATTERN = Pattern.compile("\\{([^}]*)}");
    private static final Pattern FIELD_PATTERN = Pattern.compile("\"([^\"]+)\"\\s*:\\s*(\"(?:\\\\.|[^\"])*\"|-?\\d+(?:\\.\\d+)?|true|false|null)");

    /**
     * Carrega lista de tipos de produto.
     *
     * @param arquivo caminho do JSON
     * @return lista carregada
     */
    public static List<TipoProduto> carregarTipos(Path arquivo) {
        List<TipoProduto> tipos = new ArrayList<>();
        String content = lerArquivo(arquivo);
        if (content == null || content.trim().isEmpty()) return tipos;
        for (Map<String, String> m : parseArray(content)) {
            Long id = parseLong(m.get("id"));
            String nome = parseString(m.get("nome"));
            String descricao = parseString(m.get("descricao"));
            tipos.add(new TipoProduto(id, nome, descricao));
        }
        return tipos;
    }

    /**
     * Carrega lista de produtos.
     *
     * @param arquivo caminho do JSON
     * @return lista carregada
     */
    public static List<Produto> carregarProdutos(Path arquivo) {
        List<Produto> produtos = new ArrayList<>();
        String content = lerArquivo(arquivo);
        if (content == null || content.trim().isEmpty()) return produtos;
        for (Map<String, String> m : parseArray(content)) {
            Long id = parseLong(m.get("id"));
            String nome = parseString(m.get("nome"));
            double preco = parseDouble(m.get("preco"));
            int estoque = parseInt(m.get("estoque"));
            Long tipoId = parseLong(m.get("tipoProdutoId"));
            produtos.add(new Produto(id, nome, preco, estoque, tipoId == null ? 0L : tipoId));
        }
        return produtos;
    }

    /**
     * Salva lista de tipos de produto.
     *
     * @param arquivo caminho do JSON
     * @param tipos lista a persistir
     */
    public static void salvarTipos(Path arquivo, List<TipoProduto> tipos) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < tipos.size(); i++) {
            TipoProduto t = tipos.get(i);
            sb.append("  {\"id\": ").append(t.getId())
                .append(", \"nome\": \"").append(escape(t.getNome())).append("\"")
                .append(", \"descricao\": \"").append(escape(t.getDescricao())).append("\"}");
            if (i < tipos.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]\n");
        escreverArquivo(arquivo, sb.toString());
    }

    /**
     * Salva lista de produtos.
     *
     * @param arquivo caminho do JSON
     * @param produtos lista a persistir
     */
    public static void salvarProdutos(Path arquivo, List<Produto> produtos) {
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < produtos.size(); i++) {
            Produto p = produtos.get(i);
            sb.append("  {\"id\": ").append(p.getId())
                .append(", \"nome\": \"").append(escape(p.getNome())).append("\"")
                .append(", \"preco\": ").append(p.getPreco())
                .append(", \"estoque\": ").append(p.getEstoque())
                .append(", \"tipoProdutoId\": ").append(p.getTipoProdutoId())
                .append("}");
            if (i < produtos.size() - 1) sb.append(",");
            sb.append("\n");
        }
        sb.append("]\n");
        escreverArquivo(arquivo, sb.toString());
    }

    private static List<Map<String, String>> parseArray(String json) {
        List<Map<String, String>> list = new ArrayList<>();
        Matcher objMatcher = OBJ_PATTERN.matcher(json);
        while (objMatcher.find()) {
            String body = objMatcher.group(1);
            Map<String, String> map = new LinkedHashMap<>();
            Matcher fieldMatcher = FIELD_PATTERN.matcher(body);
            while (fieldMatcher.find()) {
                map.put(fieldMatcher.group(1), fieldMatcher.group(2));
            }
            list.add(map);
        }
        return list;
    }

    private static String lerArquivo(Path arquivo) {
        try {
            if (!Files.exists(arquivo)) return null;
            return Files.readString(arquivo, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler JSON: " + arquivo, e);
        }
    }

    private static void escreverArquivo(Path arquivo, String content) {
        try {
            Path parent = arquivo.getParent();
            if (parent != null) Files.createDirectories(parent);
            Files.writeString(arquivo, content, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar JSON: " + arquivo, e);
        }
    }

    private static String escape(String s) {
        if (s == null) return "";
        return s.replace("\\", "\\\\").replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }

    private static String unquote(String raw) {
        if (raw == null) return null;
        raw = raw.trim();
        if (raw.equals("null")) return null;
        if (raw.startsWith("\"") && raw.endsWith("\"") && raw.length() >= 2) {
            String x = raw.substring(1, raw.length() - 1);
            return x.replace("\\n", "\n").replace("\\\"", "\"").replace("\\\\", "\\");
        }
        return raw;
    }

    private static String parseString(String raw) {
        return unquote(raw);
    }

    private static Long parseLong(String raw) {
        if (raw == null || raw.equals("null")) return null;
        return Long.parseLong(raw.replace("\"", "").trim());
    }

    private static int parseInt(String raw) {
        if (raw == null || raw.equals("null")) return 0;
        return Integer.parseInt(raw.replace("\"", "").trim());
    }

    private static double parseDouble(String raw) {
        if (raw == null || raw.equals("null")) return 0;
        return Double.parseDouble(raw.replace("\"", "").trim());
    }
}
