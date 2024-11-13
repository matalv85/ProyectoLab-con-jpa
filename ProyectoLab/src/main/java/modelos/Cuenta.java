package modelos;

import java.io.Serializable;
import java.math.BigDecimal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.fasterxml.jackson.annotation.*;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorColumn;
import jakarta.persistence.DiscriminatorType;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode
@Table(name ="Cuentas")
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING, name = "Moneda")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "numeroCuenta")
public abstract class Cuenta implements Serializable {
	@Id
	@GeneratedValue
	@Column(name = "numero cuenta")
	private Long numeroCuenta;
	
	@NonNull
	@NotNull
	@Past
	@Column(name = "fecha creacion")
	private LocalDate fechaCreacion;
	
	@NonNull
	@NotNull
	@Column(scale = 2, name = "saldo inicial")
	private BigDecimal saldoInicial;
	
	@Column(scale = 2, name = "saldo actual")
	private BigDecimal saldoActual;
	
	@NonNull
	@NotNull
	@Column(scale = 2)
	private BigDecimal descubierto;
	
	@Column(name = "fecha cierre")
	private LocalDate fechaCierre;
	
	@NonNull
	@NotNull
	@ManyToOne
	@JsonBackReference("cliente-cuentas")
	private Cliente titular;
	
	@ManyToMany
	@JoinTable(
	        name = "cuenta_cotitulares",
	        joinColumns = @JoinColumn(name = "cuenta_id"),
	        inverseJoinColumns = @JoinColumn(name = "cliente_id")
	)
	private Set<Cliente> cotitulares = new HashSet<Cliente>();
	
	@OneToMany(mappedBy = "cuenta", cascade = CascadeType.ALL, orphanRemoval = true)
	@JsonManagedReference("cuenta-movimientos")
	private Set<Movimiento> movimientos = new HashSet<Movimiento>();
	
	public Cuenta(LocalDate fechaCreacion, BigDecimal saldoInicial,BigDecimal descubierto ,Cliente titular) {
		this.fechaCreacion = fechaCreacion;
		this.saldoInicial = saldoInicial;
		this.saldoActual = saldoInicial;
		this.descubierto = descubierto;
		this.titular = titular;
	}

	public void addCotitulares(Cliente cliente) {
		cotitulares.add(cliente);
        cliente.getCotitulares().add(this);
	}
	
	public void addMovimiento(Movimiento movimiento) throws Exception {
		movimientos.add(movimiento);
        movimiento.setCuenta(this);
		
		
	}
	
	public void acreditoSaldo(BigDecimal montoDeposito) {
		saldoActual = saldoActual.add(montoDeposito);
	}
	
	public void debitoSaldo(BigDecimal montoDeposito) {
		saldoActual = saldoActual.subtract(montoDeposito);
	}
	
	public void cierroCuenta(LocalDate fechaCierre) {
		this.fechaCierre = fechaCierre;
	}
	
}
