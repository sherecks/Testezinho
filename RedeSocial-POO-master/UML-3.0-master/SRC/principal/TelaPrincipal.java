package principal;

import javax.swing.*;
import java.awt.*;
import dados.*;
import negocios.Metodo;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;


import java.util.List;

public class TelaPrincipal extends JFrame {

    public Usuario posting;
    public Metodo posted;
    public Usuario usuarioLogado;

    private JButton usuariosButton;
    private JButton publicarButton;
    private JPanel exibirPosts;
    private JButton seguirButton;
    private JButton desseguirButton;

    private JPanel field;
    private JPanel interactionPanel;
    private JPanel vision;

    // Função para exibir a lista de usuários seguidos
    public void exibirUsuariosSeguidos(List<Usuario> usuariosSeguidos) {
        if (!usuariosSeguidos.isEmpty()) {
            StringBuilder mensagem = new StringBuilder("Usuários que você está seguindo:\n");

            // Mensagem com os nomes dos usuários seguidos
            for (Usuario usuario : usuariosSeguidos) {
                mensagem.append(usuario.getNome()).append("\n");
            }

            // Exibe a mensagem usando o JOptionPane
            JOptionPane.showMessageDialog(null, mensagem.toString());
        } else {
            JOptionPane.showMessageDialog(null, "Você não está seguindo nenhum usuário.");
        }
    }

