package Services;

import Entities.Transacao;

import java.util.List;

public interface Repository<T> {

     void salvar(T obj);

}
