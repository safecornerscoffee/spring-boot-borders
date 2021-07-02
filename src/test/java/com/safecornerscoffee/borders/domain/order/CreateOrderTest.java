package com.safecornerscoffee.borders.domain.order;

import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import com.safecornerscoffee.borders.domain.item.Book;
import com.safecornerscoffee.borders.domain.item.Item;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class CreateOrderTest {


    @Test
    public void createOrder() {
        //given
        final Address address = Address.builder().city("city").street("street").zipcode("zipcode").build();
        final Member member = Member.builder().email("mocha@safecorners.io").address(address).build();
        final Delivery delivery = new Delivery(address, DeliveryStatus.READY);
        final Item item = Book.builder().name("mocha recipe").price(9500).stockQuantity(10).author("mocha").isbn("1234567890").build();
        final OrderItem orderItem = OrderItem.createOrderItem(item, 8500, 3);
        //when
        Order order = Order.createOrder(member, delivery, orderItem);
        //then
        assertThat(order.getMember()).isEqualTo(member);
        assertThat(order.getDelivery()).isEqualTo(delivery);
        assertThat(order.getOrderItems()).hasSize(1);
    }
}