package padroescriacionais.prototype;

public class Main {

    public static void main(String[] args) {
        CatalogoContratos catalogo = CatalogoContratos.padrao();

        ContratoTrabalho contratoAna = catalogo.obter("ENFERMEIRO_PLANTONISTA");
        contratoAna.setEmpregado("Ana Paula Rocha");

        ContratoTrabalho contratoBruno = catalogo.obter("ENFERMEIRO_PLANTONISTA");
        contratoBruno.setEmpregado("Bruno Martins");
        contratoBruno.getLocalTrabalho().setLogradouro("Rua Halfeld, 500 (Unidade Centro)");
        contratoBruno.adicionarClausula("Ajuda de custo para deslocamento");

        System.out.println(contratoAna.resumo());
        System.out.println(contratoBruno.resumo());
        System.out.println("Modelo intacto: " + catalogo.obter("ENFERMEIRO_PLANTONISTA").resumo());
    }
}
