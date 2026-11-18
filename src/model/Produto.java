package model;

public class Produto {
    private Long id;
    private String nome;
    private Double preco;
    private int quantidade;

    public Produto(String nome, Double preco, int quantidade) {

        this(null, nome, preco, quantidade);
    }

    public Produto(Long id, String nome, Double preco, int quantidade) {
        this.id = id;
        setNome(nome);
        setPreco(preco);
        setQuantidade(quantidade);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do produto é obrigatório");
        }
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        if (preco == null || preco < 0) {
            throw new IllegalArgumentException("O preço não pode ser negativo");
        }
        this.preco = preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        if (quantidade < 0) {
            throw new IllegalArgumentException("A quantidade não pode ser negativa");
        }
        this.quantidade = quantidade;
    }

    @Override
    public String toString() {
        return "Produto: id=%d, nome=%s, preco=%s, quantidade=%d"
                .formatted(id, nome, preco, quantidade);
    }
}
