package br.pe.recife.tafeito.service;

import android.content.Context;

import java.util.List;

import br.pe.recife.tafeito.R;
import br.pe.recife.tafeito.dao.AcessoDAO;
import br.pe.recife.tafeito.excecao.InfraException;
import br.pe.recife.tafeito.excecao.NegocioException;
import br.pe.recife.tafeito.negocio.Acesso;
import br.pe.recife.tafeito.negocio.Autenticacao;
import br.pe.recife.tafeito.negocio.Cliente;
import br.pe.recife.tafeito.negocio.Fornecedor;
import br.pe.recife.tafeito.negocio.Usuario;

public class AcessoService {

    private static AcessoService instancia;
    private AcessoDAO acessoDao;

    private FornecedorService fornecedorService;
    private ClienteService clienteService;

    public static AcessoService getInstancia(Context context) {

        if (instancia == null) {
            instancia = new AcessoService(context);
        }

        return instancia;
    }

    private AcessoService(Context context) {
        this.acessoDao = AcessoDAO.getInstancia(context);
        this.fornecedorService = FornecedorService.getInstancia(context);
        this.clienteService = ClienteService.getInstancia(context);
    }

    public Autenticacao inserir(Acesso acesso, Usuario usuario, Context contexto) throws InfraException, NegocioException {

        Autenticacao res = null;

        if (acesso == null || usuario == null) {
            throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nulo).toString());
        }

        try {

            if (usuario instanceof Fornecedor) {
                fornecedorService.salvar((Fornecedor) usuario, contexto);
            } else {
                clienteService.salvar((Cliente) usuario, contexto);
            }

            acesso.setId(usuario.getId());
            acessoDao.inserir(acesso);

            res = new Autenticacao();
            res.setIdAcesso(acesso.getId());
            res.setToken("");

        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }

        return res;
    }

    public Autenticacao atualizar(Acesso acesso, Usuario usuario, Context contexto) throws InfraException, NegocioException {

        Autenticacao res = null;

        if (acesso == null || usuario == null) {
            throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nulo).toString());
        }

        try {

            if (usuario instanceof Fornecedor) {
                fornecedorService.salvar((Fornecedor) usuario, contexto);
            } else {
                clienteService.salvar((Cliente) usuario, contexto);
            }

            acessoDao.atualizar(acesso);

            res = new Autenticacao();
            res.setIdAcesso(acesso.getId());
            res.setToken("");

        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }

        return res;
    }

    public Autenticacao buscarPorLoginPorSenhaFornecedor(String login, String senha, Context contexto) throws InfraException, NegocioException {

        Autenticacao res = null;

        try {

            long id = acessoDao.buscarPorLoginPorSenhaFornecedor(login, senha);

            if (id <= 0) {
                throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nao_encontrado).toString());
            }

            res = new Autenticacao();
            res.setIdAcesso(id);
            res.setToken("");

        } catch (NegocioException e) {
            throw  e;
        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }

        return res;

    }

    public Autenticacao buscarPorLoginPorSenhaCliente(String login, String senha, Context contexto) throws InfraException, NegocioException {

        Autenticacao res = null;

        try {

            long id = acessoDao.buscarPorLoginPorSenhaCliente(login, senha);

            if (id <= 0) {
                throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nao_encontrado).toString());
            }

            res = new Autenticacao();
            res.setIdAcesso(id);
            res.setToken("");

        } catch (NegocioException e) {
            throw  e;
        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }

        return res;

    }

    public boolean existePorLogin(String login) throws InfraException {

        boolean res = false;

        try {

            res = acessoDao.existePorLogin(login);

        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }

        return res;
    }

    public Acesso consultar(long id, Context contexto) throws InfraException, NegocioException {

        Acesso res = null;

        try {

            res = acessoDao.consultar(id);

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

    public int excluirCliente(Acesso acesso, Context contexto) throws InfraException, NegocioException{

        int res = 0;

        try {

            res = acessoDao.excluir(acesso);
            if (res <= 0) {
                throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nao_excluido).toString());
            }

            res = clienteService.excluir(clienteService.consultar(acesso.getId(), contexto), contexto);
            if (res <= 0) {
                throw new NegocioException(contexto.getResources().
                        getText(R.string.excecao_objeto_nao_excluido).toString());
            }

        } catch (NegocioException e) {
            throw  e;
        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }

        return res;

    }

    public int excluirFornecedor(Acesso acesso, Context contexto) throws InfraException, NegocioException{

        int res = 0;

        try {

            res = acessoDao.excluir(acesso);
            if (res <= 0) {
                throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nao_excluido).toString());
            }

            res = fornecedorService.excluir(fornecedorService.consultar(acesso.getId(), contexto), contexto);
            if (res <= 0) {
                throw new NegocioException(contexto.getResources().
                        getText(R.string.excecao_objeto_nao_excluido).toString());
            }

        } catch (NegocioException e) {
            throw  e;
        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }

        return res;

    }

    public List<Acesso> listar() throws InfraException {

        try {
            return acessoDao.listar();
        }catch (Exception e){
            throw new InfraException(e.getMessage(),e);
        }
    }
}
