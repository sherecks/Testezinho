package negocios;

import dados.Usuario;
import excecoes.InsertException;
import excecoes.SelectException;
import excecoes.UpdateException;
import persistencia.PostDAO;
import persistencia.UsuarioDAO;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

public class Metodo {

    private List<Usuario> usuarios = new ArrayList<>();
    int contUser = 0;

    private UsuarioDAO usuarioDAO;
    private PostDAO postDAO;
    private Usuario usuarioLogado;

    public List<Usuario> getUsuarios() {
        return this.usuarios;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }
    
    public void setUsuarioLogado(Usuario usuario) {
        usuarioLogado = usuario;
    }   

    private int idext = 1;

    // Metodo!!!
    public Metodo() {
        try {
            usuarioDAO = UsuarioDAO.getInstance();
            postDAO = PostDAO.getInstance();
            usuarios = usuarioDAO.selectAll();
        } catch (ClassNotFoundException | SQLException | SelectException e) {
            e.printStackTrace();
        }
    }

    // Funçãozinha
    public void cadastrarUser(String nome, String email, String senha) {
        try {
        Usuario usuario = new Usuario();
        usuario.setId(idext);
        usuario.setNome(nome);
        usuario.setEmail(email);
        usuario.setSenha(senha);
        if (usuarioDAO != null) {
            usuarioDAO.insert(usuario);
            usuarios.add(usuario);
        } else {
            throw new InsertException("Erro ao inserir usuário. Objeto usuarioDAO é nulo.");
        }
        idext++;
    } catch (InsertException | SelectException e) {
        e.printStackTrace();
    }
    }

    public List<Usuario> mostrarUsuarios() {
        return this.usuarios;
    }

    public void seguir(Usuario usuario){
       try {
            usuarioLogado.seguir(usuario);
            usuarioDAO.updateUsuario(usuario);
        } catch (UpdateException e) {
            e.printStackTrace();
        }
    }
    public void desseguir(Usuario usuario){
        try {
            usuarioLogado.desseguir(usuario);
            usuarioDAO.updateUsuario(usuario);
        } catch (UpdateException e) {
            e.printStackTrace();
        }
    }

    public Usuario fazerLogin(String email, String senha) {
        try {
            for (Usuario user : this.usuarios) {
                if (user.getEmail().trim().equals(email.trim()) && user.getSenha().trim().equals(senha.trim())) {
                    setUsuarioLogado(user);
                    return user;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
