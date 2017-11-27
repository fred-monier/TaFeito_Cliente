package br.pe.recife.tafeito.service;

import android.content.Context;

import java.util.List;
import br.pe.recife.tafeito.R;
import br.pe.recife.tafeito.dao.ServicoDAO;
import br.pe.recife.tafeito.excecao.InfraException;
import br.pe.recife.tafeito.excecao.NegocioException;
import br.pe.recife.tafeito.negocio.Fornecedor;
import br.pe.recife.tafeito.negocio.Servico;
import br.pe.recife.tafeito.negocio.ServicoCategoria;

public class ServicoService {

    private static ServicoService instancia;
    private ServicoDAO servicoDao;

    public static ServicoService getInstancia(Context context) {

        if (instancia == null) {
            instancia = new ServicoService(context);
        }

        return instancia;
    }

    private ServicoService(Context context) {
        this.servicoDao = ServicoDAO.getInstancia(context);
    }

    public void salvar(Servico servico, Context contexto) throws InfraException, NegocioException {

        if(servico == null) {
            throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nulo).toString());
        }

        try {
            servicoDao.salvar(servico);
        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }
    }

    public Servico consultar(long id, Context contexto) throws InfraException, NegocioException {

        Servico res = null;

        try {

            res = servicoDao.consultar(id);

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

    public int excluir(Servico servico, Context contexto) throws InfraException, NegocioException{

        int res = 0;

        try {

            res = servicoDao.excluir(servico);

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

    public List<Servico> listar() throws InfraException{

        try {
            return servicoDao.listar();
        }catch (Exception e){
            throw new InfraException(e.getMessage(),e);
        }
    }

    public List<Servico> listarPorServicoCategoria(ServicoCategoria servCat) throws InfraException{

        try {
            return servicoDao.listarPorServicoCategoria(servCat);
        }catch (Exception e){
            throw new InfraException(e.getMessage(),e);
        }
    }

    public List<Servico> listarPorFornecedor(Fornecedor forn) throws InfraException{

        try {
            return servicoDao.listarPorFornecedor(forn);
        }catch (Exception e){
            throw new InfraException(e.getMessage(),e);
        }
    }

    public List<Servico> listarPorServicoCategoriaPorFornecedor(ServicoCategoria servCat, Fornecedor forn) throws InfraException{

        try {
            return servicoDao.listarPorServicoCategoriaPorFornecedor(servCat, forn);
        }catch (Exception e){
            throw new InfraException(e.getMessage(),e);
        }
    }
}
