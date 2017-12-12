package br.pe.recife.tafeito.dao;

import java.util.List;

/**
 * Created by Frederico on 25/11/2017.
 */

public interface IDAOson<T> {

    void salvar(T entidade, boolean novo);
    int excluir(T entidade);
    T consultar(long id);
    List<T> listar();
}
