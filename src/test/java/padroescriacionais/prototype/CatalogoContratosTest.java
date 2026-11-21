package padroescriacionais.prototype;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CatalogoContratosTest {

    private CatalogoContratos catalogo;

    @BeforeEach
    void iniciar() {
        catalogo = CatalogoContratos.padrao();
    }

    @Test
    void deveConterDoisModelos() {
        assertEquals(2, catalogo.quantidadeModelos());
    }

    @Test
    void obterDeveDevolverCopiaNovaACadaChamada() {
        ContratoTrabalho a = catalogo.obter("ENFERMEIRO_PLANTONISTA");
        ContratoTrabalho b = catalogo.obter("ENFERMEIRO_PLANTONISTA");
        assertNotSame(a, b);
        assertNotSame(a.getLocalTrabalho(), b.getLocalTrabalho());
        assertEquals(a.resumo(), b.resumo());
    }

    @Test
    void copiaDeveVirComDadosDoModelo() {
        ContratoTrabalho c = catalogo.obter("ANALISTA_ADMINISTRATIVO");
        assertNull(c.getEmpregado());
        assertEquals("Analista administrativo", c.getCargo());
        assertEquals(4200.00, c.getSalario(), 0.001);
        assertEquals(40, c.getCargaHorariaSemanal());
        assertEquals(2, c.getClausulas().size());
    }

    @Test
    void personalizarCopiaNaoAlteraModeloDoCatalogo() {
        ContratoTrabalho c = catalogo.obter("ENFERMEIRO_PLANTONISTA");
        c.setEmpregado("Bruno");
        c.getLocalTrabalho().setLogradouro("Rua Halfeld, 500");
        c.adicionarClausula("Ajuda de custo");

        ContratoTrabalho modelo = catalogo.obter("ENFERMEIRO_PLANTONISTA");
        assertNull(modelo.getEmpregado());
        assertEquals("Av. Brasil, 1000", modelo.getLocalTrabalho().getLogradouro());
        assertEquals(3, modelo.getClausulas().size());
    }

    @Test
    void alterarObjetoRegistradoDepoisDoRegistroNaoAlteraCatalogo() {
        ContratoTrabalho modelo = new ContratoTrabalho("Técnico", 3000, 40, new Endereco("Rua B, 2", "Juiz de Fora", "MG"));
        catalogo.registrar("TECNICO", modelo);
        modelo.setSalario(1);
        assertEquals(3000.0, catalogo.obter("TECNICO").getSalario(), 0.001);
    }

    @Test
    void deveRejeitarModeloInexistente() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> catalogo.obter("MEDICO"));
        assertEquals("Modelo de contrato não encontrado: MEDICO", e.getMessage());
    }
}
