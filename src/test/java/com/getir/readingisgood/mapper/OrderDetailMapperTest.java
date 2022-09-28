package com.getir.readingisgood.mapper;

import com.getir.readingisgood.data.dto.BookDTO;
import com.getir.readingisgood.data.dto.OrderDTO;
import com.getir.readingisgood.data.dto.OrderDetailDTO;
import com.getir.readingisgood.data.entity.Book;
import com.getir.readingisgood.data.entity.Order;
import com.getir.readingisgood.data.entity.OrderDetail;
import com.getir.readingisgood.data.mapper.OrderDetailMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class OrderDetailMapperTest {

    OrderDetailMapper orderDetailMapper;
    OrderDetail orderDetail;
    OrderDetailDTO orderDetailDTO;
    Set<OrderDetailDTO> orderDetailDTOSet;
    Set<OrderDetail> orderDetailSet;
    @BeforeEach
    void setup() {
        orderDetailMapper = new OrderDetailMapper();
        orderDetail = new OrderDetail();
        orderDetail.setId(1L);
        orderDetail.setOrder(new Order());
        orderDetail.setAmount(1);
        orderDetail.setPrice(1.0);
        orderDetail.setBook(new Book(1L,"name","name",1,1.0,1L));
        orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(1L);
        orderDetailDTO.setOrder(new OrderDTO());
        orderDetailDTO.setAmount(1);
        orderDetailDTO.setPrice(1.0);
        orderDetailDTO.setBook(new BookDTO(1L,"name","name",1,1.0,1L));
        orderDetailDTO.setBookId(1L);
        orderDetailSet = new HashSet<>();
        orderDetailDTOSet = new HashSet<>();
        orderDetailSet.add(orderDetail);
        orderDetailDTOSet.add(orderDetailDTO);
    }

    @Test
    void toDto_whenEntityGiven_shouldReturnDto() {
        OrderDetailDTO result = orderDetailMapper.toDTO(orderDetail);
        assertEquals(orderDetailDTO.getBookId(), result.getBookId());
    }

    @Test
    void toEntity_whenDtoGiven_shouldReturnEntity() {
        OrderDetail result = orderDetailMapper.toEntity(orderDetailDTO);
        assertEquals(result.getId(), orderDetail.getId());
    }

    @Test
    void toEntitySet_whenDtoSetGiven_shouldReturnEntitySet() {
        Set<OrderDetail> result = orderDetailMapper.toEntitySet(orderDetailDTOSet);
        assertEquals(result.size(), orderDetailSet.size());
    }

    @Test
    void toDtoSet_whenEntitySetGiven_shouldReturnDtoSet() {
        Set<OrderDetailDTO> result = orderDetailMapper.toDTOSet(orderDetailSet);
        assertEquals(result.size(), orderDetailDTOSet.size());
    }

}
