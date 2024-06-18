/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.tss.repositories;

import org.springframework.data.repository.CrudRepository;
import com.tss.entities.User;

/**
 *
 * @author Lenovo
 */
public interface UserRepository extends CrudRepository<User, Long>{
    
}
