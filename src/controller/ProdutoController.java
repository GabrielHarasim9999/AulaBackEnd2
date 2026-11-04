package controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequstController
import java.net.URI;
import java.util.list;


@RestController
@RequestMapping("api/produtos")

public class ProdutoController {
    private final ProdutoDao produtoDao;

    public ProdutoController(ProdutoDao produtoDao){
        this.produtoDao = produtoDao;
    }

    @GetMapping
    public List<Produto> listar(){
        return produtoDao.listarTodos();
    }

    @GetMapping("/{id}")
    public Produto buscar(@PathVariable Long id){
        return produtoDao.buscarPorId(id);
    }
    @PostMapping
    public ResponseEntity<Produto> criar(@Valid @RequestBody ProdutoRequest request){
        Produto produto = produtoDao.criar(request);
        return ResponseEntity.created(URI.create("/api/produtos/" + produto.getID())).body(produto);
    }
    @PutMapping("/{id}")
    public Produto atualizar(@PathVariable Long id, @Valid @RequestBody ProdutoRequest request){
        return produtoDao.atualizar()
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> excluir(@PathVariable Long id){
        produtoDao.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
