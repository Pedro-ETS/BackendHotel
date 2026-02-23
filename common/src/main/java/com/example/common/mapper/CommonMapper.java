package com.example.common.mapper;

public interface CommonMapper <RQ, RS, E>{
	
    RS entityToResponse(E entity);

    E requestToEntity(RQ request);
    
    E updateEntityFromRequest(RQ request, E entity);

	

}
