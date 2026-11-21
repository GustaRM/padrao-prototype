package padroescriacionais.prototype;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContratoTrabalhoTest {

    private ContratoTrabalho original;

    @BeforeEach
    void criarOriginal() {
        original = new ContratoTrabalho("Enfermeiro plantonista", 6500.00, 36,
                new Endereco("Av. Brasil, 1000", "Juiz de Fora", "MG"))
                .adicionarClausula("Escala 12x36");
        original.setEmpregado("Ana");
    }

    @Test
    void cloneDeveSerOutroObjetoComMesmosDados() {
        ContratoTrabalho copia = original.clone();
        assertNotSame(original, copia);
        assertEquals(original.getEmpregado(), copia.getEmpregado());
        assertEquals(original.getCargo(), copia.getCargo());
        assertEquals(original.getSalario(), copia.getSalario(), 0.001);
        assertEquals(original.getCargaHorariaSemanal(), copia.getCargaHorariaSemanal());
        assertEquals(original.getClausulas(), copia.getClausulas());
        assertEquals(original.resumo(), copia.resumo());
    }

    @Test
    void cloneDeveCopiarEnderecoEmProfundidade() {
        ContratoTrabalho copia = original.clone();
        assertNotSame(original.getLocalTrabalho(), copia.getLocalTrabalho());
        copia.getLocalTrabalho().setCidade("Belo Horizonte");
        assertEquals("Juiz de Fora", original.getLocalTrabalho().getCidade());
    }

    @Test
    void cloneDeveCopiarListaDeClausulas() {
        ContratoTrabalho copia = original.clone();
        copia.adicionarClausula("Ajuda de custo");
        assertEquals(1, original.getClausulas().size());
        assertEquals(2, copia.getClausulas().size());
    }

    @Test
    void alterarCamposSimplesDoCloneNaoAfetaOriginal() {
        ContratoTrabalho copia = original.clone();
        copia.setEmpregado("Bruno");
        copia.setSalario(7000.00);
        assertEquals("Ana", original.getEmpregado());
        assertEquals(6500.00, original.getSalario(), 0.001);
    }

    @Test
    void enderecoDeveSerClonavelIsoladamente() {
        Endereco e = new Endereco("Rua A, 1", "Juiz de Fora", "MG");
        Endereco c = e.clone();
        assertNotSame(e, c);
        assertEquals(e.toString(), c.toString());
    }

    @Test
    void deveGerarResumo() {
        assertEquals("Ana | Enfermeiro plantonista | R$ 6500.00 | 36h semanais | Av. Brasil, 1000, Juiz de Fora/MG | 1 cláusula(s)",
                original.resumo());
    }

    @Test
    void listaDeClausulasDeveSerSomenteLeitura() {
        assertThrows(UnsupportedOperationException.class, () -> original.getClausulas().add("X"));
    }
}
