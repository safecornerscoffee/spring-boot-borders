package com.safecornerscoffee.borders.service;

import com.safecornerscoffee.borders.domain.Member;
import com.safecornerscoffee.borders.domain.item.Item;
import com.safecornerscoffee.borders.domain.order.Delivery;
import com.safecornerscoffee.borders.domain.order.DeliveryStatus;
import com.safecornerscoffee.borders.domain.order.Order;
import com.safecornerscoffee.borders.domain.order.OrderItem;
import com.safecornerscoffee.borders.repository.ItemRepository;
import com.safecornerscoffee.borders.repository.MemberRepository;
import com.safecornerscoffee.borders.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());
        delivery.setStatus(DeliveryStatus.READY);

        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        Order order = Order.createOrder(member, delivery, orderItem);

        orderRepository.save(order);
        return order.getId();
    }

    @Transactional
    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findOne(orderId);

        order.cancel();
    }
}
