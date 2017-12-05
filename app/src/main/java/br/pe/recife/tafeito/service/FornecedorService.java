package br.pe.recife.tafeito.service;

import android.content.Context;

import java.util.List;

import br.pe.recife.tafeito.R;

import br.pe.recife.tafeito.dao.FornecedorDAO;
import br.pe.recife.tafeito.excecao.InfraException;
import br.pe.recife.tafeito.excecao.NegocioException;

import br.pe.recife.tafeito.negocio.Fornecedor;
import br.pe.recife.tafeito.negocio.ServicoCategoria;

public class FornecedorService {

    private static FornecedorService instancia;

    private FornecedorDAO fornecedorDao;

    private UsuarioService usuarioService;

    public static FornecedorService getInstancia(Context context) {

        if (instancia == null) {
            instancia = new FornecedorService(context);
        }

        return instancia;
    }

    private FornecedorService(Context context) {
        this.fornecedorDao = FornecedorDAO.getInstancia(context);
        this.usuarioService = UsuarioService.getInstancia(context);
    }

    public void salvar(Fornecedor fornecedor, Context contexto) throws InfraException, NegocioException {

        if(fornecedor == null) {
            throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nulo).toString());
        }

        try {

            boolean novo = false;
            if (fornecedor.getId() == 0) {
                novo = true;
            }

            usuarioService.salvar(fornecedor, contexto);
            fornecedorDao.salvar(fornecedor, novo);

        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }
    }

    public Fornecedor consultar(long id, Context contexto) throws InfraException, NegocioException {

        Fornecedor res = null;

        try {

            res = fornecedorDao.consultar(id);

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

    public int excluir(Fornecedor fornecedor, Context contexto) throws InfraException, NegocioException{

        int res = 0;

        try {

            res = fornecedorDao.excluir(fornecedor);
            if (res <= 0) {
                throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nao_excluido).toString());
            }

            res = usuarioService.excluir(fornecedor, contexto);
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

    public List<Fornecedor> listar() throws InfraException{

        try {
            return fornecedorDao.listar();
        }catch (Exception e){
            throw new InfraException(e.getMessage(),e);
        }
    }

    public List<Fornecedor> listarPorServicoCategoria(ServicoCategoria servCat) throws InfraException{

        try {
            return fornecedorDao.listarPorServicoCategoria(servCat);
        }catch (Exception e){
            throw new InfraException(e.getMessage(),e);
        }
    }
}
