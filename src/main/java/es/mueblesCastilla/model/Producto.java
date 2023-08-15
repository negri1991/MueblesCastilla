package es.mueblesCastilla.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "productos")//Para cambiar el nombre en BD
public class Producto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)//Para generar autoincrementable
	private Integer id;
	private String nombre;
	private String imagen;
	private double precio;
	private int cantidad;
	private String descripcion;
	
	@ManyToOne//Muchos a uno
	private Usuario usuario;
	
	@ManyToOne
	@JoinColumn(name = "producto_id")
	private Producto producto;

	public Producto() {

	}







	public Producto(Integer id, String nombre, String imagen, double precio, int cantidad, String descripcion,
			Usuario usuario) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.imagen = imagen;
		this.precio = precio;
		this.cantidad = cantidad;
		this.descripcion = descripcion;
		this.usuario = usuario;
	}







	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getImagen() {
		return imagen;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public int getCantidad() {
		return cantidad;
	}

	public void setCantidad(int cantidad) {
		this.cantidad = cantidad;
	}
	

	public String getDescripcion() {
		return descripcion;
	}



	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}



	@Override
	public String toString() {
		return "Producto [id=" + id + ", nombre=" + nombre + ", imagen=" + imagen + ", precio=" + precio + ", cantidad="
				+ cantidad + "]";
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	
}
