package servicios;

import java.math.BigDecimal;

public interface ServicioCompraVenta {
	boolean vender(Long idcliente, Long idcuentaExtranjera, Long idCuentaPesos, BigDecimal monto);
}
