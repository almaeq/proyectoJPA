package org.example;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();

        try {
            entityManager.getTransaction().begin();

            Categoria perecedero = new Categoria("perecederos");
            Categoria lacteos = new Categoria("Lacteos");
            Categoria limpieza = new Categoria("Limpieza");
            Articulo leche = new Articulo(3,"leche 1",1000);
            Articulo detergente = new Articulo(4,"detergente",500);

            leche.getCategorias().add(perecedero);
            leche.getCategorias().add(lacteos);

            lacteos.getArticulos().add(leche);
            perecedero.getArticulos().add(leche);
            detergente.getCategorias().add(limpieza);
            limpieza.getArticulos().add(detergente);

            Factura fac1 = new Factura("27/08/2024",40);

            Cliente cliente = new Cliente("Alma","Quinteros",45);
            Domicilio domicilio = new Domicilio("minuzzi",345);

            cliente.setDomicilio(domicilio);
            fac1.setCliente(cliente);

            DetalleFactura linea1 = new DetalleFactura();

            linea1.setArticulo(leche);
            linea1.setCantidad(4);
            linea1.setSubtotal(450);

            fac1.getFacturas().add(linea1);

            DetalleFactura linea2 = new DetalleFactura();

            linea2.setArticulo(detergente);
            linea2.setCantidad(1);
            linea2.setSubtotal(50);

            fac1.getFacturas().add(linea2);

            entityManager.persist("fact1"); //Graba toda la factura

            entityManager.flush();

            entityManager.getTransaction().commit();
        }catch (Exception e){
            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase Factura");
        }finally {
            // Cerrar el EntityManager y el EntityManagerFactory
            entityManager.close();
            entityManagerFactory.close();
        }
    }
}
