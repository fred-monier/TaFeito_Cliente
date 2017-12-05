package br.pe.recife.tafeito.service;

import android.content.Context;

import java.util.List;
import br.pe.recife.tafeito.R;
import br.pe.recife.tafeito.dao.ServicoCategoriaDAO;
import br.pe.recife.tafeito.excecao.InfraException;
import br.pe.recife.tafeito.excecao.NegocioException;
import br.pe.recife.tafeito.negocio.Fornecedor;
import br.pe.recife.tafeito.negocio.ServicoCategoria;

public class ServicoCategoriaService {

    private static ServicoCategoriaService instancia;
    private ServicoCategoriaDAO servicoCategoriaDao;

    public static ServicoCategoriaService getInstancia(Context context) {

        if (instancia == null) {
            instancia = new ServicoCategoriaService(context);
        }

        return instancia;
    }

    private ServicoCategoriaService(Context context) {
        this.servicoCategoriaDao = ServicoCategoriaDAO.getInstancia(context);
    }

    public void salvar(ServicoCategoria servicoCategoria, Context contexto) throws InfraException, NegocioException {

        if(servicoCategoria == null) {
            throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nulo).toString());
        }

        try {
            servicoCategoriaDao.salvar(servicoCategoria);
        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }
    }

    public ServicoCategoria consultar(long id, Context contexto) throws InfraException, NegocioException {

        ServicoCategoria res = null;

        try {

            res = servicoCategoriaDao.consultar(id);

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

    public int excluir(ServicoCategoria servicoCategoria, Context contexto) throws InfraException, NegocioException{

        int res = 0;

        try {

            res = servicoCategoriaDao.excluir(servicoCategoria);

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

    public List<ServicoCategoria> listar() throws InfraException {

        try {
            return servicoCategoriaDao.listar();
        }catch (Exception e){
            throw new InfraException(e.getMessage(),e);
        }
    }

    public List<ServicoCategoria> listarPorFornecedor(Fornecedor forn)  throws InfraException {

        try {
            return servicoCategoriaDao.listarPorFornecedor(forn);
        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }
    }

}
