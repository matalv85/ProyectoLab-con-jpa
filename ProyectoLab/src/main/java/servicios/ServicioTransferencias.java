package servicios;

import java.math.BigDecimal;

import modelos.Cuenta;

public interface ServicioTransferencias {
	boolean hacerTransferencia(Cuenta cuentaOrigen, Cuenta cuentaDestino, BigDecimal monto);

}