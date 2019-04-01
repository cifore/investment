package com.csi.sbs.stock.business.base;

import java.util.List;

public interface BaseDao<T> {
	
	
    int insert(T t);  
   
    int delete(String id);  
    
    int update(T t);
    
    List<T> findMany(T t);
    
    T findOne(T t);

}
