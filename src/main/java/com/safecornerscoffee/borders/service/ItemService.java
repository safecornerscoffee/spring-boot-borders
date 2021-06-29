package com.safecornerscoffee.borders.service;

import com.safecornerscoffee.borders.data.UpdateItemRequest;
import com.safecornerscoffee.borders.domain.item.Book;
import com.safecornerscoffee.borders.domain.item.Item;
import com.safecornerscoffee.borders.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, UpdateItemRequest dto) {
        Item findItem = itemRepository.findOne(itemId);
        findItem.setPrice(dto.getPrice());
        findItem.setName(dto.getName());
        findItem.setStockQuantity(dto.getStockQuantity());
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }
}
