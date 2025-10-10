package xela.chris.barbearia.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import xela.chris.barbearia.models.Funcionario;

/**
 * Serviço responsável por gerar e validar tokens JWT (JSON Web Tokens)
 * utilizados na autenticação e autorização de usuários (funcionários).
 * <p>
 * Cada token contém informações essenciais do usuário, como ID, nome,
 * nome de usuário e permissões, permitindo o controle de acesso a
 * diferentes partes do sistema sem necessidade de consultas constantes
 * aos dados locais.
 * </p>
 *
 * <p><b>Importante:</b> A chave secreta {@code SECRET} deve ser alterada
 * e configurada através de uma variável de ambiente em produção.</p>
 */
public class TokenService {

    /** Identificador do emissor do token (nome do sistema). */
    private static final String ISSUER = "xela.barbearia";

    /**
     * Chave secreta utilizada na assinatura do token.
     * Deve ser configurada via variável de ambiente {@code JWT_SECRET}.
     */
    private static final String SECRET = System.getenv().getOrDefault(
            "JWT_SECRET",
            "Nós somos do Clube Atlético Mineiro Jogamos com muita raça e amor Vibramos com alegria nas vitórias Clube Atlético Mineiro Galo Forte Vingador Vencer, vencer, vencer Este é o nosso ideal Honramos o nome de Minas No cenário esportivo mundial Lutar, lutar, lutar Pelos gramados do mundo pra vencer Clube Atlético Mineiro Uma vez até morrer Nós somos campeões do gelo O nosso time é imortal Nós somos campeões dos Campeões Somos o orgulho do esporte nacional Lutar, lutar, lutar Com toda nossa raça pra vencer Clube Atlético Mineiro Uma vez até morrer Clube Atlético Mineiro Uma vez até morrer Nós somos campeões do gelo O nosso time é imortal Nós somos campeões dos Campeões Somos o orgulho do esporte nacional Lutar, lutar, lutar Com toda nossa raça pra vencer Clube Atlético Mineiro Uma vez até morrer Clube Atlético Mineiro Uma vez até morrer"
    );

    /** Tempo de expiração do token em milissegundos (8 horas). */
    private static final long EXPIRACAO_MS = 1000L * 60 * 60 * 8;

    /** Objeto de chave criptográfica utilizado para assinar e validar tokens. */
    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    /**
     * Gera um novo token JWT para o funcionário autenticado.
     *
     * @param f o funcionário autenticado
     * @return uma string contendo o token JWT gerado
     */
    public static String gerarToken(Funcionario f) {
        long now = System.currentTimeMillis();
        String permissoes = "";
        if (f.getPermissoes() != null) {
            permissoes = f.getPermissoes()
                    .stream()
                    .map(Enum::name)
                    .collect(Collectors.joining(","));
        }

        return Jwts.builder()
                .setIssuer(ISSUER)
                .setSubject(String.valueOf(f.getId()))
                .claim("username", f.getUsuario())
                .claim("nome", f.getNome())
                .claim("permissoes", permissoes)
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRACAO_MS))
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Analisa e valida um token JWT, retornando seus {@link Claims}.
     *
     * @param token o token JWT a ser analisado
     * @return um objeto {@link Jws} contendo os claims do token,
     *         ou {@code null} se o token for inválido ou expirado
     */
    public static Jws<Claims> parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(KEY)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException e) {
            return null;
        }
    }

    /**
     * Verifica se um token JWT é válido e não expirou.
     *
     * @param token o token JWT a ser validado
     * @return {@code true} se o token for válido, {@code false} caso contrário
     */
    public static boolean tokenValido(String token) {
        return parseToken(token) != null;
    }

    /**
     * Obtém o ID do usuário a partir do token JWT.
     *
     * @param token o token JWT
     * @return o ID do usuário contido no token, ou {@code null} se o token for inválido
     */
    public static Long getIdUsuarioDoToken(String token) {
        Jws<Claims> j = parseToken(token);
        if (j == null) return null;
        String sub = j.getBody().getSubject();
        return Long.valueOf(sub);
    }

    /**
     * Obtém a lista de permissões contida no token JWT.
     *
     * @param token o token JWT
     * @return uma lista de permissões, ou uma lista vazia se o token não contiver permissões válidas
     */
    public static List<String> getPermissoesDoToken(String token) {
        Jws<Claims> j = parseToken(token);
        if (j == null) return null;
        String p = j.getBody().get("permissoes", String.class);
        if (p == null || p.isBlank()) return java.util.Collections.emptyList();
        return java.util.Arrays.stream(p.split(",")).collect(Collectors.toList());
    }
}
