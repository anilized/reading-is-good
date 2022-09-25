package com.getir.readingisgood.data.mapper;

public interface Mapper <E,D> {

    D toDTO(E e);
    E toEntity(D d);

}
