package dados;

import java.util.ArrayList;
import java.util.List;

public class Usuario {

    private Integer id;
    private String nome;
    private String email;
    private String senha;

    private List<Usuario> seguidos = new ArrayList<>();
    private List<Usuario> seguidores = new ArrayList<>();
    private List<Post> posts = new ArrayList<>();

    //Getters & Setters

    //1°
    public void setId(Integer id) {
        this.id = id;
    }

    public void setNome(String nome){
        this.nome = nome;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public void setSenha(String senha){
        this.senha = senha;
    }

    public void setSeguidos(List<Usuario> seguidos){
        this.seguidos = seguidos;
    }

    public void setSeguidores(List<Usuario> seguidores){
        this.seguidores = seguidores;
    }

    public void setPost(List<Post> posts){
        this.posts = posts;
    }

    //2°
    public Integer getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getEmail() {
        return email;
    }
    public String getSenha() {
        return senha;
    }
    public List<Usuario> getSeguidores(){
        return seguidores;
    }
    public List<Usuario> getSeguidos(){
        return seguidos;
    }
    public List<Post> getPost(){
        return posts;
    }

    //Funçãozinha
    public void seguir(Usuario usuario){
        seguidos.add(usuario);
        usuario.getSeguidores().add(this);
    }

    public void desseguir(Usuario usuario){
        seguidos.remove(usuario);
        usuario.getSeguidores().remove(this);
    }

    public void publicar(Post post) {
        posts.add(post);
    }

    public List<Post> mostrarPosts() { 
        return posts;
    }
    
    //toString
     public String toString(){
        return "\nNome = " + nome + "\nEmail = " + email + "\nSenha = " + senha + "\n";
    }
    
}
