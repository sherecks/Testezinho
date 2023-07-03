package persistencia;

import dados.*;

import excecoes.InsertException;
import excecoes.SelectException;
import excecoes.UpdateException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UsuarioDAO {

    private static UsuarioDAO instance = null;
    private PreparedStatement selectNewId;
    private PreparedStatement selectUsuario;
    private PreparedStatement insertUsuario;
    private PreparedStatement updateUsuario;
    private PreparedStatement selectAll;

    public static UsuarioDAO getInstance() throws ClassNotFoundException, SQLException, SelectException{
        if ( instance == null)
            instance = new UsuarioDAO();
        return instance;
    } 

    private UsuarioDAO() throws ClassNotFoundException, SQLException, SelectException{
        Connection cox = Conexao.getConexao();
        selectNewId = cox.prepareStatement("select nextVal('id')");
        insertUsuario = cox.prepareStatement("insert into usuario values(?, ?, ?, ?, ?, ?)");
        selectUsuario = cox.prepareStatement("select * from usuario where id = ?");
        selectAll = cox.prepareStatement("select * from usuario");
    }

    private int selectNewId() throws SelectException{
        try{
            ResultSet rs = selectNewId.executeQuery();
            if(rs.next()){
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar novo id da tabela usuarios");
        }
        return 0;
    }

    public void updateUsuario(Usuario usuario) throws UpdateException{
        try{
            updateUsuario.setString(2, usuario.getNome());

        } catch (SQLException e) {
            throw new UpdateException("Erro ao atualizar!!!");
        }
    }

    public void insert(Usuario usuario) throws InsertException, SelectException{
        int novoId = selectNewId();
        try{
            insertUsuario.setInt(1, novoId);
            insertUsuario.setString(2, usuario.getNome());
            insertUsuario.setString(3, usuario.getEmail());
            insertUsuario.setString(4, usuario.getSenha());
            insertUsuario.setObject(5, usuario.getSeguidos());
            insertUsuario.setObject(6, usuario.getSeguidores());
            insertUsuario.setObject(7, usuario.getPost());
            insertUsuario.executeUpdate();
        } catch (SQLException e){
            throw new InsertException("Erro ao inserir Usuario!!!");
        }
    }

    public Usuario select(String email) throws SelectException{
        try{
            selectUsuario.setString(1, email);
            ResultSet rs = selectUsuario.executeQuery();
            if(rs.next()) {
                int id = rs.getInt(1);
                String nome = rs.getString(2);
                String senha = rs.getString(3);

                Usuario usuarioSelecionado = new Usuario();
                usuarioSelecionado.setId(id);
                usuarioSelecionado.setNome(nome);
                usuarioSelecionado.setEmail(email);
                usuarioSelecionado.setSenha(senha);
            
                return usuarioSelecionado;
            }

        }catch (SQLException e){
            throw new SelectException("Erro ao buscar endereço de pessoa");
        }
        return null;
    }


    public List<Usuario> selectAll() throws SelectException{

        List<Usuario> usuarios = new LinkedList<Usuario>();

        try{
            ResultSet rs = selectAll.executeQuery();
            while(rs.next()){
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt(1));
                usuario.setNome(rs.getString(2));
                usuario.setEmail(rs.getString(3));
                usuario.setSenha(rs.getString(5));

                usuarios.add(usuario);
            }
        } catch (SQLException e) {
            throw new SelectException("Erro ao buscar Usuario!!!");
        }
        return usuarios;
    }


    
}
