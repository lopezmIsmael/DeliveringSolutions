package com.IsoII.DeliveringSolutions.dominio.service;
import com.IsoII.DeliveringSolutions.dominio.entidades.ItemsPedidos;
import com.IsoII.DeliveringSolutions.persistencia.DAOItemsPedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ServiceItemsPedido {
    
        @Autowired
        private DAOItemsPedido daoItemsPedido;
    
        public List<ItemsPedidos> findAll(){
            return daoItemsPedido.findAll();
        }
    
        public Optional<ItemsPedidos> findById(Long id){
            return daoItemsPedido.findById(id);
        }
    
        public ItemsPedidos save(ItemsPedidos itemsPedidos){
            return daoItemsPedido.save(itemsPedidos);
        }
    
        public void deleteById(Long id){
            daoItemsPedido.deleteById(id);
        }
    
}
