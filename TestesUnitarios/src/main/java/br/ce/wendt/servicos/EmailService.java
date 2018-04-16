package br.ce.wendt.servicos;

import br.ce.wendt.entidades.Usuario;

public interface EmailService {

    public void notificarAtraso(Usuario usuario);

}
