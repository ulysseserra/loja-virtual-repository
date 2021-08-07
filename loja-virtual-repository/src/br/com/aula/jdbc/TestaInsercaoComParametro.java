package br.com.aula.jdbc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestaInsercaoComParametro {

	public static void main(String[] args) throws SQLException {
		ConnectionFactory factory = new ConnectionFactory();
		try(Connection connection = factory.recuperarConexao()){

			connection.setAutoCommit(false);

			try (PreparedStatement stm = 
					connection.prepareStatement("INSERT INTO PRODUTO (nome, descricao) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
					){
				adicionarVariavel("TECALDO", "TECLADO SEM FIO LOGITECH", stm);
				adicionarVariavel("MOUSE", "MOUSE SEM FIO LOGITECH", stm);
				adicionarVariavel("SSD", "SSD 240GB KINGSTON", stm);
				adicionarVariavel("MEMORIA", "MEMORIA RAM DDR4 8GB KINGSTON", stm);
				adicionarVariavel("PEN DRIVE", "PREN DRIVE 8GB KINGSTON", stm);
				adicionarVariavel("ROTEADOR", "ROTEADOR TP-LINK 1500MBPS", stm);

				connection.commit();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("ROLLBACK EXECUTADO");
				connection.rollback();
			}
		}
	}

	private static void adicionarVariavel(String nome, String descricao, PreparedStatement stm) throws SQLException {
		stm.setString(1, nome);
		stm.setString(2, descricao);

		if(nome.equals("MOUSE")) {
			throw new RuntimeException("Não foi possível adicionar o produto");
		}

		stm.execute();

		try(ResultSet rst = stm.getGeneratedKeys()){
			while(rst.next()) {
				Integer id = rst.getInt(1);
				System.out.println("O id criado foi: " + id);
			}
		}
	}
}


