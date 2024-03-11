/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic;

import ir.shenakht.paint.controller.interfaces.JudgeUserJpaControllerIntf;
import ir.shenakht.paint.domain.JudgeUser;
import ir.shenakht.paint.enums.JudgeUserType;
import ir.shenakht.paint.logic.interfaces.JudgeUserLogicIntf;
import ir.shenakht.paint.util.ConstantValues;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * @author hossien
 */
@Stateless
public class JudgeUserLogic implements JudgeUserLogicIntf {

    @EJB
    private JudgeUserJpaControllerIntf uj;

    private final long USERCODE_EXPIRATION = 14 * 24 * 60 * 60 * 1000L;//14 days in millisecond

    private final long ADMIN_EXPIRATIOIN = 30 * 60 * 1000L;//30 Minute in millisecond

    @Override
    public JudgeUser createJudgeUser(JudgeUser judgeUser, JudgeUserType judgeUserType) {
        judgeUser.setPassword(DigestUtils.sha256Hex(judgeUser.getPassword()));
        Date date = ConstantValues.ConstantFunction.getCurrentDate();
        judgeUser.setCreateAt(date);
        judgeUser.setUpdateAt(date);
        judgeUser.setLastLogin(date);
        judgeUser.setActive(true);
        judgeUser.setTypeUser(judgeUserType.ordinal());
        judgeUser.setDeleteUser(false);
        try {
            judgeUser = uj.create(judgeUser);
            judgeUser = uj.findJudgeUser(judgeUser.getId());
            return uj.findJudgeUser(judgeUser.getId());
        } catch (Exception ex) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private JudgeUser addUserCode(JudgeUser judgeUser) {
        UUID judgeUserCode = UUID.randomUUID();
        judgeUser.setUserCode(judgeUserCode.toString());
        return judgeUser;
    }

    @Override
    public JudgeUser createRegularJudgeUser(JudgeUser judgeUser) {
        judgeUser = addUserCode(judgeUser);
        return createJudgeUser(judgeUser, JudgeUserType.REGULAR_USER);
    }

    @Override
    public JudgeUser createAdminJudgeUser(JudgeUser judgeUser) {
        judgeUser = addUserCode(judgeUser);
        return createJudgeUser(judgeUser, JudgeUserType.ADMIN);
    }

    @Override
    public boolean updateJudgeUser(JudgeUser judgeUser) {
        try {
            JudgeUser judgeUserOld = findJudgeUserById(judgeUser.getId());
            if (judgeUserOld != null) {
                if (judgeUser.getPassword() != null) {
                    judgeUserOld.setPassword(DigestUtils.sha256Hex(judgeUser.getPassword()));
                }
                if (judgeUser.getUsername() != null) {
                    judgeUserOld.setUsername(judgeUser.getUsername());
                }
                if (judgeUser.getFirstName() != null) {
                    judgeUserOld.setFirstName(judgeUser.getFirstName());
                }
                if (judgeUser.getLastName() != null) {
                    judgeUserOld.setLastName(judgeUser.getLastName());
                }
                if (judgeUser.getEmail() != null) {
                    judgeUserOld.setEmail(judgeUser.getEmail());
                }
                if (judgeUser.getPhone() != null) {
                    judgeUserOld.setPhone(judgeUser.getPhone());
                }
                judgeUser.setUpdateAt(Timestamp.from(Instant.now()));
                uj.edit(judgeUserOld);
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public JudgeUser findJudgeUserByUserCode(String judgeUserCode) {
        List<JudgeUser> usr = uj.getEntityManager()
                .createNamedQuery("JudgeUser.findByUserCode")
                .setParameter("userCode", judgeUserCode)
                .getResultList();
        return !usr.isEmpty() ? usr.get(0) : null;
    }

    @Override
    public JudgeUser findJudgeUserBySession(String sessionId) {
        List<JudgeUser> judgeUser = uj.getEntityManager()
                .createNamedQuery("JudgeUser.findBySessionId")
                .setParameter("sessionId", sessionId)
                .getResultList();
        return judgeUser.size() > 0 ? judgeUser.get(0) : null;
    }

    @Override
    public JudgeUser findJudgeUserByUsername(String judgeUsername) {
        List<JudgeUser> usr = uj.getEntityManager()
                .createNamedQuery("JudgeUser.findByUsername")
                .setParameter("username", judgeUsername)
                .getResultList();
        return usr.size() > 0 ? usr.get(0) : null;
    }

    @Override
    public boolean isJudgeUserCodeValid(String judgeUserCode) {
        List<JudgeUser> judgeUserList = uj.getEntityManager()
                .createNamedQuery("JudgeUser.findByUserCode")
                .setParameter("userCode", judgeUserCode)
                .getResultList();
        if (!judgeUserList.isEmpty()) {
            return true;
        }
        return false;
    }

    @Override
    public boolean isJudgeUserLastLogin(String judgeUserCode) {
        List<JudgeUser> judgeUserList = uj.getEntityManager()
                .createNamedQuery("JudgeUser.findByUserCode")
                .setParameter("userCode", judgeUserCode)
                .getResultList();
        if (!judgeUserList.isEmpty()
                && !judgeUserList.get(0).getDeleteUser()
                && judgeUserList.get(0).getLastLogin() != null
                && (System.currentTimeMillis() - judgeUserList.get(0).getLastLogin().getTime()) <= USERCODE_EXPIRATION) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean isAdmin(String judgeUserCode) {
        JudgeUser judgeUser = findJudgeUserByUserCode(judgeUserCode);
        if (judgeUser != null) {
            if (judgeUser.getTypeUser() == JudgeUserType.ADMIN.ordinal()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isAdminLogin(String judgeUserCode) {
        JudgeUser judgeUser = findJudgeUserByUserCode(judgeUserCode);
        Date date = new Date();
        date.setTime(System.currentTimeMillis());
        if (judgeUser != null) {
            if ((judgeUser.getTypeUser() == JudgeUserType.ADMIN.ordinal())
                    && (System.currentTimeMillis() - judgeUser.getLastLogin().getTime()) <= ADMIN_EXPIRATIOIN) {
                try {
                    updateLastLogin(judgeUserCode, date, null);
                    return true;
                } catch (Exception ex) {
                    Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return false;
    }

    @Override
    public JudgeUser login(String judgeUsername, String password, String installationId) {
        JudgeUser judgeUser = findJudgeUserByUsername(judgeUsername);
        if (judgeUser != null
                && !judgeUser.getDeleteUser()
                && judgeUser.getActive()
                && judgeUser.getPassword().equals(DigestUtils.sha256Hex(password))) {
            Date date = new Date();
            date.setTime(System.currentTimeMillis());

            judgeUser = updateLastLogin(judgeUser.getUserCode(), date, installationId);
            return judgeUser;
        }
        return null;
    }

    @Override
    public JudgeUser loginPanel(String judgeUsername, String password) {
        JudgeUser judgeUser = findJudgeUserByUsername(judgeUsername);
        if (judgeUser != null
                && !judgeUser.getDeleteUser()
                && judgeUser.getActive()
                && judgeUser.getPassword().equals(DigestUtils.sha256Hex(password))
                && judgeUser.getTypeUser() == JudgeUserType.ADMIN.ordinal()) {
            Date date = new Date();
            date.setTime(System.currentTimeMillis());
            judgeUser = updateLastLogin(judgeUser.getUserCode(), date, null);
            return judgeUser;
        }
        return null;
    }

    @Override
    public boolean updateSessionId(String judgeUserCode, String sessionId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JudgeUser updateLastLogin(String judgeUserCode, Date lastLogin, String installationId) {
        JudgeUser judgeUserHelp = findJudgeUserByUserCode(judgeUserCode);
        if (judgeUserHelp != null) {
            try {
//                if (installationId != null) {
//                    judgeUserHelp.setInstallationId(installationId);
//                }
                lastLogin = ConstantValues.ConstantFunction.getCurrentDate();
                judgeUserHelp.setLastLogin(lastLogin);
                uj.edit(judgeUserHelp);
                return judgeUserHelp;
            } catch (Exception ex) {
                Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    @Override
    public boolean updateJudgeUserType(String judgeUserCode, JudgeUser judgeUser) {
        try {
            JudgeUser judgeUserOld = findJudgeUserByUsername(judgeUser.getUsername());
            if (judgeUserOld != null) {
                judgeUserOld.setTypeUser(judgeUser.getTypeUser());
                uj.edit(judgeUserOld);
                return true;
            }
        } catch (Exception ex) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public boolean isJudgeUsernameNotRepeated(String username) {
        List<JudgeUser> judgeUsers = uj.getEntityManager().
                createNamedQuery("JudgeUser.findByUsername").
                setParameter("username", username).getResultList();
        return judgeUsers.isEmpty();
    }

    @Override
    public boolean isJudgeUsernameNotRepeatedForUpdate(Integer id, String username) {
        List<JudgeUser> judgeUsers = uj.getEntityManager().
                createNamedQuery("JudgeUser.findByUsername&&!UserId").
                setParameter("username", username).
                setParameter("id", id).getResultList();
        return judgeUsers.isEmpty();
    }

    @Override
    public boolean confirmJudgeUser(String judgeUserCode, JudgeUser judgeUser) {
        JudgeUser judgeUserOld = findJudgeUserById(judgeUser.getId());
        if (judgeUserOld != null
                && judgeUserOld.getTypeUser() != JudgeUserType.ADMIN.ordinal()) {
            try {
                judgeUserOld.setActive(judgeUser.getActive());
                uj.edit(judgeUserOld);
                return true;
            } catch (Exception ex) {
                Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    @Override
    public boolean isEmailNotRepeated(String email) {
        List<JudgeUser> judgeUsers = uj.getEntityManager().
                createNamedQuery("JudgeUser.findByEmail")
                .setParameter("email", email)
                .getResultList();
        return judgeUsers.isEmpty();
    }

    @Override
    public boolean isEmailNotRepeatedForUpdate(Integer id, String email) {
        List<JudgeUser> judgeUsers = uj.getEntityManager().
                createNamedQuery("JudgeUser.findByEmail&&!UserId").
                setParameter("email", email).
                setParameter("id", id).getResultList();
        return judgeUsers.isEmpty();
    }

    @Override
    public boolean deleteJudgeUser(String userCode, Integer idJudgeUser) {
        try {
            JudgeUser judgeUser = uj.findJudgeUser(idJudgeUser);
            if (judgeUser != null) {
                if (judgeUser.getTypeUser() != JudgeUserType.ADMIN.ordinal()) {
                    judgeUser.setDeleteUser(true);
                    uj.edit(judgeUser);
                    return true;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public List<JudgeUser> findAdmin() {
        List<JudgeUser> judgeUsers = uj.getEntityManager()
                .createNamedQuery("JudgeUser.findByUserType")
                .setParameter("typeUser", JudgeUserType.ADMIN.ordinal())
                .getResultList();
        return judgeUsers.isEmpty() ? null : judgeUsers;
    }

    @Override
    public List<JudgeUser> findAdminIsOnline() {
        List<JudgeUser> judgeUsers = findAdmin();
        if (judgeUsers != null) {
            for (int i = 0; judgeUsers.size() > i; i++) {
                if ((System.currentTimeMillis() - judgeUsers.get(i).getLastLogin().getTime()) >= ADMIN_EXPIRATIOIN) {
                    judgeUsers.remove(i);
                    continue;
                }
            }
            return judgeUsers;
        }
        return null;
    }

    @Override
    public String updatePasswordRandomByEmail(String email, String username) {
        try {
            JudgeUser judgeUser = null;
            if (email != null) {
                judgeUser = findByEmail(email);
            } else {
                judgeUser = findJudgeUserByUsername(username);
            }
            if (judgeUser != null) {
                String password = Integer.toString(new Random().nextInt(999999) + 1);
                judgeUser.setPassword(DigestUtils.sha256Hex(password));
                uj.edit(judgeUser);
                return password;
            }
        } catch (Exception ex) {
            Logger.getLogger(UserLogic.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public JudgeUser findByEmail(String email) {
        List<JudgeUser> judgeUsers = uj.getEntityManager().
                createNamedQuery("JudgeUser.findByEmail").
                setParameter("email", email).
                getResultList();
        return judgeUsers.isEmpty() ? null : judgeUsers.get(0);
    }

    @Override
    public List<JudgeUser> findAllJudgeUsers() {
        List<JudgeUser> judgeUsers = uj.getEntityManager()
                .createNamedQuery("JudgeUser.findAll").getResultList();
        return judgeUsers.isEmpty() ? null : judgeUsers;
    }

    @Override
    public List<JudgeUser> findAllJudgeUsersWithLastTimeCreate(Date time) {
        List<JudgeUser> judgeUsers = uj.getEntityManager()
                .createNamedQuery("JudgeUser.findByWithLastTimeCreate")
                .setParameter("time", time)
                .getResultList();
        return judgeUsers.isEmpty() ? null : judgeUsers;
    }

    @Override
    public JudgeUser findJudgeUserById(Integer id) {
        return uj.findJudgeUser(id);
    }

}
