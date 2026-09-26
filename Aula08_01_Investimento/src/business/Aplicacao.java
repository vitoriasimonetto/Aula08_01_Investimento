package business;

public class Aplicacao implements IAplicacao {

    private float rendimento;

    @Override
    public void calcularRendimento(float valorAplicado, int prazo, float taxa) {
        rendimento = (float) (valorAplicado
                * Math.pow(1 + (taxa / 100), prazo));
    }

    public float getRendimento() {
        return rendimento;
    }
}
