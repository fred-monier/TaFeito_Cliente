package br.pe.recife.tafeito.fachada;

import android.content.Context;

import java.util.List;

import br.pe.recife.tafeito.excecao.InfraException;
import br.pe.recife.tafeito.excecao.NegocioException;
import br.pe.recife.tafeito.negocio.Acesso;
import br.pe.recife.tafeito.negocio.Agendamento;
import br.pe.recife.tafeito.negocio.Autenticacao;
import br.pe.recife.tafeito.negocio.Cliente;
import br.pe.recife.tafeito.negocio.Fornecedor;
import br.pe.recife.tafeito.negocio.Oferta;
import br.pe.recife.tafeito.negocio.Servico;
import br.pe.recife.tafeito.negocio.ServicoCategoria;
import br.pe.recife.tafeito.negocio.Usuario;

public interface IFachadaTaFeito {

    //AcessoService
    Autenticacao inserirAcesso(Acesso acesso, Usuario usuario, Context contexto) throws InfraException, NegocioException;
    Autenticacao atualizarAcesso(Acesso acesso, Usuario usuario, Context contexto) throws InfraException, NegocioException;
    Autenticacao buscarPorLoginPorSenhaFornecedorAcesso(String login, String senha, Context contexto) throws InfraException, NegocioException;
    Autenticacao buscarPorLoginPorSenhaClienteAcesso(String login, String senha, Context contexto) throws InfraException, NegocioException;
    boolean existePorLoginAcesso(String login) throws InfraException;
    Acesso consultarAcesso(long id, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException;
    int excluirAcessoCliente(Acesso acesso, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException;
    int excluirAcessoFornecedor(Acesso acesso, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException;
    List<Acesso> listarAcesso() throws InfraException;

    //FornecedorService
    void salvarFornecedor(Fornecedor fornecedor, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException;
    Fornecedor consultarFornecedor(long id, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException;
    int excluirFornecedor(Fornecedor fornecedor, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException;
    List<Fornecedor> listarFornecedor(Autenticacao autenticacao) throws InfraException;
    List<Fornecedor> listarPorServicoCategoriaFornecedor(ServicoCategoria servicoCategoria, Autenticacao autenticacao) throws InfraException;//********

    //ClienteService
    void salvarCliente(Cliente cliente, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException;
    Cliente consultarCliente(long id, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException;
    int excluirCliente(Cliente cliente, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException;
    List<Cliente> listarCliente(Autenticacao autenticacao) throws InfraException;

    //ServicoCategoriaService
    void salvarServicoCategoria(ServicoCategoria servicoCategoria, Context contexto, Autenticacao autenticacao)throws InfraException, NegocioException;
    ServicoCategoria consultarServicoCategoria(long id, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException;
    int excluirServicoCategoria(ServicoCategoria servicoCategoria, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException;
    List<ServicoCategoria> listarServicoCategoria(Autenticacao autenticacao) throws InfraException;
    List<ServicoCategoria> listarPorFornecedorServicoCategoria(Fornecedor forn, Autenticacao autenticacao)  throws InfraException;//********

    //ServicoService
    void salvarServico(Servico servico, Context contexto, Autenticacao autenticacao)throws InfraException, NegocioException;
    Servico consultarServico  (long id, Context contexto, Autenticacao autenticacao) throws InfraException,NegocioException;
    int excluirServico(Servico servico, Context contexto, Autenticacao autenticacao) throws InfraException,NegocioException;
    List<Servico> listarServico(Autenticacao autenticacao) throws InfraException;
    List<Servico> listarPorServicoCategoriaServico(ServicoCategoria servCat, Autenticacao autenticacao) throws InfraException;//********
    List<Servico> listarPorFornecedorServico(Fornecedor forn, Autenticacao autenticacao) throws InfraException;//********
    List<Servico> listarPorServicoCategoriaPorFornecedorServico(ServicoCategoria servicoCat, Fornecedor forn, Autenticacao autenticacao)//********
            throws InfraException;

    //OfertaService
    void salvarOferta(Oferta oferta, Context contexto, Autenticacao autenticacao) throws InfraException,NegocioException;
    Oferta consultarOferta(long id, Context contexto, Autenticacao autenticacao) throws InfraException,NegocioException;
    int excluirOferta(Oferta oferta, Context contexto, Autenticacao autenticacao) throws InfraException,NegocioException;
    List<Oferta> listarOferta(Autenticacao autenticacao) throws InfraException;

    //AgendamentoService
    void salvarAgendamento(Agendamento agendamento, Context contexto, Autenticacao autenticacao) throws InfraException,NegocioException;
    Agendamento consultarAgendamento (long id, Context contexto, Autenticacao autenticacao) throws InfraException,NegocioException;
    int excluirAgendamento(Agendamento agendamento, Context contexto, Autenticacao autenticacao) throws InfraException,NegocioException;
    List<Agendamento> listarAgendamento(Autenticacao autenticacao) throws InfraException;

}
