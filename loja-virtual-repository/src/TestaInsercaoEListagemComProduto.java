import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import br.com.aula.jdbc.ConnectionFactory;
import dao.ProdutoDAO;
import modelo.Produto;

public class TestaInsercaoEListagemComProduto {

	public static void main(String[] args) throws SQLException {

		Produto rack = new Produto("RACK", "RACK VESTICAL");

		try (Connection connection = new ConnectionFactory().recuperarConexao()) {
			ProdutoDAO produtoDao = new ProdutoDAO(connection);
			produtoDao.salvar(rack);
			List<Produto> listaDeProdutos = produtoDao.listar();
			listaDeProdutos.stream().forEach(lp -> System.out.println(lp));
		}
	}
}
