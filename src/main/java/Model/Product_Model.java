package Model;

import java.sql.Date;

/**
 *
 * @author SwichBlade15
 */
public class Product_Model {

    private int idproductos;
    private String codigo;
    private String codigointerno;
    private String nombre;
    private int precio;
    private int stockminimo;
    private String presentacion;
    private String descripcion;
    private Date fechavencimiento;
    private int iva;
    private int fk_subcategorias;
    private int fk_estados;

    public Product_Model(int idproductos, String codigo, String codigointerno, String nombre, int precio, int stockminimo, String presentacion, String descripcion, Date fechavencimiento, int iva, int fk_subcategorias, int fk_estados) {
        this.idproductos = idproductos;
        this.codigo = codigo;
        this.codigointerno = codigointerno;
        this.nombre = nombre;
        this.precio = precio;
        this.stockminimo = stockminimo;
        this.presentacion = presentacion;
        this.descripcion = descripcion;
        this.fechavencimiento = fechavencimiento;
        this.iva = iva;
        this.fk_subcategorias = fk_subcategorias;
        this.fk_estados = fk_estados;
    }

    public Product_Model() {
    }

    public int getIdproductos() {
        return idproductos;
    }

    public void setIdproductos(int idproductos) {
        this.idproductos = idproductos;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigointerno() {
        return codigointerno;
    }

    public void setCodigointerno(String codigointerno) {
        this.codigointerno = codigointerno;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPrecio() {
        return precio;
    }

    public void setPrecio(int precio) {
        this.precio = precio;
    }

    public int getStockminimo() {
        return stockminimo;
    }

    public void setStockminimo(int stockminimo) {
        this.stockminimo = stockminimo;
    }

    public String getPresentacion() {
        return presentacion;
    }

    public void setPresentacion(String presentacion) {
        this.presentacion = presentacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFechavencimiento() {
        return fechavencimiento;
    }

    public void setFechavencimiento(Date fechavencimiento) {
        this.fechavencimiento = fechavencimiento;
    }

    public int getIva() {
        return iva;
    }

    public void setIva(int iva) {
        this.iva = iva;
    }

    public int getFk_subcategorias() {
        return fk_subcategorias;
    }

    public void setFk_subcategorias(int fk_subcategorias) {
        this.fk_subcategorias = fk_subcategorias;
    }

    public int getFk_estados() {
        return fk_estados;
    }

    public void setFk_estados(int fk_estados) {
        this.fk_estados = fk_estados;
    }
}
