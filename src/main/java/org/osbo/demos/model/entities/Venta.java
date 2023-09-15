package org.osbo.demos.model.entities;

/**
 *
 * @author programmercito
 */
public class Venta {
    private int id;
    private int idTienda;
    private int idUsuario;
    private String descripcion;
    private String cliente;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the idTienda
     */
    public int getIdTienda() {
        return idTienda;
    }

    /**
     * @param idTienda the idTienda to set
     */
    public void setIdTienda(int idTienda) {
        this.idTienda = idTienda;
    }

    /**
     * @return the idUsuario
     */
    public int getIdUsuario() {
        return idUsuario;
    }

    /**
     * @param idUsuario the idUsuario to set
     */
    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    /**
     * @return the descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * @param descripcion the descripcion to set
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    /**
     * @return the cliente
     */
    public String getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

}
