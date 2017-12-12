package br.pe.recife.tafeito.service;

import android.content.Context;

import java.util.List;

import br.pe.recife.tafeito.R;
import br.pe.recife.tafeito.dao.UsuarioDAO;
import br.pe.recife.tafeito.excecao.InfraException;
import br.pe.recife.tafeito.excecao.NegocioException;
import br.pe.recife.tafeito.negocio.Usuario;

public class UsuarioService {

    private static UsuarioService instancia;
    private UsuarioDAO usuarioDao;

    public static UsuarioService getInstancia(Context context) {

        if (instancia == null) {
            instancia = new UsuarioService(context);
        }

        return instancia;
    }

    private UsuarioService(Context context) {
        this.usuarioDao = UsuarioDAO.getInstancia(context);
    }

    public void salvar(Usuario usuario, Context contexto) throws InfraException, NegocioException {

        if(usuario == null) {
            throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nulo).toString());
        }

        try {
            usuarioDao.salvar(usuario);
        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }
    }

    public Usuario consultar(long id, Context contexto) throws InfraException, NegocioException {

        Usuario res = null;

        try {

            res = usuarioDao.consultar(id);

            if (res == null) {
                throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nao_encontrado).toString());
            }
        } catch (NegocioException e) {
            throw  e;
        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }

        return res;

    }

    public int excluir(Usuario usuario, Context contexto) throws InfraException, NegocioException{

        int res = 0;

        try {

            res = usuarioDao.excluir(usuario);

            if (res <= 0) {
                throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nao_excluido).toString());
            }
        } catch (NegocioException e) {
            throw  e;
        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }

        return res;

    }

    public List<Usuario> listar() throws InfraException{

        try {
            return usuarioDao.listar();
        }catch (Exception e){
            throw new InfraException(e.getMessage(),e);
        }
    }
}
