package br.pe.recife.tafeito.gui;

import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import br.pe.recife.tafeito.R;
import br.pe.recife.tafeito.fachada.FachadaTaFeitoSQLite;
import br.pe.recife.tafeito.fachada.IFachadaTaFeito;
import br.pe.recife.tafeito.negocio.Acesso;
import br.pe.recife.tafeito.negocio.Agendamento;
import br.pe.recife.tafeito.negocio.Autenticacao;
import br.pe.recife.tafeito.negocio.Cliente;
import br.pe.recife.tafeito.negocio.Fornecedor;
import br.pe.recife.tafeito.negocio.Oferta;
import br.pe.recife.tafeito.negocio.Servico;
import br.pe.recife.tafeito.negocio.ServicoCategoria;

public class TesteBDActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teste_bd);

    }

    @Override
    protected void onResume() {
        super.onResume();

        teste1();
        //teste2();
        //teste3();
    }

    private void teste3() {

        System.out.println("*%*%*%*%*%*%*%*%*%*%*%* ---->  INICIANDO TESTES!  ");

        IFachadaTaFeito fachada = FachadaTaFeitoSQLite.getInstancia(this.getApplicationContext());

        Autenticacao autenticacao = new Autenticacao();
        autenticacao.setToken("123");

        ///

        //ServicoCategorias
        System.out.println("Testando Serviços Categoria:");

        try {

            //listagem de todos
            List<ServicoCategoria> listaServicoCategorias = fachada.listarServicoCategoria(autenticacao);
            System.out.println("Listagem de todos:");
            Iterator it = listaServicoCategorias.iterator();
            while (it.hasNext()) {
                ServicoCategoria obj = (ServicoCategoria) it.next();
                System.out.println(obj.toPrint());
                System.out.println("***");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    private void teste2() {

        System.out.println("*%*%*%*%*%*%*%*%*%*%*%* ---->  INICIANDO TESTES!  ");

        IFachadaTaFeito fachada = FachadaTaFeitoSQLite.getInstancia(this.getApplicationContext());

        ///

        //Acesso
        System.out.println("Testando Acessos:");

        try {

            //listagem de todos
            List<Acesso> listaAcessos = fachada.listarAcesso();
            System.out.println("Listagem de todos:");
            Iterator it = listaAcessos.iterator();
            while (it.hasNext()) {
                Acesso obj = (Acesso) it.next();
                System.out.println(obj.toPrint());
                System.out.println("***");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


    }

    private void teste1() {

        System.out.println("*%*%*%*%*%*%*%*%*%*%*%* ---->  INICIANDO TESTES!  ");

        IFachadaTaFeito fachada = FachadaTaFeitoSQLite.getInstancia(this.getApplicationContext());

        int i;

        Autenticacao autenticacao = new Autenticacao();
        autenticacao.setToken("123");

        ////

        //Cliente
        System.out.println("Testando Cliente:");

        Cliente cliente1 = new Cliente();
        cliente1.setHabilitado(true);
        cliente1.setNome("Cliente1");
        cliente1.setEndereco("Rua 1");
        cliente1.setEmail("cliente1@gmail.com");
        cliente1.setTelefone(1);
        cliente1.setCpf("1");

        Cliente cliente2 = new Cliente();
        cliente2.setHabilitado(false);
        cliente2.setNome("Cliente2");
        cliente2.setEndereco("Rua 2");
        cliente2.setEmail("cliente2@gmail.com");
        cliente2.setTelefone(2);
        cliente2.setCpf("2");

        Cliente cliente3 = new Cliente();
        cliente3.setHabilitado(true);
        cliente3.setNome("Cliente3");
        cliente3.setEndereco("Rua 3");
        cliente3.setEmail("cliente3@gmail.com");
        cliente3.setTelefone(3);
        cliente3.setCpf("3");

        try {

            //3 inclusões
            fachada.salvarCliente(cliente1, getApplicationContext(), autenticacao);
            System.out.println("Cliente1 salvo com sucesso:");
            System.out.println(cliente1.toPrint());
            System.out.println("***");

            fachada.salvarCliente(cliente2, getApplicationContext(), autenticacao);
            System.out.println("Cliente2 salvo com sucesso:");
            System.out.println(cliente2.toPrint());
            System.out.println("***");

            fachada.salvarCliente(cliente3, getApplicationContext(), autenticacao);
            System.out.println("Cliente3 salvo com sucesso:");
            System.out.println(cliente3.toPrint());
            System.out.println("***");

            //1 alteração
            cliente2.setHabilitado(true);
            cliente2.setNome("Cliente22");
            cliente2.setEndereco("Rua 22");
            cliente2.setEmail("cliente22@gmail.com");
            cliente2.setTelefone(22);
            cliente2.setCpf("22");

            fachada.salvarCliente(cliente2, getApplicationContext(), autenticacao);
            System.out.println("Cliente2 alterado com sucesso:");
            System.out.println(cliente2.toPrint());
            System.out.println("***");

            //1 consulta do alterado
            cliente2 = fachada.consultarCliente(cliente2.getId(), getApplicationContext(), autenticacao);
            System.out.println("Cliente2 consultado com sucesso:");
            System.out.println(cliente2.toPrint());
            System.out.println("***");

            //1 exclusão do alterado
            i = fachada.excluirCliente(cliente2, getApplicationContext(), autenticacao);
            System.out.println("Cliente2 excluído com sucesso. resposta: " + i);
            System.out.println("***");

            //listagem de todos
            List<Cliente> listaClientes = fachada.listarCliente(autenticacao);
            System.out.println("Listagem de todos:");
            Iterator it = listaClientes.iterator();
            while (it.hasNext()) {
                Cliente obj = (Cliente) it.next();
                System.out.println(obj.toPrint());
                System.out.println("***");
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        //Fornecedor
        System.out.println("Testando Fornecedor:");

        Fornecedor fornecedor1 = new Fornecedor();
        fornecedor1.setHabilitado(true);
        fornecedor1.setNome("Fornecedor1");
        fornecedor1.setEndereco("Rua 1");
        fornecedor1.setEmail("fornecedor1@gmail.com");
        fornecedor1.setTelefone(1);
        fornecedor1.setCnpj("1");

        Fornecedor fornecedor2 = new Fornecedor();
        fornecedor2.setHabilitado(false);
        fornecedor2.setNome("Fornecedor2");
        fornecedor2.setEndereco("Rua 2");
        fornecedor2.setEmail("fornecedor2@gmail.com");
        fornecedor2.setTelefone(2);
        fornecedor2.setCnpj("2");

        Fornecedor fornecedor3 = new Fornecedor();
        fornecedor3.setHabilitado(true);
        fornecedor3.setNome("Fornecedor3");
        fornecedor3.setEndereco("Rua 3");
        fornecedor3.setEmail("fornecedor3@gmail.com");
        fornecedor3.setTelefone(3);
        fornecedor3.setCnpj("3");

        try {

            //3 inclusões
            fachada.salvarFornecedor(fornecedor1, getApplicationContext(), autenticacao);
            System.out.println("Fornecedor1 salvo com sucesso:");
            System.out.println(fornecedor1.toPrint());
            System.out.println("***");

            fachada.salvarFornecedor(fornecedor2, getApplicationContext(), autenticacao);
            System.out.println("Fornecedor2 salvo com sucesso:");
            System.out.println(fornecedor2.toPrint());
            System.out.println("***");

            fachada.salvarFornecedor(fornecedor3, getApplicationContext(), autenticacao);
            System.out.println("Fornecedor3 salvo com sucesso:");
            System.out.println(fornecedor3.toPrint());
            System.out.println("***");

            //1 alteração
            fornecedor2.setHabilitado(true);
            fornecedor2.setNome("Fornecedor22");
            fornecedor2.setEndereco("Rua 22");
            fornecedor2.setEmail("fornecedor22@gmail.com");
            fornecedor2.setTelefone(22);
            fornecedor2.setCnpj("22");

            fachada.salvarFornecedor(fornecedor2, getApplicationContext(), autenticacao);
            System.out.println("Fornecedor2 alterado com sucesso:");
            System.out.println(fornecedor2.toPrint());
            System.out.println("***");

            //1 consulta do alterado
            fornecedor2 = fachada.consultarFornecedor(fornecedor2.getId(), getApplicationContext(), autenticacao);
            System.out.println("Fornecedor2 consultado com sucesso:");
            System.out.println(fornecedor2.toPrint());
            System.out.println("***");

            //1 exclusão do alterado
            i = fachada.excluirFornecedor(fornecedor2, getApplicationContext(), autenticacao);
            System.out.println("Fornecedor2 excluído com sucesso. resposta: " + i);
            System.out.println("***");

            //listagem de todos
            List<Fornecedor> listaFornecedors = fachada.listarFornecedor(autenticacao);
            System.out.println("Listagem de todos:");
            Iterator it = listaFornecedors.iterator();
            while (it.hasNext()) {
                Fornecedor obj = (Fornecedor) it.next();
                System.out.println(obj.toPrint());
                System.out.println("***");
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //ServicoCategoria
        System.out.println("Testando ServicoCategoria:");

        ServicoCategoria servicoCategoria1 = new ServicoCategoria();
        servicoCategoria1.setNome("ServicoCategoria1");
        servicoCategoria1.setDescricao("Descricao 1");

        ServicoCategoria servicoCategoria2 = new ServicoCategoria();
        servicoCategoria2.setNome("ServicoCategoria2");
        servicoCategoria2.setDescricao("Descricao 2");

        ServicoCategoria servicoCategoria3 = new ServicoCategoria();
        servicoCategoria3.setNome("ServicoCategoria3");
        servicoCategoria3.setDescricao("Descricao 3");

        try {

            //3 inclusões
            fachada.salvarServicoCategoria(servicoCategoria1, getApplicationContext(), autenticacao);
            System.out.println("ServicoCategoria1 salvo com sucesso:");
            System.out.println(servicoCategoria1.toPrint());
            System.out.println("***");

            fachada.salvarServicoCategoria(servicoCategoria2, getApplicationContext(), autenticacao);
            System.out.println("ServicoCategoria2 salvo com sucesso:");
            System.out.println(servicoCategoria2.toPrint());
            System.out.println("***");

            fachada.salvarServicoCategoria(servicoCategoria3, getApplicationContext(), autenticacao);
            System.out.println("ServicoCategoria3 salvo com sucesso:");
            System.out.println(servicoCategoria3.toPrint());
            System.out.println("***");

            //1 alteração
            servicoCategoria2.setNome("ServicoCategoria22");
            servicoCategoria2.setDescricao("Descricao 22");

            fachada.salvarServicoCategoria(servicoCategoria2, getApplicationContext(), autenticacao);
            System.out.println("ServicoCategoria2 alterado com sucesso:");
            System.out.println(servicoCategoria2.toPrint());
            System.out.println("***");

            //1 consulta do alterado
            servicoCategoria2 = fachada.consultarServicoCategoria(servicoCategoria2.getId(), getApplicationContext(), autenticacao);
            System.out.println("ServicoCategoria2 consultado com sucesso:");
            System.out.println(servicoCategoria2.toPrint());
            System.out.println("***");

            //1 exclusão do alterado
            i = fachada.excluirServicoCategoria(servicoCategoria2, getApplicationContext(), autenticacao);
            System.out.println("ServicoCategoria2 excluído com sucesso. resposta: " + i);
            System.out.println("***");

            //listagem de todos
            List<ServicoCategoria> listaServicoCategorias = fachada.listarServicoCategoria(autenticacao);
            System.out.println("Listagem de todos:");
            Iterator it = listaServicoCategorias.iterator();
            while (it.hasNext()) {
                ServicoCategoria obj = (ServicoCategoria) it.next();
                System.out.println(obj.toPrint());
                System.out.println("***");
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //Servico
        System.out.println("Testando Servico:");

        Servico servico1 = new Servico();
        servico1.setFornecedor(fornecedor1);
        servico1.setServicoCategoria(servicoCategoria1);
        servico1.setNome("Servico1");
        servico1.setDescricao("Descricao 1");

        Servico servico2 = new Servico();
        servico2.setFornecedor(fornecedor1);
        servico2.setServicoCategoria(servicoCategoria1);
        servico2.setNome("Servico2");
        servico2.setDescricao("Descricao 2");

        Servico servico3 = new Servico();
        servico3.setFornecedor(fornecedor3);
        servico3.setServicoCategoria(servicoCategoria3);
        servico3.setNome("Servico3");
        servico3.setDescricao("Descricao 3");

        try {

            //3 inclusões
            fachada.salvarServico(servico1, getApplicationContext(), autenticacao);
            System.out.println("Servico1 salvo com sucesso:");
            System.out.println(servico1.toPrint());
            System.out.println("***");

            fachada.salvarServico(servico2, getApplicationContext(), autenticacao);
            System.out.println("Servico2 salvo com sucesso:");
            System.out.println(servico2.toPrint());
            System.out.println("***");

            fachada.salvarServico(servico3, getApplicationContext(), autenticacao);
            System.out.println("Servico3 salvo com sucesso:");
            System.out.println(servico3.toPrint());
            System.out.println("***");

            //1 alteração
            servico2.setFornecedor(fornecedor3);
            servico2.setServicoCategoria(servicoCategoria3);
            servico2.setNome("Servico22");
            servico2.setDescricao("Descricao 22");

            fachada.salvarServico(servico2, getApplicationContext(), autenticacao);
            System.out.println("Servico2 alterado com sucesso:");
            System.out.println(servico2.toPrint());
            System.out.println("***");

            //1 consulta do alterado
            servico2 = fachada.consultarServico(servico2.getId(), getApplicationContext(), autenticacao);
            System.out.println("Servico2 consultado com sucesso:");
            System.out.println(servico2.toPrint());
            System.out.println("***");

            //1 exclusão do alterado
            i = fachada.excluirServico(servico2, getApplicationContext(), autenticacao);
            System.out.println("Servico2 excluído com sucesso. resposta: " + i);
            System.out.println("***");

            //listagem de todos
            List<Servico> listaServicos = fachada.listarServico(autenticacao);
            System.out.println("Listagem de todos:");
            Iterator it = listaServicos.iterator();
            while (it.hasNext()) {
                Servico obj = (Servico) it.next();
                System.out.println(obj.toPrint());
                System.out.println("***");
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //Oferta
        System.out.println("Testando Oferta:");

        Oferta oferta1 = new Oferta();
        oferta1.setServico(servico1);
        oferta1.setDataHoraInicio(new GregorianCalendar(2017, 11, 10, 9, 30).getTime());
        oferta1.setDataHoraFim(new GregorianCalendar(2017, 11, 10, 10, 30).getTime());

        Oferta oferta2 = new Oferta();
        oferta2.setServico(servico1);
        oferta2.setDataHoraInicio(new GregorianCalendar(2017, 11, 11, 9, 30).getTime());
        oferta2.setDataHoraFim(new GregorianCalendar(2017, 11, 11, 10, 30).getTime());

        Oferta oferta3 = new Oferta();
        oferta3.setServico(servico3);
        oferta3.setDataHoraInicio(new GregorianCalendar(2017, 11, 12, 9, 30).getTime());
        oferta3.setDataHoraFim(new GregorianCalendar(2017, 11, 12, 10, 30).getTime());

        try {

            //3 inclusões
            fachada.salvarOferta(oferta1, getApplicationContext(), autenticacao);
            System.out.println("Oferta1 salvo com sucesso:");
            System.out.println(oferta1.toPrint());
            System.out.println("***");

            fachada.salvarOferta(oferta2, getApplicationContext(), autenticacao);
            System.out.println("Oferta2 salvo com sucesso:");
            System.out.println(oferta2.toPrint());
            System.out.println("***");

            fachada.salvarOferta(oferta3, getApplicationContext(), autenticacao);
            System.out.println("Oferta3 salvo com sucesso:");
            System.out.println(oferta3.toPrint());
            System.out.println("***");

            //1 alteração
            oferta2.setServico(servico3);
            oferta2.setDataHoraInicio(new GregorianCalendar(2018, 11, 11, 9, 30).getTime());
            oferta2.setDataHoraFim(new GregorianCalendar(2018, 11, 11, 10, 30).getTime());

            fachada.salvarOferta(oferta2, getApplicationContext(), autenticacao);
            System.out.println("Oferta2 alterado com sucesso:");
            System.out.println(oferta2.toPrint());
            System.out.println("***");

            //1 consulta do alterado
            oferta2 = fachada.consultarOferta(oferta2.getId(), getApplicationContext(), autenticacao);
            System.out.println("Oferta2 consultado com sucesso:");
            System.out.println(oferta2.toPrint());
            System.out.println("***");

            //1 exclusão do alterado
            i = fachada.excluirOferta(oferta2, getApplicationContext(), autenticacao);
            System.out.println("Oferta2 excluído com sucesso. resposta: " + i);
            System.out.println("***");

            //listagem de todos
            List<Oferta> listaOfertas = fachada.listarOferta(autenticacao);
            System.out.println("Listagem de todos:");
            Iterator it = listaOfertas.iterator();
            while (it.hasNext()) {
                Oferta obj = (Oferta) it.next();
                System.out.println(obj.toPrint());
                System.out.println("***");
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //Agendamento
        System.out.println("Testando Agendamento:");

        Agendamento agendamento1 = new Agendamento();
        agendamento1.setOferta(oferta1);
        agendamento1.setCliente(cliente1);
        agendamento1.setDataHoraRealizado(new GregorianCalendar(2017, 11, 10, 9, 30).getTime());
        agendamento1.setDataHoraCancelado(new GregorianCalendar(2017, 11, 10, 10, 30).getTime());

        Agendamento agendamento2 = new Agendamento();
        agendamento2.setOferta(oferta1);
        agendamento2.setCliente(cliente3);
        agendamento2.setDataHoraRealizado(new GregorianCalendar(2017, 11, 10, 9, 30).getTime());
        agendamento2.setDataHoraCancelado(new GregorianCalendar(2017, 11, 10, 10, 30).getTime());

        Agendamento agendamento3 = new Agendamento();
        agendamento3.setOferta(oferta3);
        agendamento3.setCliente(cliente3);
        agendamento3.setDataHoraRealizado(new GregorianCalendar(2017, 11, 10, 9, 30).getTime());
        agendamento3.setDataHoraCancelado(new GregorianCalendar(2017, 11, 10, 10, 30).getTime());

        try {

            //3 inclusões
            fachada.salvarAgendamento(agendamento1, getApplicationContext(), autenticacao);
            System.out.println("Agendamento1 salvo com sucesso:");
            System.out.println(agendamento1.toPrint());
            System.out.println("***");

            fachada.salvarAgendamento(agendamento2, getApplicationContext(), autenticacao);
            System.out.println("Agendamento2 salvo com sucesso:");
            System.out.println(agendamento2.toPrint());
            System.out.println("***");

            fachada.salvarAgendamento(agendamento3, getApplicationContext(), autenticacao);
            System.out.println("Agendamento3 salvo com sucesso:");
            System.out.println(agendamento3.toPrint());
            System.out.println("***");

            //1 alteração
            agendamento2.setOferta(oferta3);
            agendamento2.setCliente(cliente1);
            agendamento2.setDataHoraRealizado(new GregorianCalendar(2018, 11, 10, 9, 30).getTime());
            agendamento2.setDataHoraCancelado(new GregorianCalendar(2018, 11, 10, 10, 30).getTime());

            fachada.salvarAgendamento(agendamento2, getApplicationContext(), autenticacao);
            System.out.println("Agendamento2 alterado com sucesso:");
            System.out.println(agendamento2.toPrint());
            System.out.println("***");

            //1 consulta do alterado
            agendamento2 = fachada.consultarAgendamento(agendamento2.getId(), getApplicationContext(), autenticacao);
            System.out.println("Agendamento2 consultado com sucesso:");
            System.out.println(agendamento2.toPrint());
            System.out.println("***");

            //1 exclusão do alterado
            i = fachada.excluirAgendamento(agendamento2, getApplicationContext(), autenticacao);
            System.out.println("Agendamento2 excluído com sucesso. resposta: " + i);
            System.out.println("***");

            //listagem de todos
            List<Agendamento> listaAgendamentos = fachada.listarAgendamento(autenticacao);
            System.out.println("Listagem de todos:");
            Iterator it = listaAgendamentos.iterator();
            while (it.hasNext()) {
                Agendamento obj = (Agendamento) it.next();
                System.out.println(obj.toPrint());
                System.out.println("***");
            }


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        //5 métodos restantes ainda não testados
        System.out.println("Testando as 5 listagens que ainda faltam:");

        try {

            //Fornecedor por ServicoCategoria
            List<Fornecedor> lista0 = fachada.listarPorServicoCategoriaFornecedor(servicoCategoria3, autenticacao);
            System.out.println("Listagem de Fornecedor por ServicoCategoria:");
            System.out.println(servicoCategoria3.toPrint());
            System.out.println("***");
            Iterator it = lista0.iterator();
            while (it.hasNext()) {
                Fornecedor obj = (Fornecedor) it.next();
                System.out.println(obj.toPrint());
                System.out.println("***");
            }

            //ServicoCategoria por Fornecedor
            List<ServicoCategoria> lista1 = fachada.listarPorFornecedorServicoCategoria(fornecedor3, autenticacao);
            System.out.println("Listagem de ServicoCategoria por Fornecedor:");
            System.out.println(fornecedor3.toPrint());
            System.out.println("***");
            it = lista1.iterator();
            while (it.hasNext()) {
                ServicoCategoria obj = (ServicoCategoria) it.next();
                System.out.println(obj.toPrint());
                System.out.println("***");
            }

            //Servico por ServicoCategoria
            List<Servico> lista2 = fachada.listarPorServicoCategoriaServico(servicoCategoria3, autenticacao);
            System.out.println("Listagem de Servico por ServicoCategoria:");
            System.out.println(servicoCategoria3.toPrint());
            System.out.println("***");
            it = lista2.iterator();
            while (it.hasNext()) {
                Servico obj = (Servico) it.next();
                System.out.println(obj.toPrint());
                System.out.println("***");
            }

            //Servico por Fornecedor
            List<Servico> lista3 = fachada.listarPorFornecedorServico(fornecedor3, autenticacao);
            System.out.println("Listagem de Servico por Fornecedor:");
            System.out.println(fornecedor3.toPrint());
            System.out.println("***");
            it = lista3.iterator();
            while (it.hasNext()) {
                Servico obj = (Servico) it.next();
                System.out.println(obj.toPrint());
                System.out.println("***");
            }

            //Servico por ServicoCategoria por Fornecedor
            List<Servico> lista4 = fachada.listarPorServicoCategoriaPorFornecedorServico(servicoCategoria3, fornecedor3, autenticacao);
            System.out.println("Listagem de Servico por ServicoCategoria por Fornecedor:");
            System.out.println(servicoCategoria3.toPrint());
            System.out.println("***");
            System.out.println(fornecedor3.toPrint());
            System.out.println("***");
            it = lista4.iterator();
            while (it.hasNext()) {
                Servico obj = (Servico) it.next();
                System.out.println(obj.toPrint());
                System.out.println("***");
            }


        }  catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}

