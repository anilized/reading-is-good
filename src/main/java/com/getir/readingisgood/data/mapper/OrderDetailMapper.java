package com.getir.readingisgood.data.mapper;

import com.getir.readingisgood.data.dto.OrderDetailDTO;
import com.getir.readingisgood.data.entity.OrderDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderDetailMapper implements Mapper<OrderDetail, OrderDetailDTO> {

    //TODO: Find a better way to avoid StackOverflowException on toMany relations when mapping rather than setting object to null
    @Override
    public OrderDetailDTO toDTO(OrderDetail orderDetail) {
        BookMapper bookMapper = new BookMapper();
        return OrderDetailDTO.builder()
                .id(orderDetail.getId())
                .order(null)
                .price(orderDetail.getPrice())
                .amount(orderDetail.getAmount())
                .book(bookMapper.toDTO(orderDetail.getBook()))
                .bookId(orderDetail.getBook().getBookId())
                .build();
    }

    public Set<OrderDetailDTO> toDTOSet(Set<OrderDetail> orderDetailSet) {
        List<OrderDetailDTO> orderDetailDTOList= orderDetailSet.stream().map(r -> toDTO(r)).collect(Collectors.toList());
        Set<OrderDetailDTO> orderDetailDTOSet = new HashSet<>(orderDetailDTOList);
        return orderDetailDTOSet;
    }

    @Override
    public OrderDetail toEntity(OrderDetailDTO orderDetailDTO) {
        BookMapper bookMapper = new BookMapper();
        return OrderDetail.builder()
                .price(orderDetailDTO.getPrice())
                .amount(orderDetailDTO.getAmount())
                .book(bookMapper.toEntity(orderDetailDTO.getBook()))
                .id(orderDetailDTO.getId())
                .build();
    }

    public Set<OrderDetail> toEntitySet(Set<OrderDetailDTO> orderDetailDTOSet) {
        List<OrderDetail> orderDetailList = orderDetailDTOSet.stream().map(r -> toEntity(r)).collect(Collectors.toList());
        Set<OrderDetail> orderDetailSet = new HashSet<OrderDetail>(orderDetailList);
        return orderDetailSet;
    }
}
