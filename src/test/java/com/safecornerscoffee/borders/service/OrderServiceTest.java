package com.safecornerscoffee.borders.service;

import com.safecornerscoffee.borders.domain.Address;
import com.safecornerscoffee.borders.domain.Member;
import com.safecornerscoffee.borders.domain.item.Book;
import com.safecornerscoffee.borders.domain.item.Item;
import com.safecornerscoffee.borders.domain.order.DeliveryStatus;
import com.safecornerscoffee.borders.domain.order.Order;
import com.safecornerscoffee.borders.domain.order.OrderStatus;
import com.safecornerscoffee.borders.exception.NotEnoughStockException;
import com.safecornerscoffee.borders.repository.OrderRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class OrderServiceTest {

    @PersistenceContext
    EntityManager em;

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;


    @Test
    public void order() {
        Member member = createMember();
        Item item = createBook("시골 JPA", 10000, 10);
        int orderCount = 2;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        Order order = orderRepository.findOne(orderId);

        assertThat(order.getStatus()).as("상품 주문시 상태는 ORDER").isEqualTo(OrderStatus.ORDER);
        assertThat(order.getOrderItems()).as("주문한 상품 종류 수가 정확해야한다.").hasSize(1);
        assertThat(order.getTotalPrice()).as("주문한 가격은 가격 * 수량이다.", 10000 * 2).isEqualTo(10000 * 2);
        assertThat(item.getStockQuantity()).as("주문 수량만큼 재고가 줄어야 한다.").isEqualTo(8);
    }

    @Test
    public void ShouldThrowNotEnoughStockExceptionWhenOrderCountGreaterThanStockQuantity() {
        Member member = createMember();
        Item item = createBook("mocha recipe", 3000, 3);
        int orderCount = 10;

        assertThatThrownBy(() -> {
            Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        }).as("주문 수량이 재고보다 많을 때").isInstanceOf(NotEnoughStockException.class);

    }

    @Test
    public void cancelOrder() {
        Member member = createMember();
        Item item = createBook("mocha recipe", 3000, 13);
        int orderCount = 10;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);

        orderService.cancelOrder(orderId);

        Order order = orderRepository.findOne(orderId);

        assertThat(order.getStatus()).isEqualTo(OrderStatus.CANCEL);
        assertThat(item.getStockQuantity()).isEqualTo(13);
    }

    @Test
    public void CanNotCancelOrderAlreadyShipped() {
        Member member = createMember();
        Item item = createBook("mocha recipe", 3000, 13);
        int orderCount = 10;

        Long orderId = orderService.order(member.getId(), item.getId(), orderCount);
        Order order = orderRepository.findOne(orderId);
        order.getDelivery().setStatus(DeliveryStatus.COMP);

        assertThatThrownBy(() -> {
            order.cancel();
        }).isInstanceOf(IllegalStateException.class).hasMessage("이미 배송완료된 상품은 취소가 불가능합니다.");
    }

    private Member createMember() {
        Member member = Member.builder()
                .email("mocha@safecorners.io")
                .address(new Address("city", "street", "zipcode"))
                .build();

        em.persist(member);
        return member;
    }

    private Item createBook(String name, int price, int stockQuantity) {
        Book book = Book.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .author("mocha")
                .isbn("9482")
                .build();

        em.persist(book);
        return book;
    }

}