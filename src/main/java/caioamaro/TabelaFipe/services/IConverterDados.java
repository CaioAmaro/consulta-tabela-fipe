package caioamaro.TabelaFipe.services;

import java.util.List;

public interface IConverterDados {
    <T> T converter(String json, Class<T> classe);

    <T> List<T> obterListar(String json, Class<T> classe);
}
