package padroescriacionais.prototype;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Prototype: contrato que sabe se clonar. A cópia é profunda: o endereço e a lista
 * de cláusulas da cópia são objetos novos, independentes do modelo original.
 */
public class ContratoTrabalho implements Cloneable {

    private String empregado;
    private String cargo;
    private double salario;
    private int cargaHorariaSemanal;
    private Endereco localTrabalho;
    private List<String> clausulas = new ArrayList<>();

    public ContratoTrabalho(String cargo, double salario, int cargaHorariaSemanal, Endereco localTrabalho) {
        this.cargo = cargo;
        this.salario = salario;
        this.cargaHorariaSemanal = cargaHorariaSemanal;
        this.localTrabalho = localTrabalho;
    }

    @Override
    public ContratoTrabalho clone() {
        try {
            ContratoTrabalho copia = (ContratoTrabalho) super.clone();
            copia.localTrabalho = localTrabalho.clone();
            copia.clausulas = new ArrayList<>(clausulas);
            return copia;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    public ContratoTrabalho adicionarClausula(String clausula) {
        clausulas.add(clausula);
        return this;
    }

    public String getEmpregado() {
        return empregado;
    }

    public void setEmpregado(String empregado) {
        this.empregado = empregado;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getCargaHorariaSemanal() {
        return cargaHorariaSemanal;
    }

    public void setCargaHorariaSemanal(int cargaHorariaSemanal) {
        this.cargaHorariaSemanal = cargaHorariaSemanal;
    }

    public Endereco getLocalTrabalho() {
        return localTrabalho;
    }

    public List<String> getClausulas() {
        return Collections.unmodifiableList(clausulas);
    }

    public String resumo() {
        return String.format(Locale.US, "%s | %s | R$ %.2f | %dh semanais | %s | %d cláusula(s)",
                empregado == null ? "(sem empregado)" : empregado, cargo, salario,
                cargaHorariaSemanal, localTrabalho, clausulas.size());
    }
}
