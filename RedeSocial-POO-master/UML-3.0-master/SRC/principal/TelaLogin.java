package principal;

import negocios.Metodo;
import dados.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class TelaLogin extends JFrame {

    public String email;
    public String senha;
    public Metodo login;

    private JPanel field;
    private JPanel fieldText;
    private JPanel fieldButton;

    public JTextField emailUsuario;
    public JTextField senhaUsuario;
    private JLabel tituloLabel, titulo1;
    private JButton loginUsuario; 
    

    public TelaLogin(Metodo login){

        field = new JPanel();
        fieldText = new JPanel();
        fieldButton = new JPanel();
        field.setBorder(BorderFactory.createEmptyBorder(200, 20, 10, 20));
        field.setBackground(Color.GRAY);

        Container c = getContentPane();

        titulo1 = new JLabel("Digite Email e Senha:");
        field.add(titulo1);

        // TEXTFIELD
        tituloLabel = new JLabel("LOGIN");
        fieldText.add(tituloLabel, BorderLayout.CENTER);

        emailUsuario = new JTextField();
        emailUsuario.setColumns(20);
        field.add(emailUsuario, BorderLayout.CENTER);

        senhaUsuario = new JTextField();
        senhaUsuario.setColumns(20);
        field.add(senhaUsuario, BorderLayout.CENTER);

        loginUsuario = new JButton("Login");
        fieldButton.add(loginUsuario, BorderLayout.CENTER); 

        c.add(field, BorderLayout.CENTER);
        c.add(fieldText, BorderLayout.NORTH);
        c.add(fieldButton, BorderLayout.SOUTH);
        setSize(400, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // EVENTO
        loginUsuario.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e){

                email = emailUsuario.getText().trim(); 
                senha = senhaUsuario.getText().trim();
            
                if (email.isEmpty() || senha.isEmpty()) {
                    JOptionPane.showMessageDialog(field, "Usuário não encontrado ou senha incorreta.");
                    
                } else {

                    // Cadastro de Alguns Usuarios !!!
                    login.cadastrarUser("Joao", "Joao@", "123");
                    login.cadastrarUser("Pietra", "Pietra@", "456");

                    // Exibir informações do usuário!!!
                    String mensagem = "Usuário:\n" +
                    "Email: " + email; 
                    mensagem += "\n\nCadastrados:\n";
                    List<Usuario> usuarios = login.getUsuarios();
                    for (Usuario usuario : usuarios) {
                        mensagem += "Nome: " + usuario.getNome() + ", Email: " + usuario.getEmail() + "\n";
                    }

                    JOptionPane.showMessageDialog(null, mensagem);

                   Usuario usuarioLogado = login.fazerLogin(email, senha);
                   String test = "O resultado: " + usuarioLogado;
                   JOptionPane.showMessageDialog(null, test);

                   if(usuarioLogado != null){
                        TelaPrincipal tela = new TelaPrincipal(login, usuarioLogado);
                        tela.setVisible(true);
                        setVisible(false);
                   } else {
                    JOptionPane.showMessageDialog(field, "Usuário não encontrado ou senha incorreta.");
                   }
                }
                     
            }
        });
    }
}
