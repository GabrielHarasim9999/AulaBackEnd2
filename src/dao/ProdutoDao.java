package dao;

import config.ConnectionFactory;
import model.Produto;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class ProdutoDao  implements  CrudDao<Produto, Long>{


    @Override
    public Produto criar(Produto produto) throws SQLException {
        String sql ="INSERT INTO produtos (nome, preço, quantidade) VALUES(?,?,?)";

        try(Connection conexao = ConnectionFactory.abrirConexao();
            PreparedStatement comando = conexao.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){

            comando.setString(1, produto.getNome());
            comando.setDouble(2, produto.getPreco());
            comando.setInt(3, produto.getQuantidade());
            comando.executeUpdate();


            try(ResultSet chaves = comando.getGeneratedKeys()){
                if (chaves.next()){
                    produto.setId(chaves.getLong(1));
                }
            }

        }
        return null;
    }

    @Override
    public Optional<Produto> buscarPorId(Long i) throws SQLException {
        return Optional.empty();
    }

    @Override
    public List<Produto> listarTodos() throws SQLException {
        return List.of();
    }

    @Override
    public boolean atualizar(Produto entidade) throws SQLException {
        return false;
    }

    @Override
    public boolean excluir(Long i) throws SQLException {
        return false;
    }
}
