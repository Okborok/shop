package com.okborok.web.shop.repository;

import com.okborok.web.shop.model.Tile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created on 04.11.2017.
 */
@Repository
public interface TileRepository extends JpaRepository<Tile, Long> {
}
