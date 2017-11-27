package br.pe.recife.tafeito.service;

import android.content.Context;

import java.util.List;

import br.pe.recife.tafeito.R;
import br.pe.recife.tafeito.dao.AgendamentoDAO;
import br.pe.recife.tafeito.excecao.InfraException;
import br.pe.recife.tafeito.excecao.NegocioException;
import br.pe.recife.tafeito.negocio.Agendamento;

public class AgendamentoService {

    private static AgendamentoService instancia;
    private AgendamentoDAO agendamentoDao;

    public static AgendamentoService getInstancia(Context context) {

        if (instancia == null) {
            instancia = new AgendamentoService(context);
        }

        return instancia;
    }

    private AgendamentoService(Context context) {
        this.agendamentoDao = AgendamentoDAO.getInstancia(context);
    }

    public void salvar(Agendamento agendamento, Context contexto) throws InfraException, NegocioException {

        if(agendamento == null) {
            throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nulo).toString());
        }

        try {
            agendamentoDao.salvar(agendamento);
        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }
    }

    public Agendamento consultar(long id, Context contexto) throws InfraException, NegocioException {

        Agendamento res = null;

        try {

            res = agendamentoDao.consultar(id);

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

    public int excluir(Agendamento agendamento, Context contexto) throws InfraException, NegocioException{

        int res = 0;

        try {

            res = agendamentoDao.excluir(agendamento);

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

    public List<Agendamento> listar() throws InfraException{

        try {
            return agendamentoDao.listar();
        }catch (Exception e){
            throw new InfraException(e.getMessage(),e);
        }
    }
}