    public TelaPrincipal(Metodo posted, Usuario usuarioLogado) {
        Container c = getContentPane();

        vision = new JPanel();
        field = new JPanel();
        vision.setLayout(new BorderLayout());
        vision.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        usuariosButton = new JButton("Usuário");
        field.add(usuariosButton, BorderLayout.NORTH);

        // Teste com ScrollBar
        exibirPosts = new JPanel();
        exibirPosts.setBackground(Color.GRAY);
        exibirPosts.setLayout(new BoxLayout(exibirPosts, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(exibirPosts);


        // Exibir a barra de rolagem sempre que necessário
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        vision.add(scrollPane, BorderLayout.CENTER);
 

        // Painel de intereção
        interactionPanel = new JPanel();

        publicarButton = new JButton("Publicar");
        interactionPanel.add(publicarButton);

        seguirButton = new JButton("Seguir");
        interactionPanel.add(seguirButton);

        desseguirButton = new JButton("Desseguir");
        interactionPanel.add(desseguirButton);

        c.add(field, BorderLayout.NORTH);
        c.add(vision, BorderLayout.CENTER);
        c.add(interactionPanel, BorderLayout.SOUTH);
        setSize(480, 640);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);



        // DEIXAR POST PRONTO ->  OK !!!


        // Criação do post de exemplo!!!
        Post postExemplo = new Post();
        postExemplo.setLegenda("Exemplo!!!");
        postExemplo.setAutor(posted.getUsuarios().get(1)); // Assume o segundo usuário!!!

        usuarioLogado.publicar(postExemplo);

        String caminhoImagem = "C:\\Users\\Jo\u00E3o Pedro\\Desktop\\RedeSocial-POO\\UML-3.0-master\\SRC\\principal\\IMAGEM.png";
        ImageIcon imagemExemplo = new ImageIcon(caminhoImagem);
        ImageIcon scaledIcon = new ImageIcon(imagemExemplo.getImage().getScaledInstance(260, 260, Image.SCALE_DEFAULT));
        
        postExemplo.setImagem(scaledIcon);

        // Cria a exibição do Post de exemplo!!!
        JPanel postLabelsPanel = new JPanel();
        JLabel postLabel01 = new JLabel(postExemplo.getLegenda());
        JLabel postLabel02 = new JLabel(postExemplo.getAutor().getNome());
        JLabel imagemLabel = new JLabel(scaledIcon);

        // Contador de curtidas!!!
        JLabel curtidasLabel = new JLabel("Curtidas: " + postExemplo.getCurtidas());
        JButton curtirButton = new JButton("Curtir");

        // Botão de curtir!!!
        curtirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Incrementa o contador de curtidas!!!
                postExemplo.curtir();

                // Atualiza o valor exibido no contador de curtidas!!!
                curtidasLabel.setText("Curtidas: " + postExemplo.getCurtidas());
            }
        });
 
        // Organizar Legenda e Autor!!!
        postLabelsPanel.setLayout(new BoxLayout(postLabelsPanel, BoxLayout.PAGE_AXIS));
        postLabelsPanel.add(postLabel01);
        postLabelsPanel.add(postLabel02);
        postLabelsPanel.add(curtidasLabel);
        postLabelsPanel.add(curtirButton);
        postLabelsPanel.setBackground(Color.GRAY);
        postLabelsPanel.setBorder(BorderFactory.createEmptyBorder(25, 155, 10, 155));

        // Cria um JPanel para Organizar o Post de exemplo!!!
        JPanel postPanel = new JPanel();
        postPanel.setLayout(new BorderLayout());
        postPanel.add(postLabelsPanel, BorderLayout.CENTER);
        postPanel.add(imagemLabel, BorderLayout.NORTH);
        postPanel.setBackground(Color.GRAY);

        // Adiciona o Post de exemplo à interface!!!
        exibirPosts.add(postPanel);
        exibirPosts.revalidate();
        exibirPosts.repaint();


        // EVENTO!!!
        seguirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeSeguido = JOptionPane.showInputDialog(null, "Digite o nome do usuário a ser seguido:");
               
                if (nomeSeguido != null && usuarioLogado != null) {
                    
                    Usuario seguido = null;
                    for (Usuario u : posted.getUsuarios()) {
                        if (u != null && u.getNome().equals(nomeSeguido)) {
                            seguido = u;
                            break;
                        }
                    }
        
                    if (seguido == null) {
                        JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
                    } else {
                        usuarioLogado.seguir(seguido);
                        JOptionPane.showMessageDialog(null, "Usuário seguido com sucesso!");
                    }
                } else {
                    // O usuário cancelou a entrada dos nomes!!!
                }
            }
        });

        // Publicar!!!
        publicarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String legenda = JOptionPane.showInputDialog(null, "Digite a legenda:");

                if (legenda != null && usuarioLogado != null) {
                    Post poster = new Post();
                    poster.setLegenda(legenda);
                    poster.setAutor(usuarioLogado);

                    // Seleciona a imagem usando JFileChooser
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.setFileFilter(new FileNameExtensionFilter("Imagens", "jpg", "jpeg", "png", "gif"));
                    int result = fileChooser.showOpenDialog(null);

                    if (result == JFileChooser.APPROVE_OPTION) {
                        String imagePath = fileChooser.getSelectedFile().getPath();
                        ImageIcon imageIcon = new ImageIcon(imagePath);
                        ImageIcon scaledIcon = new ImageIcon(imageIcon.getImage().getScaledInstance(260, 260, Image.SCALE_DEFAULT));
                        poster.setImagem(scaledIcon);
                        usuarioLogado.publicar(poster);
                        JOptionPane.showMessageDialog(null, "Post publicado com sucesso!");
                        
                        // Cria a exibição do Post!!!
                        JPanel postLabelsPanel = new JPanel();
                        JLabel postLabel01 = new JLabel(poster.getLegenda());
                        JLabel postLabel02 = new JLabel(usuarioLogado.getNome());
                        JLabel imagemLabel = new JLabel(scaledIcon);
        
                        // Contador de curtidas
                        JLabel curtidasLabel = new JLabel("Curtidas: " + poster.getCurtidas());
                        JButton curtirButton = new JButton("Curtir");

                         // Botão de curtir!!!
                        curtirButton.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent e) {
                                
                                // Incrementa o contador de curtidas
                                poster.curtir();

                                // Atualiza o valor exibido no contador de curtidas
                                curtidasLabel.setText("Curtidas: " + poster.getCurtidas());
                            }
                        });



                        // Organizar Legenda e Autor!!!
                        postLabelsPanel.setLayout(new BoxLayout(postLabelsPanel, BoxLayout.PAGE_AXIS));
                        postLabelsPanel.add(postLabel01);
                        postLabelsPanel.add(postLabel02);
                        postLabelsPanel.add(curtidasLabel);
                        postLabelsPanel.add(curtirButton);
                        postLabelsPanel.setBackground(Color.GRAY);
                        postLabelsPanel.setBorder(BorderFactory.createEmptyBorder(25, 155, 10, 155));



                        // Cria um JPanel para Organizar!!!
                        JPanel postPanel = new JPanel();
                        postPanel.setLayout(new BorderLayout());
                        postPanel.add(postLabelsPanel, BorderLayout.CENTER);
                        postPanel.add(imagemLabel, BorderLayout.NORTH);
                        postPanel.setBackground(Color.GRAY);


                        exibirPosts.add(postPanel);
                        exibirPosts.revalidate();
                        exibirPosts.repaint();
                    }            
                } else {
                    // O usuário cancelou a entrada dos dados
                    JOptionPane.showMessageDialog(null, "Erro!");
                }
             }
        });

        // Desseguir!!!
        desseguirButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nomeEXSeguido = JOptionPane.showInputDialog(null, "Digite o nome do usuário a desseguir:");
                
                if (nomeEXSeguido != null && usuarioLogado != null) {
                    
                    Usuario seguido = null;
                    for (Usuario u : posted.getUsuarios()) {
                        if (u != null && u.getNome().equals(nomeEXSeguido)) {
                            seguido = u;
                            break;
                        }
                    }
        
                    if (seguido == null) {
                        JOptionPane.showMessageDialog(null, "Usuário não encontrado.");
                    } else {
                        usuarioLogado.desseguir(seguido);
                        JOptionPane.showMessageDialog(null, "Você não segue mais esse usuário!");

                        List<Usuario> usuariosSeguidos = usuarioLogado.getSeguidos();
                        exibirUsuariosSeguidos(usuariosSeguidos);
                    }
                } else {
                    // O usuário cancelou a entrada dos nomes
                }
            }
        });

        usuariosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obtem a lista de usuários que você está seguindo
                List<Usuario> usuariosSeguidos = usuarioLogado.getSeguidos();
        
                // Verifique se há usuários seguidos
                if (!usuariosSeguidos.isEmpty()) {
                    StringBuilder mensagem = new StringBuilder("Usuários que você está seguindo:\n");
                    
                    // Nomes dos usuários seguidos
                    for (Usuario usuario : usuariosSeguidos) {
                        mensagem.append(usuario.getNome()).append("\n");
                    }
        
                    // Exibe a mensagem usando o JOptionPane
                    JOptionPane.showMessageDialog(null, mensagem.toString());
                } else {
                    JOptionPane.showMessageDialog(null, "Você não está seguindo nenhum usuário.");
                }
            }
        });


    }

}
