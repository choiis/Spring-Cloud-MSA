package org.api.application.menu

import org.api.entity.menu.MenuEntity
import org.api.entity.menu.MenuRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class MenuService {

    @Autowired
    private lateinit var menuRepository: MenuRepository

    @Transactional
    fun saveMenu(menu: MenuRequest) : MenuResponse? {
        var entity: MenuEntity = menuRepository.save(MenuClassUtils.requestToEntity(menu));
        return MenuClassUtils.entityToResponse(entity)
    }

    @Transactional(readOnly = true)
    fun getMenuOne(mid: String) : MenuResponse? {

        var option: Optional<MenuEntity> = menuRepository.findByMid(mid);
        if(option.isPresent) {
            var entity = option.get()
            return MenuClassUtils.entityToResponse(entity)
        } else {
            return null;
        }
    }


    @Transactional(readOnly = true)
    fun getMenuByRid(rid: String) : List<MenuResponse> {
       var list = menuRepository.findByRestaurant_Rid(rid)
        var menuList = arrayListOf<MenuResponse>();
        for (entity in list) {
            if (entity != null) {
                menuList.add(MenuClassUtils.entityToResponse(entity))
            }
        }
        return menuList;
    }

    @Transactional
    fun removeMenuOne(mid:String) {
        var vo: MenuEntity = MenuEntity();
        vo.mid = mid;
        menuRepository.delete(vo)
        return;
    }
}