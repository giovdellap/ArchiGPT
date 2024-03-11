/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.User;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface UserLogicIntf {

    User createUser(User user);

    boolean updateUser(User user);

    boolean deleteUser(Integer id);

    User findUserById(Integer id);

    List<User> findAllUser();

    User CheckStatusConfirmUser(String phone, String confirmCode);

    User findUserWithUserCode(String userCode);

    User findUserWithPhone(String phone);

    User findUserByPhoneAndConfiemCode(String phone, String confirmCode);

    String createConfirmCode(String phone);
}
