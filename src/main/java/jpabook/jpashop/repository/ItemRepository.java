package jpabook.jpashop.repository;

import jpabook.jpashop.domain.item.Item;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class ItemRepository {
    @PersistenceContext
    EntityManager em;

    public void save(Item item){
        if(item.getId() == null)
            em.persist(item);
        else{
            // 준영속 -> 영속
            System.out.println("===== merging =====");
            Item findItem = em.merge(item);

            System.out.println("findItem = " + findItem.getPrice());
            System.out.println("item = " + item.getPrice());
            // item도 변경되는 이유 merge하면 기존 아이를 덮어 씌우게 된다.

            // 영속성 컨텍스트에 관리되어 있는지?
            System.out.println("em.contains(item) " + em.contains(item)); // false
            System.out.println("em.contains(findItem) " + em.contains(findItem)); // true

            // detach하면 아이탬 수정이 적용되지 않는다.
//            em.detach(findItem);

            System.out.println("===== ===== =====");
        }
    }

    public Item findOne(Long id){
        return em.find(Item.class, id);
    }

    public List<Item> findAll(){
        return em.createQuery("SELECT i FROM Item i", Item.class)
                .getResultList();
    }



}
