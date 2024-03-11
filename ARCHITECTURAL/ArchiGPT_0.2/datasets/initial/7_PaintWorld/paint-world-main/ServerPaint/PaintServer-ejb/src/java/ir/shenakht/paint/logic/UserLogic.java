/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.exceptions.RollbackFailureException;
import ir.shenakht.paint.controller.interfaces.UserJpaControllerIntf;
import ir.shenakht.paint.domain.User;
import javax.ejb.Stateless;
import ir.shenakht.paint.logic.interfaces.UserLogicIntf;
import ir.shenakht.paint.util.ConstantValues;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

/**
 *
 * @author hossien
 */
@Stateless
public class UserLogic implements UserLogicIntf {

    @EJB
    private UserJpaControllerIntf uj;

    private final long CONFIRM_CODE_EXPIRATIOIN = 30 * 60 * 1000L;//30 Minute in millisecond

    @Override
    public User createUser(User user) {
        User userFind = findUserWithPhone(user.getPhone());
        if (userFind != null) {
            return userFind;
        }
        try {
            user = addUserCode(user);
            user.setActive(true);
            user.setDeleteUser(false);
            uj.create(user);
            return user;
        } catch (Exception ex) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private User addUserCode(User user) {
        UUID userCode = UUID.randomUUID();
        user.setUserCode(userCode.toString());
        return user;
    }

    @Override
    public boolean updateUser(User user) {
        try {
            User userOld = findUserById(user.getId());
            if (user.getEmail() != null) {
                userOld.setEmail(user.getEmail());
            }
            if (user.getExtraField() != null) {
                userOld.setExtraField(user.getExtraField());
            }
            if (user.getPhone() != null) {
                userOld.setPhone(user.getPhone());
            }
            if (user.getUserCode() != null) {
                userOld.setUserCode(user.getUserCode());
            }
            uj.edit(userOld);
            return true;
        } catch (RollbackFailureException ex) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean deleteUser(Integer id) {
        User user = findUserById(id);
        if (user != null) {
            try {
                uj.destroy(id);
                return true;
            } catch (RollbackFailureException ex) {
                Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public User findUserById(Integer id) {
        return uj.findUser(id);
    }

    @Override
    public List<User> findAllUser() {
        List<User> users = uj.getEntityManager()
                .createNamedQuery("User.findAll").getResultList();
        return users.isEmpty() ? null : users;
    }

    @Override
    public User CheckStatusConfirmUser(String phone, String confirmCode) {
        User user = findUserByPhoneAndConfiemCode(phone, confirmCode);
        if (user != null) {
            if ((System.currentTimeMillis() - user.getTimeCreateCodeConfrim().getTime()) <= CONFIRM_CODE_EXPIRATIOIN
                    && user.getActive()
                    && !user.getDeleteUser()) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User findUserWithUserCode(String userCode) {
        List<User> users = uj.getEntityManager()
                .createNamedQuery("User.findByUserCode")
                .setParameter("userCode", userCode).getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findUserWithPhone(String phone) {
        List<User> users = uj.getEntityManager()
                .createNamedQuery("User.findByPhone")
                .setParameter("phone", phone).getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public User findUserByPhoneAndConfiemCode(String phone, String confirmCode) {
        List<User> users = uj.getEntityManager()
                .createNamedQuery("User.findByPhone&ConfirmCode")
                .setParameter("phone", phone)
                .setParameter("confirmCode", confirmCode).getResultList();
        return users.isEmpty() ? null : users.get(0);
    }

    @Override
    public String createConfirmCode(String phone) {
        User user = findUserWithPhone(phone);
        Random random = new Random();
        int code = random.nextInt(9999);
        String confirmCode = Integer.toString(code);
        if (user != null) {
            user.setConfirmCode(confirmCode);
            user.setTimeCreateCodeConfrim(ConstantValues.ConstantFunction.getCurrentDate());
            if (updateUser(user)) {
                return confirmCode;
            }
            return null;
        }
        user = new User();
        user.setConfirmCode(confirmCode);
        user.setTimeCreateCodeConfrim(ConstantValues.ConstantFunction.getCurrentDate());
        user.setPhone(phone);
        user = createUser(user);
        if (user != null) {
            return confirmCode;
        }
        return null;
    }

}
