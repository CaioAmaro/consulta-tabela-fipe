package caioamaro.TabelaFipe.principal;

import caioamaro.TabelaFipe.model.Dados;
import caioamaro.TabelaFipe.model.Modelos;
import caioamaro.TabelaFipe.model.Veiculo;
import caioamaro.TabelaFipe.services.ConsumirApi;
import caioamaro.TabelaFipe.services.ConverterDados;

import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private Scanner leitura = new Scanner(System.in);
    private ConsumirApi consumirApi = new ConsumirApi();
    private ConverterDados converterDados = new ConverterDados();
    private final String URL_BASE = "https://parallelum.com.br/fipe/api/v1/";

    public void exibeMenu(){
        System.out.println("Escolha o tipo de Veiculo");
        System.out.println("-> Carro");
        System.out.println("-> Moto");
        System.out.println("-> Caminhão");
        System.out.print("Digite: ");
        var tipoVeiculo = leitura.nextLine();
        System.out.println();

        var linkSolicitacao = URL_BASE;

        if(tipoVeiculo.toLowerCase().contains("carr")){
            linkSolicitacao += "carros/marcas";
        }else if(tipoVeiculo.toLowerCase().contains("mot")){
            linkSolicitacao += "motos/marcas";
        }else{
            linkSolicitacao += "caminhoes/marcas";
        }

        var json = consumirApi.obterDados(linkSolicitacao);
        List<Dados> marcasVeiculos = converterDados.obterListar(json, Dados.class);

        System.out.println("** Lista de Marcas **");
        marcasVeiculos.forEach(n -> System.out.println("Codigo: "+ n.codigo() +" Marca: " + n.nome()));

        System.out.print("Selecione o código da marca: ");
        var opcaoMarca = leitura.nextLine();
        System.out.println();

        linkSolicitacao += "/"+ opcaoMarca + "/modelos";

        json = consumirApi.obterDados(linkSolicitacao);
        Modelos modelos = converterDados.converter(json, Modelos.class);

        System.out.println("** Lista de Modelos **");
        modelos.listaModelos().forEach(n -> System.out.println("Codigo: "+ n.codigo() +" Modelo: " + n.nome()));
        System.out.print("Digite um trecho do modelo: ");
        var trechoModelo = leitura.nextLine();
        System.out.println();

        modelos.listaModelos().stream()
                        .filter(n -> n.nome().toLowerCase().contains(trechoModelo.toLowerCase()))
                        .forEach(n -> System.out.println("Codigo: "+ n.codigo() +" Modelo: " + n.nome()));



        System.out.print("Selecione o código da marca: ");
        var opcaoModelo = leitura.nextLine();
        System.out.println();

        linkSolicitacao += "/"+ opcaoModelo + "/anos";

        json = consumirApi.obterDados(linkSolicitacao);
        var dados = converterDados.obterListar(json, Dados.class);

        for (Dados item: dados){
            json = consumirApi.obterDados(linkSolicitacao+"/"+item.codigo());
            Veiculo veiculo = converterDados.converter(json,Veiculo.class);
            System.out.println(veiculo);
        }



    }
}
