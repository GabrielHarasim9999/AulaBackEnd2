import dao.ProdutoDao;
import model.Produto;

import java.sql.SQLException;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        ProdutoDao produtoDao = new ProdutoDao();

        try{
            Produto produto = new Produto(00001L, "Banana prata", 6.50, 10);

            produtoDao.criar(produto);
            System.out.println("Criado: "+ produto);

            produtoDao.buscarPorId(produto.getId()).ifPresent( encontrado ->System.out.println("Encontrado: "+ encontrado));

            produto.setPreco(5.12);
            produto.setQuantidade(20);
            System.out.println("Atualizado: " + produtoDao.atualizar(produto));

            System.out.println("Produtos cadastrados: ");
            produtoDao.listarTodos().forEach(System.out::println);

            System.out.println("Excluido: " + produtoDao.excluir(produto.getId()));
        } catch (SQLException e){
            System.err.println("Não foi possível acessar uma SQL");
            System.err.println("Mensagem:" + e.getSQLState());
            System.err.println("SQLState:" + e.getSQLState());
            e.printStackTrace();
        }
    }
}