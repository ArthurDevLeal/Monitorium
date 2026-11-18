package monitorium.comum.sessao;

import monitorium.usuario.model.Usuario;

/** Guarda quem está logado. Qualquer classe descobre com UsuarioLogado.get(). */
public final class UsuarioLogado {

    private static Usuario atual;

    private UsuarioLogado() {
    }

    public static Usuario get() {
        return atual;
    }

    public static void set(Usuario usuario) {
        atual = usuario;
    }

    public static void limpar() {
        atual = null;
    }

    public static boolean estaLogado() {
        return atual != null;
    }
}
