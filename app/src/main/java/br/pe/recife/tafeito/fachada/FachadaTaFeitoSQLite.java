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
import br.pe.recife.tafeito.service.AcessoService;
import br.pe.recife.tafeito.service.AgendamentoService;
import br.pe.recife.tafeito.service.ClienteService;
import br.pe.recife.tafeito.service.FornecedorService;
import br.pe.recife.tafeito.service.OfertaService;
import br.pe.recife.tafeito.service.ServicoCategoriaService;
import br.pe.recife.tafeito.service.ServicoService;

public class FachadaTaFeitoSQLite implements IFachadaTaFeito {

    private static FachadaTaFeitoSQLite instancia;

    private AcessoService acessoService;
    private FornecedorService fornecedorService;
    private ClienteService clienteService;
    private ServicoCategoriaService servicoCategoriaService;
    private ServicoService servicoService;
    private OfertaService ofertaService;
    private AgendamentoService agendamentoService;

    public static FachadaTaFeitoSQLite getInstancia(Context context){

        if(instancia == null) {
            instancia = new FachadaTaFeitoSQLite(context);
        }

        return instancia;
    }

    private FachadaTaFeitoSQLite(Context context) {

        this.acessoService = AcessoService.getInstancia(context);
        this.fornecedorService = FornecedorService.getInstancia(context);
        this.clienteService = ClienteService.getInstancia(context);
        this.servicoCategoriaService = ServicoCategoriaService.getInstancia(context);
        this.servicoService = ServicoService.getInstancia(context);
        this.ofertaService = OfertaService.getInstancia(context);
        this.agendamentoService = AgendamentoService.getInstancia(context);
    }

    @Override
    public Autenticacao inserirAcesso(Acesso acesso, Usuario usuario, Context contexto) throws InfraException, NegocioException {

        return this.acessoService.inserir(acesso, usuario, contexto);
    }

    @Override
    public Autenticacao atualizarAcesso(Acesso acesso, Usuario usuario, Context contexto) throws InfraException, NegocioException {

        return this.acessoService.inserir(acesso, usuario, contexto);
    }

    @Override
    public Autenticacao buscarPorLoginPorSenhaFornecedorAcesso(String login, String senha, Context contexto) throws InfraException, NegocioException {
        return this.acessoService.buscarPorLoginPorSenhaFornecedor(login, senha, contexto);
    }

    @Override
    public Autenticacao buscarPorLoginPorSenhaClienteAcesso(String login, String senha, Context contexto) throws InfraException, NegocioException {
        return this.acessoService.buscarPorLoginPorSenhaCliente(login, senha, contexto);
    }

    @Override
    public boolean existePorLoginAcesso(String login) throws InfraException {
        return this.acessoService.existePorLogin(login);
    }

    @Override
    public List<Acesso> listarAcesso() throws InfraException {
        return this.acessoService.listar();
    }

    @Override
    public void salvarAgendamento(Agendamento agendamento, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        this.agendamentoService.salvar(agendamento, contexto);
    }

    @Override
    public Agendamento consultarAgendamento(long id, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        return this.agendamentoService.consultar(id, contexto);

    }

    @Override
    public int excluirAgendamento(Agendamento agendamento, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        return this.agendamentoService.excluir(agendamento, contexto);
    }

    @Override
    public List<Agendamento> listarAgendamento(Autenticacao autenticacao) throws InfraException {

        return this.agendamentoService.listar();
    }

    @Override
    public void salvarCliente(Cliente cliente, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        this.clienteService.salvar(cliente, contexto);
    }

    @Override
    public Cliente consultarCliente(long id, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        return this.clienteService.consultar(id, contexto);
    }

    @Override
    public int excluirCliente(Cliente cliente, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        return this.clienteService.excluir(cliente, contexto);
    }

    @Override
    public List<Cliente> listarCliente(Autenticacao autenticacao) throws InfraException {

        return this.clienteService.listar();
    }

    @Override
    public void salvarFornecedor(Fornecedor fornecedor, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        this.fornecedorService.salvar(fornecedor, contexto);
    }

    @Override
    public Fornecedor consultarFornecedor(long id, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        return this.fornecedorService.consultar(id, contexto);
    }

    @Override
    public int excluirFornecedor(Fornecedor fornecedor, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        return this.fornecedorService.excluir(fornecedor, contexto);
    }

    @Override
    public List<Fornecedor> listarFornecedor(Autenticacao autenticacao) throws InfraException {

        return this.fornecedorService.listar();
    }

    @Override
    public List<Fornecedor> listarPorServicoCategoriaFornecedor(ServicoCategoria servicoCategoria, Autenticacao autenticacao) throws InfraException {

        return this.fornecedorService.listarPorServicoCategoria(servicoCategoria);
    }

    @Override
    public void salvarOferta(Oferta oferta, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        this.ofertaService.salvar(oferta, contexto);
    }

    @Override
    public Oferta consultarOferta(long id, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        return this.ofertaService.consultar(id, contexto);
    }

    @Override
    public int excluirOferta(Oferta oferta, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        return this.ofertaService.excluir(oferta, contexto);
    }

    @Override
    public List<Oferta> listarOferta(Autenticacao autenticacao) throws InfraException {

        return this.ofertaService.listar();
    }

    @Override
    public void salvarServicoCategoria(ServicoCategoria servicoCategoria, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        this.servicoCategoriaService.salvar(servicoCategoria, contexto);
    }

    @Override
    public ServicoCategoria consultarServicoCategoria(long id, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        return this.servicoCategoriaService.consultar(id, contexto);
    }

    @Override
    public int excluirServicoCategoria(ServicoCategoria servicoCategoria, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        return this.servicoCategoriaService.excluir(servicoCategoria, contexto);
    }

    @Override
    public List<ServicoCategoria> listarServicoCategoria(Autenticacao autenticacao) throws InfraException {

        return this.servicoCategoriaService.listar();
    }

    @Override
    public List<ServicoCategoria> listarPorFornecedorServicoCategoria(Fornecedor forn, Autenticacao autenticacao)  throws InfraException {

        return this.servicoCategoriaService.listarPorFornecedor(forn);
    }

    @Override
    public void salvarServico(Servico servico, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        this.servicoService.salvar(servico, contexto);
    }

    @Override
    public Servico consultarServico(long id, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        return this.servicoService.consultar(id, contexto);
    }

    @Override
    public int excluirServico(Servico servico, Context contexto, Autenticacao autenticacao) throws InfraException, NegocioException {

        return this.servicoService.excluir(servico, contexto);
    }

    @Override
    public List<Servico> listarServico(Autenticacao autenticacao) throws InfraException {

        return this.servicoService.listar();
    }

    @Override
    public List<Servico> listarPorServicoCategoriaServico(ServicoCategoria servCat, Autenticacao autenticacao) throws InfraException {

        return this.servicoService.listarPorServicoCategoria(servCat);
    }

    @Override
    public List<Servico> listarPorFornecedorServico(Fornecedor forn, Autenticacao autenticacao) throws InfraException {

        return this.servicoService.listarPorFornecedor(forn);
    }

    @Override
    public List<Servico> listarPorServicoCategoriaPorFornecedorServico(ServicoCategoria servicoCat, Fornecedor forn, Autenticacao autenticacao)
            throws InfraException {
        return this.servicoService.listarPorServicoCategoriaPorFornecedor(servicoCat, forn);
    }
}
