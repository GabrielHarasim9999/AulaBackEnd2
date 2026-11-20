package API;

import DTO.ProdutoRequest;
import dao.ProdutoDao;
import io.javalin.Javalin;
import io.javalin.http.Context;
import model.Produto;

import java.sql.SQLException;
import java.util.Map;
import java.util.NoSuchElementException;

public class ApiMain {
    public static void main(String[] args) {
        ProdutoDao produtoDao = new ProdutoDao();


        int porta = lerPorta();

        Javalin app = Javalin.create(config -> {
            config.routes.get("/", ctx -> ctx.json(Map.of("aplicacao", "Aula Exemplo API", "status", "online")));

            config.routes.get("/API/produto", ctx -> executar(ctx, () -> ctx.json(produtoDao.listarTodos())));

            config.routes.get("/API/produto/{id}", ctx -> executar(ctx, () -> {
                Long id = Long.parseLong(ctx.pathParam("id"));
                Produto produto = produtoDao.buscarPorId(id)
                        .orElseThrow(() -> new NoSuchElementException("Produto não encontrado"));
                ctx.json(produto);
            }));

            config.routes.post("/API/produto", ctx -> executar(ctx, () -> {
                ProdutoRequest request = ctx.bodyAsClass(ProdutoRequest.class);
                Produto produto = new Produto(request.nome(), request.preco(), request.quantidade());

                ctx.status(201).json(produtoDao.criar(produto));
            }));

            config.routes.put("/API/produto/{id}", ctx -> executar(ctx, () -> {
                Long id = Long.parseLong(ctx.pathParam("id"));
                ProdutoRequest request = ctx.bodyAsClass(ProdutoRequest.class);
                Produto produto = new Produto(id, request.nome(), request.preco(), request.quantidade());
                if (!produtoDao.atualizar(produto)) {
                    throw new NoSuchElementException("Produto não encontrado!");
                }
                ctx.json(produto);
            }));

            config.routes.delete("/API/produto/{id}", ctx -> executar(ctx, () -> {
                Long id = Long.parseLong(ctx.pathParam("id"));
                if (!produtoDao.excluir(id)) {
                    throw new NoSuchElementException("Produto não encontrado!");
                }
                ctx.status(204);
                //http.cat
            }));
        }).start(porta);

        System.out.println("API disponivel em http://localhost:" + app.port());
    }

    private static void executar(Context ctx, AcaoHttp acaoHttp) {
        try {
            acaoHttp.executar();
        } catch (SecurityException e) {
            ctx.status(401).json(Map.of("erro", e.getMessage()));
        } catch (NoSuchElementException e) {
            ctx.status(404).json(Map.of("erro", e.getMessage()));
        } catch (IllegalStateException e) {
            ctx.status(409).json(Map.of("erro", e.getMessage()));
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(Map.of("erro", e.getMessage()));
        } catch (SQLException e) {
            ctx.status(500).json(Map.of("erro", "Falha ao acessar o banco de dados!"));
            e.printStackTrace();
        } catch (Exception e) {
            ctx.status(500).json(Map.of("erro", "Erro interno da API"));
            e.printStackTrace();
        }
    }

    private static int lerPorta() {
        String valor = System.getenv().getOrDefault("API_PORT", "7070");
        try {
            int porta = Integer.parseInt(valor);
            if (porta < 1 || porta > 65535) {
                throw new IllegalArgumentException();
            }
            return porta;
        } catch (IllegalArgumentException e) {
            return 7070;
        }
    }

    @FunctionalInterface
    private interface AcaoHttp {
        void executar() throws Exception;
    }
}
