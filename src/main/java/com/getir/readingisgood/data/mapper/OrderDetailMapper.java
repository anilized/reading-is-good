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

    @Override
    public OrderDetailDTO toDTO(OrderDetail orderDetail) {
        BookMapper bookMapper = new BookMapper();
        OrderMapper orderMapper = new OrderMapper();
        return OrderDetailDTO.builder()
                .id(orderDetail.getId())
                .order(orderMapper.toDTO(orderDetail.getOrder()))
                .price(orderDetail.getPrice())
                .amount(orderDetail.getAmount())
                .book(bookMapper.toDTO(orderDetail.getBook()))
                .bookId(orderDetail.getBook().getBookId())
                .build();
    }

    public Set<OrderDetailDTO> toDTOSet(Set<OrderDetail> orderDetailSet) {
        Set<OrderDetailDTO> orderDetailDTOSet = (Set<OrderDetailDTO>) orderDetailSet.stream().map(r -> toDTO(r)).collect(Collectors.toList());
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
