package com.duvan.curso.springboot.jpa.springbootjpareationship.repositories;

import org.springframework.data.repository.CrudRepository;

import com.duvan.curso.springboot.jpa.springbootjpareationship.entities.Invoice;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

}
