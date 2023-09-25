package ir.smartpath.authenticationservice.mappers;

import ir.smartpath.authenticationservice.exceptions.ServiceException;

import java.util.List;
import java.util.stream.Collectors;

public interface BaseMapper<D,E>{
    E mapDto(D d) throws ServiceException;
    D mapEntity(E e);

    default List<E> mapDto(List<D> dList){
        if(dList!=null){
            return dList.stream()
                    .map(i-> {
                        try {
                            return mapDto(i);
                        } catch (ServiceException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .collect(Collectors.toList());
        }
        return null;
    }

    default List<D> mapEntity(List<E> eList){
        if(eList!=null){
            return eList.stream()
                    .map(i-> mapEntity(i))
                    .collect(Collectors.toList());
        }
        return null;
    }
}
