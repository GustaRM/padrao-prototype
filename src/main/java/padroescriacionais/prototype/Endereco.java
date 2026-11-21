package padroescriacionais.prototype;

/** Objeto mutável que faz parte do contrato: precisa ser clonado também (cópia profunda). */
public class Endereco implements Cloneable {

    private String logradouro;
    private String cidade;
    private String uf;

    public Endereco(String logradouro, String cidade, String uf) {
        this.logradouro = logradouro;
        this.cidade = cidade;
        this.uf = uf;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public void setLogradouro(String logradouro) {
        this.logradouro = logradouro;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    @Override
    public Endereco clone() {
        try {
            return (Endereco) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public String toString() {
        return logradouro + ", " + cidade + "/" + uf;
    }
}
