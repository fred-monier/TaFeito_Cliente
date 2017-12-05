package br.pe.recife.tafeito.service;

import android.content.Context;

import java.util.List;

import br.pe.recife.tafeito.R;
import br.pe.recife.tafeito.dao.OfertaDAO;
import br.pe.recife.tafeito.excecao.InfraException;
import br.pe.recife.tafeito.excecao.NegocioException;
import br.pe.recife.tafeito.negocio.Oferta;

public class OfertaService {

    private static OfertaService instancia;
    private OfertaDAO ofertaDao;

    public static OfertaService getInstancia(Context context) {

        if (instancia == null) {
            instancia = new OfertaService(context);
        }

        return instancia;
    }

    private OfertaService(Context context) {
        this.ofertaDao = OfertaDAO.getInstancia(context);
    }

    public void salvar(Oferta oferta, Context contexto) throws InfraException, NegocioException {

        if(oferta == null) {
            throw new NegocioException(contexto.getResources().getText(R.string.excecao_objeto_nulo).toString());
        }

        try {
            ofertaDao.salvar(oferta);
        } catch (Exception e) {
            throw new InfraException(e.getMessage(), e);
        }
    }

    public Oferta consultar(long id, Context contexto) throws InfraException, NegocioException {

        Oferta res = null;

        try {

            res = ofertaDao.consultar(id);

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

    public int excluir(Oferta oferta, Context contexto) throws InfraException, NegocioException{

        int res = 0;

        try {

            res = ofertaDao.excluir(oferta);

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

    public List<Oferta> listar() throws InfraException{

        try {
            return ofertaDao.listar();
        }catch (Exception e){
            throw new InfraException(e.getMessage(),e);
        }
    }
}
