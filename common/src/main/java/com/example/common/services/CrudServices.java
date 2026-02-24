package com.example.common.services;

import java.util.List;

public interface CrudServices <RQ, RS> {
	

	  List<RS> listar ();

	    RS obtenerPorID(Long id);

	    RS registrar(RQ request);

	    RS actualizar(RQ request, Long id);

	    void eliminar(Long id);

}
	