package padroescriacionais.prototype;

import java.util.HashMap;
import java.util.Map;

/**
 * Registro de protótipos: guarda os modelos de contrato aprovados pelo RH
 * e devolve sempre uma CÓPIA, nunca o modelo original.
 */
public class CatalogoContratos {

    private final Map<String, ContratoTrabalho> prototipos = new HashMap<>();

    public void registrar(String chave, ContratoTrabalho modelo) {
        prototipos.put(chave, modelo.clone());
    }

    public ContratoTrabalho obter(String chave) {
        ContratoTrabalho modelo = prototipos.get(chave);
        if (modelo == null) {
            throw new IllegalArgumentException("Modelo de contrato não encontrado: " + chave);
        }
        return modelo.clone();
    }

    public int quantidadeModelos() {
        return prototipos.size();
    }

    public static CatalogoContratos padrao() {
        CatalogoContratos catalogo = new CatalogoContratos();
        Endereco hospital = new Endereco("Av. Brasil, 1000", "Juiz de Fora", "MG");

        catalogo.registrar("ENFERMEIRO_PLANTONISTA",
                new ContratoTrabalho("Enfermeiro plantonista", 6500.00, 36, hospital)
                        .adicionarClausula("Escala 12x36")
                        .adicionarClausula("Adicional de insalubridade de 20%")
                        .adicionarClausula("Adicional noturno conforme CLT"));

        catalogo.registrar("ANALISTA_ADMINISTRATIVO",
                new ContratoTrabalho("Analista administrativo", 4200.00, 40, hospital)
                        .adicionarClausula("Jornada de segunda a sexta-feira")
                        .adicionarClausula("Vale-transporte"));
        return catalogo;
    }
}
