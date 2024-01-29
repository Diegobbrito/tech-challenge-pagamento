package br.com.fiap.pagamento.core.enumerator;

public enum StatusEnum {

    PAGAMENTOPENDENTE("PAGAMENTOPENDENTE"),
    PAGO( "PAGO");


    private String tipo;


    StatusEnum(String tipo) {
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

}