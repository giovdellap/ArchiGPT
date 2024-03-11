/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ir.shenakht.paint.logic.interfaces;

import ir.shenakht.paint.domain.JudgeUser;
import ir.shenakht.paint.enums.JudgeUserType;
import java.util.Date;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author hossien
 */
@Local
public interface JudgeUserLogicIntf {

    JudgeUser createJudgeUser(JudgeUser judgeUser, JudgeUserType judgeUserType);

    JudgeUser createRegularJudgeUser(JudgeUser judgeUser);

    JudgeUser createAdminJudgeUser(JudgeUser judgeUser);

    boolean updateJudgeUser(JudgeUser judgeUser);

    JudgeUser findJudgeUserByUserCode(String judgeUserCode);

    JudgeUser findJudgeUserBySession(String sessionId);

    JudgeUser findJudgeUserByUsername(String judgeUsername);

    boolean isJudgeUserCodeValid(String judgeUserCode);

    boolean isJudgeUserLastLogin(String judgeUserCode);

    Boolean isAdmin(String judgeUserCode);

    Boolean isAdminLogin(String judgeUserCode);

    JudgeUser login(String judgeUsername, String password, String installationId);

    JudgeUser loginPanel(String judgeUsername, String password);

    boolean updateSessionId(String judgeUserCode, String sessionId);

    JudgeUser updateLastLogin(String judgeUserCode, Date lastLogin, String installationId);

    boolean updateJudgeUserType(String judgeUserCode, JudgeUser judgeUser);

    boolean isJudgeUsernameNotRepeated(String judgeUsername);

    boolean isJudgeUsernameNotRepeatedForUpdate(Integer id, String judgeUsername);

    boolean confirmJudgeUser(String judgeUserCode, JudgeUser judgeUser);

    boolean isEmailNotRepeated(String email);

    boolean isEmailNotRepeatedForUpdate(Integer id, String email);

    boolean deleteJudgeUser(String judgeUserCode, Integer idJudgeUser);

    List<JudgeUser> findAdmin();

    List<JudgeUser> findAdminIsOnline();

    String updatePasswordRandomByEmail(String email, String judgeUsername);

    JudgeUser findByEmail(String email);

    List<JudgeUser> findAllJudgeUsers();

    List<JudgeUser> findAllJudgeUsersWithLastTimeCreate(Date time);

    JudgeUser findJudgeUserById(Integer id);
}
