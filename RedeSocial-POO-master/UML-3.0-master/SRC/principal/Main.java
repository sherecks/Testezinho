package principal;

import negocios.Metodo;

public class Main {

    // O IMPORTANTE!!!
    public static void main(String[] args) {
        Metodo login = new Metodo();
        Screen frame1 = new Screen(login);
        frame1.setVisible(true);
    }

}
//Deu boa!!!